package com.badminton.booking.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class CreateUserRequest(
    @field:NotBlank(message = "Name is required")
    val name: String,

    @field:NotBlank(message = "Mobile number is required")
    @field:Pattern(regexp = "\\d{10}", message = "Value must follow pattern \\d{10}")
    val mobileNumber: String,

    @field:NotBlank(message = "Role is required")
    val role: String // "ADMIN" or "USER"
)