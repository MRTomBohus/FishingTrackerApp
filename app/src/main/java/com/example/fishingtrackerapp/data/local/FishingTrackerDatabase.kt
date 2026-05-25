package com.example.fishingtrackerapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Main Room database for the Fishing Tracker application.
 */
@Database(
    entities = [CatchEntity::class],
    version = 2,
    exportSchema = false
)
abstract class FishingTrackerDatabase : RoomDatabase() {

    abstract fun catchDao(): CatchDao
}