package com.example.nova_movil.ui.tipo_servicio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nova_movil.data.repository.TipoServicioRepository

class AdminTipoServicioViewModelFactory(
    private val tipoServicioRepository: TipoServicioRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdminTipoServicioViewModel::class.java)) {
            return AdminTipoServicioViewModel(tipoServicioRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}