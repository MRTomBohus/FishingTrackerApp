package com.example.fishingtrackerapp.data.repository

import com.example.fishingtrackerapp.domain.model.Catch
import kotlinx.coroutines.flow.Flow

/**
 * Defines operations for reading and modifying fishing catches.
 */
interface CatchRepository {

    val catches: Flow<List<Catch>>

    suspend fun addCatch(
        fishType: String,
        weight: Float,
        length: Float,
        location: String,
        bait: String,
        date: Long,
        released: Boolean,
        note: String
    )

    suspend fun updateCatch(
        id: Int,
        fishType: String,
        weight: Float,
        length: Float,
        location: String,
        bait: String,
        date: Long,
        released: Boolean,
        note: String
    )

    suspend fun deleteCatch(id: Int)
}