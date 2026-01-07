package com.badminton.booking.controller

import com.badminton.booking.dto.CancelBookingResponse
import com.badminton.booking.dto.CreateUserRequest
import com.badminton.booking.dto.ErrorResponse
import com.badminton.booking.dto.UserBookingResponse
import com.badminton.booking.dto.UserDto
import com.badminton.booking.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "User Management", description = "APIs for user operations")
@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {
    @Operation(
        summary = "Fetch User Booking Details",
        description = "Fetch booking details for a user using their mobile number."
    )
    @GetMapping("/details")
    fun getUserBookingDetails(@RequestParam mobileNumber: String): UserBookingResponse =
        userService.getUserBookingDetails(mobileNumber)

    @Operation(
        summary = "Cancel Booking",
        description = "Cancel a specific booking using user mobile number and booking id."
    )
    @PostMapping("/bookings/{bookingId}/cancel")
    fun cancelBooking(
        @RequestParam mobileNumber: String,
        @PathVariable bookingId: Long
    ): CancelBookingResponse =
        userService.cancelBooking(mobileNumber, bookingId)

    @ExceptionHandler(IllegalArgumentException::class, IllegalStateException::class)
    fun handleErrors(ex: Exception): ErrorResponse = ErrorResponse(ex.message ?: "Unknown error")

    @Operation(
        summary = "Create User",
        description = "Create User or Admin"
    )
    @PostMapping
    fun createUser(@RequestBody @Valid request: CreateUserRequest): ResponseEntity<UserDto> =
        try {
            val user = userService.createUser(request)
            ResponseEntity.status(HttpStatus.CREATED).body(user)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
}