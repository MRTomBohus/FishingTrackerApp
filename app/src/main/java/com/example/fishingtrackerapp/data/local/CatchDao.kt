package com.example.fishingtrackerapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for catch database operations.
 */
@Dao
interface CatchDao {

    @Query("SELECT * FROM catches ORDER BY date DESC")
    fun getAllCatches(): Flow<List<CatchEntity>>

    @Query("SELECT * FROM catches WHERE id = :id LIMIT 1")
    suspend fun getCatchById(id: Int): CatchEntity?

    @Query("SELECT COUNT(*) FROM catches")
    suspend fun getCatchCount(): Int

    @Insert
    suspend fun insertCatch(catchEntity: CatchEntity)

    @Update
    suspend fun updateCatch(catchEntity: CatchEntity)

    @Delete
    suspend fun deleteCatch(catchEntity: CatchEntity)

    @Query("DELETE FROM catches WHERE id = :id")
    suspend fun deleteCatchById(id: Int)
}