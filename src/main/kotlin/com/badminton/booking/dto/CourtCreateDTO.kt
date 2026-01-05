package com.badminton.booking.dto

data class CourtCreateDTO(
    val courtName: String,
    val openTime: String,
    val closeTime: String
)