package com.badminton.booking.controller

import com.badminton.booking.dto.CancelBookingResponse
import com.badminton.booking.dto.ErrorResponse
import com.badminton.booking.dto.UserBookingResponse
import com.badminton.booking.service.UserService
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/details")
    fun getUserBookingDetails(@RequestParam mobileNumber: String): UserBookingResponse =
        userService.getUserBookingDetails(mobileNumber)

    @PostMapping("/bookings/{bookingId}/cancel")
    fun cancelBooking(
        @RequestParam mobileNumber: String,
        @PathVariable bookingId: Long
    ): CancelBookingResponse =
        userService.cancelBooking(mobileNumber, bookingId)

    @ExceptionHandler(IllegalArgumentException::class, IllegalStateException::class)
    fun handleErrors(ex: Exception): ErrorResponse = ErrorResponse(ex.message ?: "Unknown error")
}