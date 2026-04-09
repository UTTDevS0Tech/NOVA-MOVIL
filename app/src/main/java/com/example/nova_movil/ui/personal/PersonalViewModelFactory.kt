package com.example.nova_movil.ui.personal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nova_movil.data.repository.PersonalRepository

class AdminPersonalViewModelFactory(
    private val repository: PersonalRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdminPersonalViewModel::class.java)) {
            return AdminPersonalViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}