package com.example.nova_movil.ui.servicios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nova_movil.data.repository.ServicioRepository

class AdminServiciosViewModelFactory(
    private val servicioRepository: ServicioRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdminServiciosViewModel::class.java)) {
            return AdminServiciosViewModel(servicioRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}