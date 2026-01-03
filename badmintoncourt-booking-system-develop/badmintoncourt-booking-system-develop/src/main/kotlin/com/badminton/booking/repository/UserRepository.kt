package com.badminton.booking.repository

import com.badminton.booking.entity.Court
import com.badminton.booking.entity.Location
import com.badminton.booking.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByMobileNumberAndRole(mobileNumber: String, role: String): User?

    fun findByMobileNumber(mobileNumber: String): User?

    interface LocationRepository : JpaRepository<Location, Long> {
        fun countByAdmin(admin: User): Long
        fun findByAdmin(admin: User): List<Location>
    }

    interface CourtRepository : JpaRepository<Court, Long> {
        fun countByLocation(location: Location): Long
        fun findByLocation(location: Location): List<Court>
    }
}