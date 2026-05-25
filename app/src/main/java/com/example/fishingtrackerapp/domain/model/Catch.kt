package com.example.fishingtrackerapp.domain.model

/**
 * Represents one saved fishing catch in the application.
 */
data class Catch(
    val id: Int = 0,
    val fishType: String,
    val weight: Float,
    val length: Float,
    val location: String,
    val bait: String,
    val date: Long = System.currentTimeMillis(),
    val released: Boolean = false,
    val note: String = ""
)