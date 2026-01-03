package com.badminton.booking.dto

data class SlotInfo(
    val startTime: String,
    val endTime: String,
    val isBooked: Boolean,
    val isBlocked: Boolean
)