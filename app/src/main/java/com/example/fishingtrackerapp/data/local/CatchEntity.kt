package com.example.fishingtrackerapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fishingtrackerapp.domain.model.Catch

/**
 * Room entity representing one catch row in the local database.
 */
@Entity(tableName = "catches")
data class CatchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fishType: String,
    val weight: Float,
    val length: Float,
    val location: String,
    val bait: String,
    val date: Long,
    val released: Boolean,
    val note: String
)

fun CatchEntity.toDomain(): Catch {
    return Catch(
        id = id,
        fishType = fishType,
        weight = weight,
        length = length,
        location = location,
        bait = bait,
        date = date,
        released = released,
        note = note
    )
}

fun Catch.toEntity(): CatchEntity {
    return CatchEntity(
        id = id,
        fishType = fishType,
        weight = weight,
        length = length,
        location = location,
        bait = bait,
        date = date,
        released = released,
        note = note
    )
}