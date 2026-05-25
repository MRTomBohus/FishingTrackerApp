package com.example.fishingtrackerapp.data.repository

import com.example.fishingtrackerapp.data.local.SampleDataProvider
import com.example.fishingtrackerapp.domain.model.FishingSpot

/**
 * Provides predefined fishing spots stored locally in memory.
 */
class InMemoryFishingSpotRepository : FishingSpotRepository {

    override fun getFishingSpots(): List<FishingSpot> {
        return SampleDataProvider.fishingSpots
    }

    override fun getFishingSpotById(id: Int): FishingSpot? {
        return SampleDataProvider.fishingSpots.firstOrNull { it.id == id }
    }
}