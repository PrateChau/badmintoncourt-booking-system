package com.badminton.booking.controller

import com.badminton.booking.dto.CourtDto
import com.badminton.booking.dto.CreateCourtRequest
import com.badminton.booking.dto.RegisterCourtRequest
import com.badminton.booking.dto.RegisterCourtResponse
import com.badminton.booking.service.AdminCourtRegistrationService
import com.badminton.booking.service.CourtService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@Tag(
    name = "Register Court Management",
    description = "APIs for managing court registration operations by admin"
)
@RestController
@RequestMapping("/api/admin/locations")
class AdminCourtRegistrationController(
    private val registrationService: AdminCourtRegistrationService,
    private val courtService: CourtService
) {

    @Operation(
        summary = "Register a new location with courts",
        description = "Allows admin to register a new location with multiple courts in the system."
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun registerLocationWithCourts(@RequestBody request: RegisterCourtRequest): RegisterCourtResponse =
        registrationService.registerNewLocationWithCourts(request)

    @ExceptionHandler(IllegalArgumentException::class, IllegalStateException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleErrors(ex: Exception): Map<String, String> = mapOf("error" to (ex.message ?: "Unknown error"))

    @Operation(
        summary = "Register a new court under an existing location",
        description = "Allows admin to register a new court under an existing location in the system."
    )
    @PostMapping("/{locationId}/courts")
    fun addCourtToLocation(
        @PathVariable locationId: Long,
        @RequestBody request: CreateCourtRequest
    ): ResponseEntity<CourtDto> = try {
        val courtDto = courtService.addCourtToLocation(locationId, request)
        ResponseEntity.status(HttpStatus.CREATED).body(courtDto)
    } catch (e: IllegalArgumentException) {
        ResponseEntity.notFound().build()
    }
}