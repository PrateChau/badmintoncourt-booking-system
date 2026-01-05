package com.badminton.booking.dto

data class BookingBrief(
    val bookingId: Long?,
    val courtName: String,
    val locationName: String,
    val date: String,    // "YYYY-MM-DD"
    val startTime: String,
    val endTime: String,
    val status: String,
    val canCancel: Boolean
)