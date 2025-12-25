package com.badminton.booking.dto

data class RegisterCourtRequest( val mobileNumber: String,
                                 val locationName: String,
                                 val address: String,
                                 val courts: List<CourtCreateDTO>)
