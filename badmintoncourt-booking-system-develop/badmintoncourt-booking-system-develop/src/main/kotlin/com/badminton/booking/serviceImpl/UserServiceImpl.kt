package com.badminton.booking.serviceImpl

import com.badminton.booking.dto.BookingBrief
import com.badminton.booking.dto.CancelBookingResponse
import com.badminton.booking.dto.UserBookingResponse
import com.badminton.booking.entity.Booking
import com.badminton.booking.entity.BookingStatus
import com.badminton.booking.repository.BookingRepository
import com.badminton.booking.repository.UserRepository
import com.badminton.booking.service.UserService
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val bookingRepository: BookingRepository
) : UserService {

    override fun getUserBookingDetails(mobileNumber: String): UserBookingResponse {
        val user = userRepository.findByMobileNumber(mobileNumber)
            ?: throw IllegalArgumentException("User not found")
        val bookings = bookingRepository.findByUserOrderByDateDesc(user).map { booking ->
            BookingBrief(
                bookingId = booking.id,
                courtName = booking.court.name,
                locationName = booking.court.location.name,
                date = booking.date,
                startTime = booking.startTime,
                endTime = booking.endTime,
                status = booking.status.name,
                canCancel = calculateCanCancel(booking)
            )
        }
        return UserBookingResponse(
            id = user.id,
            name = user.name,
            mobileNumber = user.mobileNumber,
            bookings = bookings
        )
    }

    override fun cancelBooking(mobileNumber: String, bookingId: Long): CancelBookingResponse {
        val user = userRepository.findByMobileNumber(mobileNumber)
            ?: throw IllegalArgumentException("User not found")
        val booking = bookingRepository.findByIdAndUser(bookingId, user)
            ?: throw IllegalArgumentException("Booking not found for this user")

        if (!calculateCanCancel(booking)) {
            throw IllegalStateException("Cannot cancel booking: Less than 4 hours remain or already cancelled")
        }

        booking.status = BookingStatus.CANCELLED
        bookingRepository.save(booking)
        return CancelBookingResponse(
            bookingId = booking.id,
            status = booking.status.name,
            message = "Your booking has been cancelled."
        )
    }

    private fun calculateCanCancel(booking: Booking): Boolean {
        if (booking.status != BookingStatus.BOOKED) return false
        val bookingDate = LocalDate.parse(booking.date)
        val bookingStart = LocalTime.parse(booking.startTime)
        val bookingDateTime = LocalDateTime.of(bookingDate, bookingStart)
        val now = LocalDateTime.now()
        return bookingDateTime.isAfter(now.plusHours(4))
    }
}