package com.badminton.booking.service

import com.badminton.booking.dto.RegisterCourtRequest
import com.badminton.booking.dto.RegisterCourtResponse
import org.springframework.stereotype.Service

@Service
interface AdminCourtRegistrationService {
    fun registerNewLocationWithCourts(request: RegisterCourtRequest): RegisterCourtResponse

}