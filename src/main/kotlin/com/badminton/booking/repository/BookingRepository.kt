package com.badminton.booking.repository
import com.badminton.booking.entity.Booking
import org.springframework.data.jpa.repository.JpaRepository

interface BookingRepository : JpaRepository<Booking, Long> {
    fun findByCourtIdAndDate(courtId: Long, date: String): List<Booking>
}