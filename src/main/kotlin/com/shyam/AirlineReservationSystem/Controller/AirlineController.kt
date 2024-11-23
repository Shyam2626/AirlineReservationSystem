package com.shyam.AirlineReservationSystem.controller

import com.shyam.AirlineReservationSystem.DTO.LoginDetails
import com.shyam.AirlineReservationSystem.DTO.User
import com.shyam.AirlineReservationSystem.Email.MailService
import org.jooq.DSLContext
import org.jooq.generated.tables.references.USER
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import kotlin.random.Random

@Controller
@RequestMapping("/airline")
class AirlineController(
    @Autowired private val dslContext: DSLContext,
    @Autowired private val mailService: MailService,
    @Autowired private val scheduledExecutorService: ScheduledExecutorService
) {
    private val userStorage = mutableMapOf<String, User>()
    private val otpStorage = mutableMapOf<String, Long>()
    private val logger = LoggerFactory.getLogger(AirlineController::class.java)

    @GetMapping
    fun showLogin(model: Model): String {
        model.addAttribute("loginDetails", LoginDetails())
        return "login"
    }

    @PostMapping
    fun processLogin(@ModelAttribute("loginDetails") loginDetails: LoginDetails, redirectAttributes: RedirectAttributes): String {
        val user = dslContext.selectFrom(USER)
            .where(USER.EMAIL.eq(loginDetails.email))
            .fetchOne() ?: return redirectWithError(redirectAttributes, "User not found.")

//        val passwordDetails = dslContext.selectFrom(PASSWORDMANAGEMENT)
//            .where(PASSWORDMANAGEMENT.USERID.eq(user.userid))
//            .fetchOne() ?: return redirectWithError(redirectAttributes, "Invalid credentials.")

        val isValid : Boolean = (loginDetails.password == user.password)

        return if (isValid) "redirect:/airline/home" else redirectWithError(redirectAttributes, "Invalid credentials.")
    }

    @GetMapping("/forgot-password")
    fun forgotPassword(model: Model): String = "forgot-password"

    @PostMapping("/forgot-password")
    fun forgotPasswordSendEmail(@ModelAttribute("forgotPasswordEmail") email: String, redirectAttributes: RedirectAttributes): String {
        val user = dslContext.selectFrom(USER).where(USER.EMAIL.eq(email)).fetchOne()

        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "If an account exists with this email, you will receive an OTP")
            return "redirect:/airline/forgot-password"
        }

        val otp = generateOTP()
        otpStorage[email] = otp
        scheduledExecutorService.schedule({ otpStorage.remove(email) }, 5, TimeUnit.MINUTES)

        user.firstname?.let { user.lastname?.let { it1 -> mailService.sendOtpForForgotPassword(it, it1, email, otp) } }

        return "redirect:/airline/verify-otp-forgotPassword?email=${urlEncode(email)}"
    }

    @GetMapping("/verify-otp-forgotPassword")
    fun verifyOtpForm(@RequestParam("email") email: String, model: Model): String {
        model.addAttribute("email", email)
        return "verify-otp-forgotPassword"
    }

    @PostMapping("/verify-otp-forgotPassword")
    fun verifyOtp(
        @RequestParam("email") email: String,
        @RequestParam("otp") otp: String,
        redirectAttributes: RedirectAttributes
    ): String {
        val storedOtp = otpStorage[email]
        if (storedOtp == otp.toLongOrNull()) {
            otpStorage.remove(email)  // Remove used OTP
            return "redirect:/airline/change-password?email=${urlEncode(email)}"
        }

        return redirectWithError(redirectAttributes, "Invalid OTP", "/airline/verify-otp-forgotPassword?email=${urlEncode(email)}")
    }

    @GetMapping("/change-password")
    fun changePasswordForm(@RequestParam("email") email: String, model: Model): String {
        model.addAttribute("email", email)
        return "change-password"
    }

    @PostMapping("/change-password")
    fun changePassword(
        @RequestParam("email") email: String,
        @RequestParam("newPassword") newPassword: String,
        @RequestParam("confirmPassword") confirmPassword: String,
        redirectAttributes: RedirectAttributes
    ): String {
        if (!isPasswordValid(newPassword)) {
            return redirectWithError(
                redirectAttributes,
                "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number, and one special character",
                "/airline/change-password?email=${urlEncode(email)}"
            )
        }

        if (newPassword != confirmPassword) {
            return redirectWithError(redirectAttributes, "Passwords do not match", "/airline/change-password?email=${urlEncode(email)}")
        }

        return try {
            val user = dslContext.selectFrom(USER).where(USER.EMAIL.eq(email)).fetchOne()
//            val userPasswordDetails = dslContext.selectFrom(PASSWORDMANAGEMENT).where(PASSWORDMANAGEMENT.USERID.eq(user?.userid)).fetchOne()

//            val encryptedPassword = passwordSecurityService.encryptPassword(newPassword)

            val updatedRows = dslContext
                            .update(USER)
                            .set(USER.PASSWORD, newPassword)
                            .where(USER.EMAIL.eq(email))
                            .execute()

//            dslContext.delete(PASSWORDMANAGEMENT)
//                .where(PASSWORDMANAGEMENT.USERID.eq(user?.userid))
//                .execute()

//            dslContext.insertInto(PASSWORDMANAGEMENT)
//                .set(PASSWORDMANAGEMENT.USERID, user?.userid)
//                .set(PASSWORDMANAGEMENT.IV, encryptedPassword.iv)
//                .set(PASSWORDMANAGEMENT.SALT, encryptedPassword.salt)
//                .set(PASSWORDMANAGEMENT.SECRETKEY, encryptedPassword.secretKey)
//                .execute()

            if (updatedRows > 0) {
                redirectAttributes.addFlashAttribute("success", "Password changed successfully!")
                "home"
            } else {
                redirectWithError(redirectAttributes, "Failed to change password. Please try again.", "/airline/change-password?email=${urlEncode(email)}")
            }
        } catch (e: Exception) {
            logger.error("Error changing password", e)
            redirectWithError(redirectAttributes, "An error occurred. Please try again.", "/airline/change-password?email=${urlEncode(email)}")
        }
    }

    @GetMapping("/new-user")
    fun showNewUserForm(model: Model): String {
        model.addAttribute("newUser", User())
        return "new-user"
    }

    @PostMapping("/new-user")
    fun saveNewUser(@ModelAttribute("newUser") user: User, redirectAttributes: RedirectAttributes): String {
        val otp = generateOTP()
        userStorage[user.email] = user
        otpStorage[user.email] = otp

        mailService.sendOtpForNewUser(user.firstName, user.lastName, user.email, otp)
        return "redirect:/airline/verify-otp-newUser?email=${urlEncode(user.email)}"
    }

    @GetMapping("/verify-otp-newUser")
    fun showOtpVerificationForm(@RequestParam("email") email: String, model: Model): String {
        model.addAttribute("email", email)
        return "verify-otp-newUser"
    }

    @PostMapping("/verify-otp-newUser")
    fun verifyOtpForNewUser(@RequestParam("email") email: String, @RequestParam("otp") otp: String): String {
        val storedOtp = otpStorage[email]
        val inputOtp = otp.toLongOrNull()

        return if (storedOtp == inputOtp) {
            val user = userStorage[email] ?: return "redirect:/airline/verify-otp-newUser?email=$email&error=User not found"

            val userId = dslContext.insertInto(USER)
                .set(USER.FIRSTNAME, user.firstName)
                .set(USER.LASTNAME, user.lastName)
                .set(USER.AGE, user.age)
                .set(USER.PHONE, user.phone)
                .set(USER.EMAIL, user.email)
                .set(USER.PASSWORD, user.password)
                .returning(USER.USERID)
                .fetchOne()
                ?.getValue(USER.USERID)

//            dslContext.insertInto(PASSWORDMANAGEMENT)
//                .set(PASSWORDMANAGEMENT.USERID, userId)
//                .set(PASSWORDMANAGEMENT.SECRETKEY, encryptionResult.secretKey)
//                .set(PASSWORDMANAGEMENT.SALT, encryptionResult.salt)
//                .set(PASSWORDMANAGEMENT.IV, encryptionResult.iv)
//                .execute()

            "redirect:/airline/home"
        } else {
            "redirect:/airline/verify-otp-newUser?email=$email&error=Invalid OTP"
        }
    }

    @GetMapping("/home")
    fun home(): String = "home"

    private fun generateOTP(): Long = Random.nextLong(100000, 1000000)

    private fun isPasswordValid(password: String): Boolean {
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"
        return password.matches(passwordPattern.toRegex())
    }

    private fun redirectWithError(redirectAttributes: RedirectAttributes, message: String, url: String = "/airline"): String {
        redirectAttributes.addFlashAttribute("error", message)
        return "redirect:$url"
    }

    private fun urlEncode(value: String): String = URLEncoder.encode(value, StandardCharsets.UTF_8.toString())
}
