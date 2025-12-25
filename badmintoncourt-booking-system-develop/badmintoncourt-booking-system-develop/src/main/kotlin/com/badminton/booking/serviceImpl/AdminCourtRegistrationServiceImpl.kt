package com.badminton.booking.serviceImpl

import com.badminton.booking.dto.CourtCreateDTO
import com.badminton.booking.dto.RegisterCourtRequest
import com.badminton.booking.dto.RegisterCourtResponse
import com.badminton.booking.dto.RegisteredCourtInfo
import com.badminton.booking.entity.Court
import com.badminton.booking.entity.Location
import com.badminton.booking.repository.CourtRepository
import com.badminton.booking.repository.LocationRepository
import com.badminton.booking.repository.UserRepository
import com.badminton.booking.service.AdminCourtRegistrationService
import org.springframework.stereotype.Service


class AdminCourtRegistrationServiceImpl(
    private val userRepository: UserRepository,
    private val locationRepository: LocationRepository,
    private val courtRepository: CourtRepository
) : AdminCourtRegistrationService
{


    override fun registerNewLocationWithCourts(request: RegisterCourtRequest): RegisterCourtResponse {
        val admin = userRepository.findByMobileNumberAndRole(request.mobileNumber, "ADMIN")
            ?: throw IllegalArgumentException("Admin with this mobile number not found.")

        // Enforce location limit
        val locationsCount = locationRepository.countByAdmin(admin)
        if (locationsCount >= 3) {
            throw IllegalStateException("Cannot register more than 3 locations per admin.")
        }

        if (request.courts.size > 4) {
            throw IllegalStateException("Maximum 4 courts allowed per location. Attempted: ${request.courts.size}")
        }

        // Create and save location
        val location = Location(
            name = request.locationName,
            address = request.address,
           // imageUrl = null, // Set image if needed
            admin = admin
        )
        val savedLocation = locationRepository.save(location)

        // Create courts
        val courts = request.courts.map {
            Court(
                name = it.courtName,
                imageUrl = null,
                openTime = it.openTime,
                closeTime = it.closeTime,
                location = savedLocation
            )
        }
        val savedCourts = courtRepository.saveAll(courts)



            return RegisterCourtResponse(
                locationId = savedLocation.id,
                message = "Location and courts registered successfully",
                courts = savedCourts.map { RegisteredCourtInfo(it.id, it.name) }
            )


    }

//    val mobileNumber: String,
//    val locationName: String,
//    val address: String,
//    val courts: List<CourtCreateDTO>,
//    val locationId: Long,
//    val message: String


}