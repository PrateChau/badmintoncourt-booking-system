package com.badminton.booking.repository

import com.badminton.booking.entity.Location
import com.badminton.booking.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface LocationRepository : JpaRepository<Location, Long> {
    fun findByAdmin(admin: User): List<Location>
}