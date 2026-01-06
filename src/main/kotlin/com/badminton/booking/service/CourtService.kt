package com.badminton.booking.service

import com.badminton.booking.dto.CourtDto
import com.badminton.booking.dto.CourtSlotsDayResponse
import com.badminton.booking.dto.CreateCourtRequest
import org.springframework.stereotype.Service

@Service
interface CourtService {
    fun getCourtSlotsForDateRange(
        courtId: Long,
        startDate: String,
        endDate: String
    ): List<CourtSlotsDayResponse>

    fun addCourtToLocation(locationId: Long, request: CreateCourtRequest): CourtDto

}