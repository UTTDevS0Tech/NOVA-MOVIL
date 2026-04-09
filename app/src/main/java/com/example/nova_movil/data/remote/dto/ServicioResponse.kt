package com.example.nova_movil.data.remote.dto

data class ServicioListResponse(
    val status: String?,
    val error: Any?,
    val data: List<com.example.nova_movil.data.remote.dto.ServicioDto>?,
    val message: String?
)

data class ServicioDto(
    val id: Int?,
    val nombre: String?,
    val activo: Int?,
    val created_at: String?,
    val updated_at: String?
) {
    val isActivo: Boolean
        get() = activo == 1
}