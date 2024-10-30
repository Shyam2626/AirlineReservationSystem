package com.shyam.AirlineReservationSystem.dto

data class User(
    val userid : Int?,
    val firstName : String,
    val lastName : String,
    val age : Int,
    val phone : String,
    val email : String,
    val password : String
)

data class LoginDetails(
    val email : String,
    val password: String
)