package com.badminton.booking.service

import com.badminton.booking.dto.AdminLocationResponse
import com.badminton.booking.dto.CourtBriefResponse
import org.springframework.stereotype.Service

@Service
interface AdminService {
    fun getAdminLocations(mobileNumber: String): List<AdminLocationResponse>
    fun getCourtsForLocation(locationId: Long): List<CourtBriefResponse>
}