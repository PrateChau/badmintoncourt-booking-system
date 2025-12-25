package com.badminton.booking.service

import com.badminton.booking.dto.CourtSlotsDayResponse
import org.springframework.stereotype.Service

@Service
interface CourtService {
    fun getCourtSlotsForDateRange(
        courtId: Long,
        startDate: String,
        endDate: String
    ): List<CourtSlotsDayResponse>
}