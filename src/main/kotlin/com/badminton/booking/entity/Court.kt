package com.badminton.booking.entity

import jakarta.persistence.*

@Entity
@Table(name = "courts")
data class Court(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val openTime: String, // e.g., "07:00"

    @Column(nullable = false)
    val closeTime: String, // e.g., "21:00"

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    val location: Location
)