package com.badminton.booking.dto

data class UserBookingResponse(
    val id: Long?,
    val name: String,
    val mobileNumber: String,
    val bookings: List<BookingBrief>
)