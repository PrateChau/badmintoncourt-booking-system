package com.badminton.booking.serviceImpl

import com.badminton.booking.dto.CourtSlotsDayResponse
import com.badminton.booking.dto.SlotInfo
import com.badminton.booking.entity.BookingStatus
import com.badminton.booking.repository.BookingRepository
import com.badminton.booking.repository.CourtRepository
import com.badminton.booking.repository.CourtTimeBlockRepository
import com.badminton.booking.service.CourtService
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Service
class CourtServiceImpl(
    private val courtRepository: CourtRepository,
    private val bookingRepository: BookingRepository,
    private val courtTimeBlockRepository: CourtTimeBlockRepository
) : CourtService {

    override fun getCourtSlotsForDateRange(
        courtId: Long,
        startDate: String,
        endDate: String
    ): List<CourtSlotsDayResponse> {
        val court = courtRepository.findById(courtId)
            .orElseThrow { IllegalArgumentException("Court not found") }
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val start = LocalDate.parse(startDate, formatter)
        val end = LocalDate.parse(endDate, formatter)

        val openTime = LocalTime.parse(court.openTime)
        val closeTime = LocalTime.parse(court.closeTime)
        val slotDurationHours = 1L

        val days = mutableListOf<CourtSlotsDayResponse>()
        var date = start
        while (!date.isAfter(end)) {
            val bookings = bookingRepository.findByCourtIdAndDate(courtId, date.toString())
            val blocks = courtTimeBlockRepository.findByCourtIdAndDate(courtId, date.toString())

            val slotList = mutableListOf<SlotInfo>()
            var slotStart = openTime
            while (slotStart.plusHours(slotDurationHours) <= closeTime) {
                val slotEnd = slotStart.plusHours(slotDurationHours)
                val isBooked = bookings.any { it.startTime == slotStart.toString() && it.endTime == slotEnd.toString() && it.status == BookingStatus.BOOKED }
                val isBlocked = blocks.any { it.startTime == slotStart.toString() && it.endTime == slotEnd.toString() }
                slotList.add(
                    SlotInfo(
                        startTime = slotStart.toString(),
                        endTime = slotEnd.toString(),
                        isBooked = isBooked,
                        isBlocked = isBlocked
                    )
                )
                slotStart = slotEnd
            }
            days.add(CourtSlotsDayResponse(date.toString(), slotList))
            date = date.plusDays(1)
        }
        return days
    }
}