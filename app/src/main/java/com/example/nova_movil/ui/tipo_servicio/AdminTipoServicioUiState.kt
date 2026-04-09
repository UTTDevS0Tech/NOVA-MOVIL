package com.example.nova_movil.ui.tipo_servicio

import com.example.nova_movil.data.remote.dto.TipoServicioDto

enum class TipoServicioFilter {
    TODOS,
    ACTIVOS,
    INACTIVOS
}

data class AdminTipoServicioUiState(
    val isLoading: Boolean = false,
    val tiposServicio: List<com.example.nova_movil.data.remote.dto.TipoServicioDto> = emptyList(),
    val error: String? = null,
    val actionMessage: String? = null,
    val selectedFilter: TipoServicioFilter = TipoServicioFilter.TODOS
) {
    val filteredTiposServicio: List<com.example.nova_movil.data.remote.dto.TipoServicioDto>
        get() = when (selectedFilter) {
            TipoServicioFilter.TODOS -> tiposServicio
            TipoServicioFilter.ACTIVOS -> tiposServicio.filter { it.isActivo }
            TipoServicioFilter.INACTIVOS -> tiposServicio.filter { !it.isActivo }
        }
}