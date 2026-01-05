package com.badminton.booking.dto

data class AvailableSlotResponse(
    val time: String,      // e.g. "10:00"
    val isAvailable: Boolean
)