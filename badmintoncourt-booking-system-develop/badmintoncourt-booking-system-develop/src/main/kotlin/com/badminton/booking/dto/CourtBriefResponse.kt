package com.badminton.booking.dto

data class CourtBriefResponse(
    val id: Long,
    val name: String,
    val imageUrl: String?,
    val openTime: String,
    val closeTime: String
)