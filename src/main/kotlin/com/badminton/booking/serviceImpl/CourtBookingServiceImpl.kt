package com.badminton.booking.serviceImpl

import com.badminton.booking.dto.AvailableSlotResponse
import com.badminton.booking.dto.BookCourtRequest
import com.badminton.booking.dto.BookCourtResult
import com.badminton.booking.dto.CourtListItem
import com.badminton.booking.dto.LocationListItem
import com.badminton.booking.entity.Booking
import com.badminton.booking.entity.BookingStatus
import com.badminton.booking.repository.BookingRepository
import com.badminton.booking.repository.CourtRepository
import com.badminton.booking.repository.LocationRepository
import com.badminton.booking.repository.UserRepository
import com.badminton.booking.service.CourtBookingService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Service
class CourtBookingServiceImpl(
    private val locationRepository: LocationRepository,
    private val courtRepository: CourtRepository,
    private val bookingRepository: BookingRepository,
    private val userRepository: UserRepository
) : CourtBookingService {

    override fun getAllLocations(): List<LocationListItem> =
        locationRepository.findAll().map {
            LocationListItem(it.id, it.name, it.address)
        }

    override fun getCourtsForLocation(locationId: Long): List<CourtListItem> =
        courtRepository.findByLocationId(locationId).map {
            CourtListItem(it.id, it.name, it.openTime, it.closeTime)
        }

    override fun getAvailableSlots(courtId: Long, date: String, duration: Int): List<AvailableSlotResponse> {
        val court = courtRepository.findById(courtId)
            .orElseThrow { IllegalArgumentException("Court not found.") }
        val bookings = bookingRepository.findByCourtIdAndDate(courtId, date)
        val bookedSlots = bookings.filter { it.status == BookingStatus.BOOKED }
            .map { Pair(LocalTime.parse(it.startTime), LocalTime.parse(it.endTime)) }

        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val openTime = LocalTime.parse(court.openTime)
        val closeTime = LocalTime.parse(court.closeTime)

        val slots = mutableListOf<AvailableSlotResponse>()
        var slotStart = openTime

        // Each slot is 'duration' hours long
        while (slotStart.plusHours(duration.toLong()) <= closeTime) {
            val slotEnd = slotStart.plusHours(duration.toLong())
            // Check collision with existing bookings
            val isAvailable = bookedSlots.none { booked ->
                !(slotEnd <= booked.first || slotStart >= booked.second)
            }
            slots.add(
                AvailableSlotResponse(
                    time = "${slotStart.format(formatter)} - ${slotEnd.format(formatter)}",
                    isAvailable = isAvailable
                )
            )
            slotStart = slotStart.plusHours(1)
        }
        return slots
    }

    @Transactional
    override fun bookCourt(request: BookCourtRequest): BookCourtResult {
        val user = userRepository.findByMobileNumber(request.mobileNumber)
            ?: throw IllegalArgumentException("User not found.")
        val court = courtRepository.findById(request.courtId)
            .orElseThrow { IllegalArgumentException("Court not found.") }
        val bookings = bookingRepository.findByCourtIdAndDate(request.courtId, request.date)
        val newStart = LocalTime.parse(request.startTime)
        val newEnd = LocalTime.parse(request.endTime)

        // Overlap check
        val conflict = bookings.any { it.status == BookingStatus.BOOKED &&
                !(newEnd <= LocalTime.parse(it.startTime) || newStart >= LocalTime.parse(it.endTime))
        }
        if (conflict) {
            throw IllegalStateException("Selected slot is already booked.")
        }

        val newBooking = Booking(
            user = user,
            court = court,
            date = request.date,
            startTime = request.startTime,
            endTime = request.endTime,
            status = BookingStatus.BOOKED
        )
        val savedBooking = bookingRepository.save(newBooking)
        return BookCourtResult(
            bookingId = savedBooking.id,
            status = savedBooking.status.name,
            message = "Booking confirmed successfully"
        )
    }
}