package com.example.fishingtrackerapp.data.repository

import com.example.fishingtrackerapp.data.local.SampleDataProvider
import com.example.fishingtrackerapp.domain.model.Catch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Temporary in-memory repository used mainly before the Room database is connected.
 */
class InMemoryCatchRepository : CatchRepository {

    private val _catches = MutableStateFlow(SampleDataProvider.catches)
    override val catches: Flow<List<Catch>> = _catches.asStateFlow()

    override suspend fun addCatch(
        fishType: String,
        weight: Float,
        length: Float,
        location: String,
        bait: String,
        date: Long,
        released: Boolean,
        note: String
    ) {
        val nextId = (_catches.value.maxOfOrNull { it.id } ?: 0) + 1

        val newCatch = Catch(
            id = nextId,
            fishType = fishType,
            weight = weight,
            length = length,
            location = location,
            bait = bait,
            date = date,
            released = released,
            note = note
        )

        _catches.value = _catches.value + newCatch
    }

    override suspend fun updateCatch(
        id: Int,
        fishType: String,
        weight: Float,
        length: Float,
        location: String,
        bait: String,
        date: Long,
        released: Boolean,
        note: String
    ) {
        _catches.value = _catches.value.map { existingCatch ->
            if (existingCatch.id == id) {
                existingCatch.copy(
                    fishType = fishType,
                    weight = weight,
                    length = length,
                    location = location,
                    bait = bait,
                    date = date,
                    released = released,
                    note = note
                )
            } else {
                existingCatch
            }
        }
    }

    override suspend fun deleteCatch(id: Int) {
        _catches.value = _catches.value.filterNot { it.id == id }
    }
}