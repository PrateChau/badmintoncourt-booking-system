package com.badminton.booking.dto

data class CancelBookingResponse(
    val bookingId: Long,
    val status: String,
    val message: String
)