package com.shyam.AirlineReservationSystem.Email

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/airline")
class MailSenderResolver(private val mailService : MailService) {

    @GetMapping("/sendEmail")
    fun sendEmail(@RequestParam to: String,
                  @RequestParam subject: String,
                  @RequestParam text: String) : String{

        mailService.sendSimpleMessage(to, subject, text)
        return "Email sent to $to successfully!"
    }
}

