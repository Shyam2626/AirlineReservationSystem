package com.shyam.AirlineReservationSystem

object Constants{

    const val SENDER_EMAIL = "shyamsam1818@gmail.com"
    const val EMAIL_SUBJECT_NEWUSER_OTP = "Validating OTP - TrustyWings"
    const val EMAIL_SUBJECT_FORGOT_PASSWORD = "Password Reset Request - TrustWings"
    const val OTP_USER_NAME = "{userName}"
    const val OTP_OTP_CODE = "{otpCode}"
    const val OTP_VALIDITY_MINUTES = "{validityMinutes}"
    const val VALIDITY_MINUTES = 2

    const val GCM_IV_LENGTH = 12
    const val ENCRYPTION_KEY_SIZE = 128
    const val SALT_LENGTH = 16
    const val GCM_TAG_LENGTH = 128

    const val  NEW_USER_MAIL_CONTENT = """
        Dear {userName},
        
            Thank you for registering with us! To complete your registration and verify your email address, please use the One-Time Password (OTP) provided below:

            Your OTP: {otpCode}

            This OTP is valid for the next {validityMinutes} minutes. Please do not share it with anyone. If you did not request this verification, please ignore this email.

            Thank you for choosing Us! If you have any questions or need further assistance, feel free to contact our support team.

        Best regards,
        TrustyWings Support Team
    """

    const val FORGOT_PASSWORD_MAIL_CONTENT = """
        Hello {userName},

            We received a request to reset the password for your TrustWings account. Please use the One-Time Password (OTP) below to complete the password reset process.

            Your OTP Code: {otpCode}

            Note: This OTP is valid for {validityMinutes} minutes. If you did not request a password reset, please ignore this email or contact our support team for assistance.

        Thank you for choosing TrustyWings!

        Best regards,
        TrustyWings Support Team
    """
}

