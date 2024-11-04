package com.shyam.AirlineReservationSystem.controller

import com.shyam.AirlineReservationSystem.DTO.LoginDetails
import com.shyam.AirlineReservationSystem.DTO.User
import com.shyam.AirlineReservationSystem.Email.MailService
import com.shyam.AirlineReservationSystem.EncryptionAndDecryption.PasswordSecurityService
import org.jooq.DSLContext
import org.jooq.generated.tables.references.PASSWORDMANAGEMENT
import org.jooq.generated.tables.references.USER
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import kotlin.random.Random

@Controller
@RequestMapping("/airline")
class AirlineController(
    @Autowired private val dslContext: DSLContext,
    @Autowired private val mailService: MailService,
    @Autowired private val passwordCoder: PasswordCoder,
    @Autowired private val passwordSecurityService: PasswordSecurityService
) {
    private val userStorage = mutableMapOf<String, User>()
    private val otpStorage = mutableMapOf<String, Long>()

    @GetMapping
    fun showLogin(model: Model): String {
        model.addAttribute("loginDetails", LoginDetails())
        return "login"
    }

    @PostMapping
    fun processLogin(@ModelAttribute("loginDetails") loginDetails: LoginDetails, redirectAttributes: RedirectAttributes): String {
        val user = dslContext.selectFrom(USER)
            .where(USER.EMAIL.eq(loginDetails.email))
            .fetchOne() ?: run {
            redirectAttributes.addFlashAttribute("error", "User not found.")
            return "redirect:/airline"
        }

        val passwordDetails = dslContext.selectFrom(PASSWORDMANAGEMENT)
            .where(PASSWORDMANAGEMENT.USERID.eq(user.userid))
            .fetchOne() ?: run {
            redirectAttributes.addFlashAttribute("error", "Invalid credentials.")
            return "redirect:/airline"
        }

        val isValid = user.password?.let {
            passwordDetails.secretkey?.let { it1 ->
                passwordDetails.salt?.let { it2 ->
                    passwordDetails.iv?.let { it3 ->
                        passwordSecurityService.verifyPassword(
                            loginDetails.password,
                            it,
                            it2,
                            it3,
                            it1
                        )
                    }
                }
            }
        }

        return if (isValid == true) {
            "redirect:/airline/home"
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid credentials.")
            "redirect:/airline"
        }
    }

    @GetMapping("/new-user")
    fun showNewUserForm(model: Model): String {
        model.addAttribute("newUser", User())
        return "new-user"
    }

    @PostMapping("/new-user")
    fun saveNewUser(@ModelAttribute("newUser") user: User): String {
        val otp = generateOTP()
        userStorage[user.email] = user
        otpStorage[user.email] = otp

        mailService.sendOTP(user.firstName, user.lastName, user.email, otp)

        return "redirect:/airline/verify-otp?email=${user.email}"
    }

    @GetMapping("/verify-otp")
    fun showOtpVerificationForm(@RequestParam("email") email: String, model: Model): String {
        model.addAttribute("email", email)
        return "verify-otp"
    }

    @PostMapping("/verify-otp")
    fun verifyOtp(@RequestParam("email") email: String, @RequestParam("otp") otp: String): String {
        val storedOtp = otpStorage[email]
        val inputOtp = otp.toLongOrNull()

        return if (storedOtp != null && storedOtp == inputOtp) {
            val user = userStorage[email] ?: return "redirect:/airline/verify-otp?email=$email&error=User not found"

            val encryptionResult = passwordSecurityService.encryptPassword(user.password ?: return "redirect:/airline/verify-otp?email=$email&error=Password is null")

            val userIdRecord = dslContext.insertInto(USER)
                .set(USER.FIRSTNAME, user.firstName)
                .set(USER.LASTNAME, user.lastName)
                .set(USER.AGE, user.age)
                .set(USER.PHONE, user.phone)
                .set(USER.EMAIL, user.email)
                .set(USER.PASSWORD, encryptionResult.encryptedPassword)
                .returning(USER.USERID)
                .fetchOne()

            val userId = userIdRecord?.getValue(USER.USERID)
            dslContext.insertInto(PASSWORDMANAGEMENT)
                .set(PASSWORDMANAGEMENT.USERID, userId)
                .set(PASSWORDMANAGEMENT.SECRETKEY, encryptionResult.secretKey)
                .set(PASSWORDMANAGEMENT.SALT, encryptionResult.salt)
                .set(PASSWORDMANAGEMENT.IV, encryptionResult.iv)
                .execute()

            "redirect:/airline/home"
        } else {
            "redirect:/airline/verify-otp?email=$email&error=Invalid OTP"
        }
    }

    @GetMapping("/home")
    fun home(): String {
        return "home"
    }

    private fun generateOTP(): Long {
        return Random.nextLong(100000, 1000000)
    }
}
