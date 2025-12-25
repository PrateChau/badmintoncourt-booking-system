package com.badminton.booking.controller

import com.badminton.booking.dto.CourtSlotsDayResponse
import com.badminton.booking.service.CourtService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Court Operations", description = "APIs for managing court slots")
@RestController
@RequestMapping("/api/courts")
class CourtController(
    private val courtService: CourtService
) {
    @Operation(
        summary = "Fetch slots for a court",
        description = "Returns a list of available slots for a court between startDate and endDate."
    )
    @GetMapping("/{courtId}/slots")
    fun getCourtSlots(
        @Parameter(description = "Court ID", required = true)
        @PathVariable courtId: Long,
        @Parameter(description = "Start date (yyyy-MM-dd)", required = true)
        @RequestParam startDate: String,
        @Parameter(description = "End date (yyyy-MM-dd)", required = true)
        @RequestParam endDate: String
    ): List<CourtSlotsDayResponse> =
        courtService.getCourtSlotsForDateRange(courtId, startDate, endDate)
}