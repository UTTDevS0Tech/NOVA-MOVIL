package com.example.nova_movil.ui.servicios

import com.example.nova_movil.data.remote.dto.ServicioDto

enum class ServicioFilter {
    TODOS,
    ACTIVOS,
    INACTIVOS
}

data class AdminServiciosUiState(
    val isLoading: Boolean = false,
    val servicios: List<com.example.nova_movil.data.remote.dto.ServicioDto> = emptyList(),
    val error: String? = null,
    val actionMessage: String? = null,
    val selectedFilter: ServicioFilter = ServicioFilter.TODOS
) {
    val filteredServicios: List<com.example.nova_movil.data.remote.dto.ServicioDto>
        get() = when (selectedFilter) {
            ServicioFilter.TODOS -> servicios
            ServicioFilter.ACTIVOS -> servicios.filter { it.isActivo }
            ServicioFilter.INACTIVOS -> servicios.filter { !it.isActivo }
        }
}