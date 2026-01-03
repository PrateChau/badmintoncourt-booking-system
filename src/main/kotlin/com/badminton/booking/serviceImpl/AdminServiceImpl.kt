package com.badminton.booking.serviceImpl

import com.badminton.booking.dto.AdminLocationResponse
import com.badminton.booking.dto.CourtBriefResponse
import com.badminton.booking.repository.CourtRepository
import com.badminton.booking.repository.LocationRepository
import com.badminton.booking.repository.UserRepository
import com.badminton.booking.service.AdminService
import org.springframework.stereotype.Service

@Service
class AdminServiceImpl(
    private val userRepository: UserRepository,
    private val locationRepository: LocationRepository,
    private val courtRepository: CourtRepository
) : AdminService {

    override fun getAdminLocations(mobileNumber: String): List<AdminLocationResponse> {
        val admin = userRepository.findByMobileNumberAndRole(mobileNumber, "ADMIN")
            ?: throw IllegalArgumentException("Admin not found")
        return locationRepository.findByAdmin(admin).map {
            AdminLocationResponse(it.id, it.name, it.address)
        }
    }

    override fun getCourtsForLocation(locationId: Long): List<CourtBriefResponse> {
        return courtRepository.findByLocationId(locationId).map {
            CourtBriefResponse(it.id, it.name,  it.openTime, it.closeTime)
        }
    }
}