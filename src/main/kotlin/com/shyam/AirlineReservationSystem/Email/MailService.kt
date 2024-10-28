package com.shyam.AirlineReservationSystem.Email

import jakarta.mail.internet.MimeMessage
import org.slf4j.LoggerFactory
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class MailService(private val mailSender: JavaMailSender) {

    private val logger = LoggerFactory.getLogger(MailService::class.java)

    fun sendSimpleMessage(to: String, subject: String, text: String) {
        try {
            val message: MimeMessage = mailSender.createMimeMessage()
            val helper = MimeMessageHelper(message, true)

            helper.setFrom("shyamsam1818@gmail.com")
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
}
