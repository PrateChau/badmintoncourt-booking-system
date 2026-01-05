package com.badminton.booking.dto

data class BookCourtRequest(
    val mobileNumber: String,
    val courtId: Long,
    val date: String,         // e.g. "2024-06-10"
    val startTime: String,    // e.g. "10:00"
    val endTime: String       // e.g. "11:00"
)