package com.badminton.booking.repository

import com.badminton.booking.entity.Booking
import com.badminton.booking.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface BookingRepository : JpaRepository<Booking, Long> {
    fun findByCourtIdAndDate(courtId: Long, date: String): List<Booking>

    fun findByUserOrderByDateDesc(user: User): List<Booking>

    fun findByIdAndUser(bookingId: Long, user: User): Booking?
}