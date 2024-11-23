package com.shyam.AirlineReservationSystem.Email


import com.shyam.AirlineReservationSystem.Constants
import jakarta.mail.internet.MimeMessage
import org.slf4j.LoggerFactory
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class MailService(private val mailSender: JavaMailSender) {

    private val logger = LoggerFactory.getLogger(MailService::class.java)

    fun sendSimpleMessage(to: String, subject: String, text: String) {
        try {
            val message: MimeMessage = mailSender.createMimeMessage()
            val helper = MimeMessageHelper(message, true)

            helper.setFrom(Constants.SENDER_EMAIL)
            helper.setTo(to)
            helper.setSubject(subject)
            helper.setText(text, false)

            mailSender.send(message)
            logger.info("Email sent successfully to {}", to)
        } catch (ex: MailException) {
            logger.error("Failed to send email to {}: {}", to, ex.message)
        } catch (ex: Exception) {
            logger.error("Unexpected error occurred while sending email: {}", ex.message)
        }
    }

    private fun generateEmailBody(firstName: String, lastName: String, otp : Long, emailContent : String): String {

        val fullName = "$firstName $lastName"

        return emailContent
                .replace(Constants.OTP_USER_NAME, fullName)
                .replace(Constants.OTP_OTP_CODE, otp.toString())
                .replace(Constants.OTP_VALIDITY_MINUTES, Constants.VALIDITY_MINUTES.toString())
    }



    fun sendOtpForNewUser(firstName: String, lastName: String, receiverEmail: String, otp : Long) {
        
        try{
            val message : MimeMessage = mailSender.createMimeMessage()
            val helper = MimeMessageHelper(message, true)
            
            helper.setFrom(Constants.SENDER_EMAIL)
            helper.setTo(receiverEmail)
            helper.setSubject(Constants.EMAIL_SUBJECT_NEWUSER_OTP)
            
            val emailBody = generateEmailBody(firstName, lastName, otp, Constants.NEW_USER_MAIL_CONTENT)
            helper.setText(emailBody, false)

            mailSender.send(message)
            logger.info("Email sent successfully to {}", receiverEmail)
        } catch (ex: MailException) {
            logger.error("Failed to send email to {}: {}", receiverEmail, ex.message)
        } catch (ex: Exception) {
            logger.error("Unexpected error occurred while sending email: {}", ex.message)
        }
    }

    fun sendOtpForForgotPassword(firstName: String, lastName: String, receiverEmail: String, otp: Long) {

        try {
            val message: MimeMessage = mailSender.createMimeMessage()
            val helper = MimeMessageHelper(message, true)

            helper.setFrom(Constants.SENDER_EMAIL)
            helper.setTo(receiverEmail)
            helper.setSubject(Constants.EMAIL_SUBJECT_FORGOT_PASSWORD)

            val emailBody = generateEmailBody(firstName, lastName, otp, Constants.FORGOT_PASSWORD_MAIL_CONTENT)
            helper.setText(emailBody, false)

            mailSender.send(message)

            logger.info("Email sent successfully to {}", receiverEmail)
        } catch (ex: MailException) {
            logger.error("Failed to send email to {}: {}", receiverEmail, ex.message)
        } catch (ex: Exception) {
            logger.error("Unexpected error occurred while sending email: {}", ex.message)
        }
    }
}
