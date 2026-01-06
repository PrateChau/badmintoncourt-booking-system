package com.badminton.booking.dto

data class CreateUserRequest(
    val name: String,
    val mobileNumber: String,
    val role: String // "ADMIN" or "USER"
)