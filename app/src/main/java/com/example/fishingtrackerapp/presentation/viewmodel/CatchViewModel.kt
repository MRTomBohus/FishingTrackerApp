package com.example.fishingtrackerapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fishingtrackerapp.data.repository.CatchRepository
import com.example.fishingtrackerapp.data.repository.InMemoryCatchRepository
import com.example.fishingtrackerapp.domain.model.Catch
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel that exposes catch data and handles add, edit and delete operations.
 */
class CatchViewModel(
    private val repository: CatchRepository = InMemoryCatchRepository()
) : ViewModel() {

    val catches: StateFlow<List<Catch>> = repository.catches.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun getCatchById(id: Int): Catch? {
        return catches.value.firstOrNull { it.id == id }
    }

    fun addCatch(
        fishType: String,
        weight: Float,
        length: Float,
        location: String,
        bait: String,
        date: Long,
        released: Boolean,
        note: String
    ) {
        viewModelScope.launch {
            repository.addCatch(
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
    }

    fun updateCatch(
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
        viewModelScope.launch {
            repository.updateCatch(
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
    }

    fun deleteCatch(id: Int) {
        viewModelScope.launch {
            repository.deleteCatch(id)
        }
    }
}