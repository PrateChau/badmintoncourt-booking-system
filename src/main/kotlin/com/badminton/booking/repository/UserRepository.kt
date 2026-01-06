package com.badminton.booking.repository

import com.badminton.booking.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByMobileNumberAndRole(mobileNumber: String, role: String): User?

    fun findByMobileNumber(mobileNumber: String): User?

    fun existsByMobileNumber(mobileNumber: String): Boolean
}