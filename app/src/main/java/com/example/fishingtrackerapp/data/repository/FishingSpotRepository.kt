package com.example.fishingtrackerapp.data.repository

import com.example.fishingtrackerapp.domain.model.FishingSpot

/**
 * Defines operations for reading available fishing spots.
 */
interface FishingSpotRepository {

    fun getFishingSpots(): List<FishingSpot>

    fun getFishingSpotById(id: Int): FishingSpot?
}