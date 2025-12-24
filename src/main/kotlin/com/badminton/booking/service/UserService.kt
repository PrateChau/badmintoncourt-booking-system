package com.badminton.booking.service

import com.badminton.booking.dto.CancelBookingResponse
import com.badminton.booking.dto.UserBookingResponse
import org.springframework.stereotype.Service

@Service
interface UserService {
    fun getUserBookingDetails(mobileNumber: String): UserBookingResponse
    fun cancelBooking(mobileNumber: String, bookingId: Long): CancelBookingResponse
}