package com.badminton.booking.dto

data class CourtSlotsDayResponse(
    val date: String,
    val slots: List<SlotInfo>
)