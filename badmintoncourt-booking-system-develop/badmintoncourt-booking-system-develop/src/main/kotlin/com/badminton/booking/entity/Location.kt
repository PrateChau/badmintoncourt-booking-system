package com.badminton.booking.entity

import jakarta.persistence.*

@Entity
@Table(name = "locations")
data class Location(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val address: String,

    // Many locations to one admin (User)
    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    val admin: User
)
