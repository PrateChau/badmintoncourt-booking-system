package com.badminton.booking.dto

data class CreateCourtRequest(
    val name: String,
    val openTime: String,
    val closeTime: String
)