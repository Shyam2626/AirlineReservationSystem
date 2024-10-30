package com.shyam.AirlineReservationSystem

object Constants{

    const val SENDER_EMAIL = "shyamsam1818@gmail.com"
    const val OTP_VALIDATION_SUBJECT = "Validating OTP for User Details"
    const val OTP_USER_NAME = "{userName}"
    const val OTP_OTP_CODE = "{otpCode}"
    const val OTP_VALIDITY_MINUTES = "{validityMinutes}"
    const val VALIDITY_MINUTES = 2

    const val OTP_CONTENT = """
        Dear {userName},
        
        Thank you for registering with us! To complete your registration and verify your email address, please use the One-Time Password (OTP) provided below:

        Your OTP: {otpCode}

        This OTP is valid for the next {validityMinutes} minutes. Please do not share it with anyone. If you did not request this verification, please ignore this email.

        Thank you for choosing Us! If you have any questions or need further assistance, feel free to contact our support team.

        Best regards,
        The Airline Email Team
    """
}

