package com.example.fishingtrackerapp.data.repository

import com.example.fishingtrackerapp.data.local.CatchDao
import com.example.fishingtrackerapp.data.local.CatchEntity
import com.example.fishingtrackerapp.data.local.toDomain
import com.example.fishingtrackerapp.domain.model.Catch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Repository implementation that stores fishing catches in the local Room database.
 */
class RoomCatchRepository(
    private val catchDao: CatchDao
) : CatchRepository {

    override val catches: Flow<List<Catch>> =
        catchDao.getAllCatches().map { entities ->
            entities.map { entity ->
                entity.toDomain()
            }
        }

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
        val entity = CatchEntity(
            fishType = fishType,
            weight = weight,
            length = length,
            location = location,
            bait = bait,
            date = date,
            released = released,
            note = note
        )

        catchDao.insertCatch(entity)
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
        val existingCatch = catchDao.getCatchById(id) ?: return

        val updatedCatch = existingCatch.copy(
            fishType = fishType,
            weight = weight,
            length = length,
            location = location,
            bait = bait,
            date = date,
            released = released,
            note = note
        )

        catchDao.updateCatch(updatedCatch)
    }

    override suspend fun deleteCatch(id: Int) {
        catchDao.deleteCatchById(id)
    }
}