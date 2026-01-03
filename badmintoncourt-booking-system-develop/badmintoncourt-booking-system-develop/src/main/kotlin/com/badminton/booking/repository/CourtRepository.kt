package com.badminton.booking.repository
import com.badminton.booking.entity.Court
import org.springframework.data.jpa.repository.JpaRepository

interface CourtRepository : JpaRepository<Court, Long> {
    fun findByLocationId(locationId: Long): List<Court>
}