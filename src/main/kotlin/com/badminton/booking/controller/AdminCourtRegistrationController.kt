package com.badminton.booking.controller

import com.badminton.booking.dto.RegisterCourtRequest
import com.badminton.booking.dto.RegisterCourtResponse
import com.badminton.booking.service.AdminCourtRegistrationService
import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpStatus

@RestController
@RequestMapping("/api/admin/locations")
class AdminCourtRegistrationController(
    private val registrationService: AdminCourtRegistrationService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun registerLocationWithCourts(@RequestBody request: RegisterCourtRequest): RegisterCourtResponse =
        registrationService.registerNewLocationWithCourts(request)

    @ExceptionHandler(IllegalArgumentException::class, IllegalStateException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleErrors(ex: Exception): Map<String, String> = mapOf("error" to (ex.message ?: "Unknown error"))
}