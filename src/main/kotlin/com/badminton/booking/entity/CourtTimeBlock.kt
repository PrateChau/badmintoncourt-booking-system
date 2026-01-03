package com.badminton.booking.entity

import jakarta.persistence.*

@Entity
@Table(name = "court_time_blocks")
data class CourtTimeBlock(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "court_id", nullable = false)
    val court: Court,

    @Column(nullable = false)
    val date: String, // "YYYY-MM-DD"

    @Column(nullable = false)
    val startTime: String, // "HH:mm"

    @Column(nullable = false)
    val endTime: String // "HH:mm"
)