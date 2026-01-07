package com.badminton.booking.controller

import com.badminton.booking.dto.AdminLocationResponse
import com.badminton.booking.dto.CourtBriefResponse
import com.badminton.booking.dto.CourtSlotsDayResponse
import com.badminton.booking.service.AdminService
import com.badminton.booking.service.CourtService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Admin Management", description = "APIs for admin operations")
@RestController
@RequestMapping("/api/admin")
class AdminController(
    private val adminService: AdminService
) {

    @Operation(summary = "Fetch locations for admin", description = "Gets list of locations based on admin's mobile number.")
    @GetMapping("/locations")
    fun getLocationsForAdmin(
        @Parameter(description = "Admin's mobile number", required = true)
        @RequestParam
        @Pattern(regexp = "\\d{10}", message = "must contain 10 characters")
        mobileNumber: String
    ): List<AdminLocationResponse> =
        adminService.getAdminLocations(mobileNumber)

    @Operation(summary = "Fetch courts for a location", description = "Gets list of courts in a specific location.")
    @GetMapping("/locations/{locationId}/courts")
    fun getCourtsForLocation(
        @Parameter(description = "Location ID", required = true)
        @PathVariable locationId: Long
    ): List<CourtBriefResponse> =
        adminService.getCourtsForLocation(locationId)
}
