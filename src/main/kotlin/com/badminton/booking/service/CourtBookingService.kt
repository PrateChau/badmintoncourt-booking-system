package com.badminton.booking.service

import com.badminton.booking.dto.AvailableSlotResponse
import com.badminton.booking.dto.BookCourtRequest
import com.badminton.booking.dto.BookCourtResult
import com.badminton.booking.dto.CourtListItem
import com.badminton.booking.dto.LocationListItem

interface CourtBookingService {
    fun getAllLocations(): List<LocationListItem>
    fun getCourtsForLocation(locationId: Long): List<CourtListItem>
    fun getAvailableSlots(courtId: Long, date: String, duration: Int): List<AvailableSlotResponse>
    fun bookCourt(request: BookCourtRequest): BookCourtResult
}