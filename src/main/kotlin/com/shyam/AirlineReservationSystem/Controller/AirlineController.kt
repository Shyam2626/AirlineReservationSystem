package com.shyam.AirlineReservationSystem.Controller

import com.shyam.AirlineReservationSystem.Constants
import com.shyam.AirlineReservationSystem.Email.MailService
import com.shyam.AirlineReservationSystem.dto.LoginDetails
import com.shyam.AirlineReservationSystem.dto.User
import org.jooq.DSLContext
import org.jooq.generated.tables.references.USER
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.random.Random

@RestController
@RequestMapping("/airline")
class AirlineController(private val dslContext: DSLContext,
                        private val mailService: MailService) {

    private val otpStorage = mutableMapOf<String, Long>()
    private val userStorage = mutableMapOf<String, User>()

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

    @PostMapping("/newuser")
    fun storeNewUser(@RequestBody user: User): ResponseEntity<String> {

        val otp = generateOTP()

        otpStorage[user.email] = otp
        userStorage[user.email] = user
        mailService.sendOTP(user.firstName, user.lastName, user.email, Constants.OTP_VALIDATION_SUBJECT, otp)

        return ResponseEntity.ok("OTP sent to ${user.email}. Please verify it on the next page.")
    }

    @PostMapping("/validate-otp")
    fun validateOtp(@RequestParam otp: Long): ResponseEntity<String> {
        val email = otpStorage.keys.firstOrNull()
        val expectedOtp = otpStorage[email]

        return if (expectedOtp != null && expectedOtp == otp) {
            otpStorage.remove(email)

            val user = userStorage[email]
            if (user != null) {
                dslContext
                    .insertInto(USER)
                    .set(USER.FIRSTNAME, user.firstName)
                    .set(USER.LASTNAME, user.lastName)
                    .set(USER.AGE, user.age)
                    .set(USER.PHONE, user.phone)
                    .set(USER.EMAIL, user.email)
                    .set(USER.PASSWORD, user.password)
                    .execute()
                userStorage.remove(email)
                ResponseEntity.ok("Email successfully validated! User details stored in the database.")
            } else {
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve user data.")
            }
        } else {
            ResponseEntity.status(401).body("Invalid OTP. Please try again.")
        }
    }

    private fun generateOTP(): Long {
        return Random.nextLong(10000L, 100000L)
    }
}
