package com.shyam.AirlineReservationSystem.dto

@En
data class User(
    val firstName : String,
    val lastName : String,
    val age : Int,
    val phone : Long,
    val email : String,
    val password : String
)
