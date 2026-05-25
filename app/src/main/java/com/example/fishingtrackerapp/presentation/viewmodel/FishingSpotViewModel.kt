package com.example.fishingtrackerapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.fishingtrackerapp.data.repository.FishingSpotRepository
import com.example.fishingtrackerapp.data.repository.InMemoryFishingSpotRepository
import com.example.fishingtrackerapp.domain.model.FishingSpot

/**
 * ViewModel that provides fishing spot data for the Map & Spots screen.
 */
class FishingSpotViewModel(
    private val repository: FishingSpotRepository = InMemoryFishingSpotRepository()
) : ViewModel() {

    val fishingSpots: List<FishingSpot> = repository.getFishingSpots()

    fun getFishingSpotById(id: Int): FishingSpot? {
        return repository.getFishingSpotById(id)
    }
}