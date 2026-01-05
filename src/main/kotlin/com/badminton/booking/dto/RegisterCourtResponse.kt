package com.badminton.booking.dto

data class RegisterCourtResponse(
    val locationId: Long?,
    val message: String,
    val courts: List<RegisteredCourtInfo>
)