package com.badminton.booking.repository
import com.badminton.booking.entity.Court
import com.badminton.booking.entity.Location
import org.springframework.data.jpa.repository.JpaRepository

interface CourtRepository : JpaRepository<Court, Long> {
    fun countByLocation(location: Location): Long

    fun findByLocation(location: Location): List<Court>

    fun findByLocationId(locationId: Long): List<Court>
}