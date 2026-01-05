package com.badminton.booking.controller

import com.badminton.booking.dto.AvailableSlotResponse
import com.badminton.booking.dto.BookCourtRequest
import com.badminton.booking.dto.BookCourtResult
import com.badminton.booking.dto.CourtListItem
import com.badminton.booking.dto.LocationListItem
import com.badminton.booking.service.CourtBookingService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpStatus


@Tag(
    name = "Court Booking Management",
    description = "APIs for managing court booking operations by users"
)
@RestController
@RequestMapping("/api/booking")
class CourtBookingController(
    private val bookingService: CourtBookingService
) {
    @Operation(
        summary = "Fetch all locations",
        description = "Retrieves a list of all available locations where courts can be booked."
    )
    @GetMapping("/locations")
    fun getLocations(): List<LocationListItem> = bookingService.getAllLocations()


    @Operation(
        summary = "Fetch courts for a given location",
        description = "Gets a list of courts for a given location, identified by locationId.",
        parameters = [Parameter(name = "locationId", description = "ID of the location", required = true)]
    )
    @GetMapping("/locations/{locationId}/courts")
    fun getCourts(@PathVariable locationId: Long): List<CourtListItem> =
        bookingService.getCourtsForLocation(locationId)

    @Operation(
        summary = "Fetch available slots for a given court",
        description = "Gets a list of available court slots for a specific court on a given date and for a given duration.",
        parameters = [
            Parameter(name = "courtId", description = "ID of the court", required = true),
            Parameter(name = "date", description = "Date in YYYY-MM-DD format", required = true),
            Parameter(name = "duration", description = "Duration of the slot in minutes", required = true)
        ]
    )
    @GetMapping("/courts/{courtId}/slots")
    fun getSlots(
        @PathVariable courtId: Long,
        @RequestParam date: String,
        @RequestParam duration: Int
    ): List<AvailableSlotResponse> =
        bookingService.getAvailableSlots(courtId, date, duration)


    @Operation(
        summary = "Book a court",
        description = "Submits a request to book a court for a user. Requires detailed booking info in the request body."
    )
    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    fun bookCourt(@RequestBody request: BookCourtRequest): BookCourtResult =
        bookingService.bookCourt(request)

    @ExceptionHandler(IllegalArgumentException::class, IllegalStateException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleErrors(ex: Exception): Map<String, String> = mapOf("error" to (ex.message ?: "Unknown error"))
}