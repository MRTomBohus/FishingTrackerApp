package com.example.fishingtrackerapp.domain.model

/**
 * Represents a fishing location shown in the Map & Spots screen.
 */
data class FishingSpot(
    val id: Int,
    val name: String,
    val type: String,
    val description: String,
    val latitude: Double,
    val longitude: Double
)