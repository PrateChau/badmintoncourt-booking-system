package com.badminton.booking.entity

import jakarta.persistence.*

@Entity
@Table(name = "bookings")
data class Booking(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne
    @JoinColumn(name = "court_id", nullable = false)
    val court: Court,

    @Column(nullable = false)
    val date: String, // "YYYY-MM-DD"

    @Column(nullable = false)
    val startTime: String, // "HH:mm"

    @Column(nullable = false)
    val endTime: String, // "HH:mm"

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: BookingStatus = BookingStatus.BOOKED,

    @Column(nullable = false)
    val canModify: Boolean = true // Allow one modification
)

enum class BookingStatus {
    BOOKED,
    CANCELLED
}