package com.badminton.booking.dto

data class CourtDto(
    val id: Long,
    val name: String,
    val openTime: String,
    val closeTime: String,
    val locationId: Long
)