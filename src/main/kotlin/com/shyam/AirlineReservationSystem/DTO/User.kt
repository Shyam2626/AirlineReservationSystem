package com.shyam.AirlineReservationSystem.DTO

data class User(
    var userid: Int? = null,
    var firstName: String = "",
    var lastName: String = "",
    var age: String = "",
    var phone: String = "",
    var email: String = "",
    var password: String = ""
)

data class LoginDetails(
    var email: String = "",
    var password: String = ""
)

data class EncryptionResult(
    val encryptedPassword: String,
    val salt: String,
    val iv: String,
    val secretKey: String
)