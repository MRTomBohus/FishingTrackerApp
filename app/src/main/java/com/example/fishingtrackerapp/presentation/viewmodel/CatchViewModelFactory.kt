package com.example.fishingtrackerapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fishingtrackerapp.data.repository.CatchRepository

/**
 * Factory used to create CatchViewModel with a repository dependency.
 */
class CatchViewModelFactory(
    private val repository: CatchRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatchViewModel::class.java)) {
            return CatchViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}