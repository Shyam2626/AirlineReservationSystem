package com.shyam.AirlineReservationSystem.Controller

import com.shyam.AirlineReservationSystem.Constants
import com.shyam.AirlineReservationSystem.Email.MailService
import com.shyam.AirlineReservationSystem.dto.LoginDetails
import com.shyam.AirlineReservationSystem.dto.User
import org.jooq.DSLContext
import org.jooq.generated.tables.references.USER
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.*
import kotlin.random.Random

@RestController
@RequestMapping("/airline")
class AirlineController(private val dslContext: DSLContext,
                        private val mailService: MailService) {

    @PostMapping("/login")
    fun getLoginDetails(@RequestBody loginDetails: LoginDetails): ResponseEntity<String> {
        val email = loginDetails.email
        val password = loginDetails.password

        val userExists = dslContext.fetchExists(
            dslContext
                .selectFrom(USER)
                .where(USER.EMAIL.eq(email))
                .and(USER.PASSWORD.eq(password))
        )

        return if (userExists) {
            ResponseEntity.ok("Login Successful")
        } else {
            ResponseEntity.status(401).body("Invalid email or password !!")
        }
    }

    @PostMapping
    fun getEmail(@RequestBody email : String) : ResponseEntity<String>{

        mailService.sendOTP()

    }


    @PostMapping("/newuser")
    fun storeNewUser(@RequestBody user: User): ResponseEntity<String> {

        mailService.sendOTP(user.firstName, user.lastName, user.email, Constants.OTP_VALIDATION_SUBJECT)

        dslContext
            .insertInto(USER)
            .set(USER.FIRSTNAME, user.firstName)
            .set(USER.LASTNAME, user.lastName)
            .set(USER.AGE, user.age)
            .set(USER.PHONE, user.phone)
            .set(USER.EMAIL, user.email)
            .set(USER.PASSWORD, user.password)
            .execute()

        return ResponseEntity.ok("Successfully stored")
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadable(e: HttpMessageNotReadableException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Request body is not readable: ${e.message}")
    }
}
