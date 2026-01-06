package com.badminton.booking.service

import com.badminton.booking.dto.CancelBookingResponse
import com.badminton.booking.dto.CreateUserRequest
import com.badminton.booking.dto.UserBookingResponse
import com.badminton.booking.dto.UserDto
import org.springframework.stereotype.Service

@Service
interface UserService {
    fun getUserBookingDetails(mobileNumber: String): UserBookingResponse

    fun cancelBooking(mobileNumber: String, bookingId: Long): CancelBookingResponse

    fun createUser(request: CreateUserRequest): UserDto
}