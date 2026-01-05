package com.badminton.booking.controller

import com.badminton.booking.dto.AvailableSlotResponse
import com.badminton.booking.dto.BookCourtRequest
import com.badminton.booking.dto.BookCourtResult
import com.badminton.booking.dto.CourtListItem
import com.badminton.booking.dto.LocationListItem
import com.badminton.booking.service.CourtBookingService
import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpStatus

@RestController
@RequestMapping("/api/booking")
class CourtBookingController(
    private val bookingService: CourtBookingService
) {
    @GetMapping("/locations")
    fun getLocations(): List<LocationListItem> = bookingService.getAllLocations()

    @GetMapping("/locations/{locationId}/courts")
    fun getCourts(@PathVariable locationId: Long): List<CourtListItem> =
        bookingService.getCourtsForLocation(locationId)

    @GetMapping("/courts/{courtId}/slots")
    fun getSlots(
        @PathVariable courtId: Long,
        @RequestParam date: String,
        @RequestParam duration: Int
    ): List<AvailableSlotResponse> =
        bookingService.getAvailableSlots(courtId, date, duration)

    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    fun bookCourt(@RequestBody request: BookCourtRequest): BookCourtResult =
        bookingService.bookCourt(request)

    @ExceptionHandler(IllegalArgumentException::class, IllegalStateException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleErrors(ex: Exception): Map<String, String> = mapOf("error" to (ex.message ?: "Unknown error"))
}