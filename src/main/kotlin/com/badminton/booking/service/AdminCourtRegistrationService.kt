package com.badminton.booking.service

import com.badminton.booking.dto.RegisterCourtRequest
import com.badminton.booking.dto.RegisterCourtResponse

interface AdminCourtRegistrationService {
    fun registerNewLocationWithCourts(request: RegisterCourtRequest): RegisterCourtResponse
}