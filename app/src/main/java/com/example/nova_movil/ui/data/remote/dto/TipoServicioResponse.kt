package com.example.nova_movil.data.remote.dto

data class TipoServicioListResponse(
    val status: String?,
    val error: Any?,
    val data: List<TipoServicioDto>?,
    val message: String?
)

data class TipoServicioDto(
    val id: Int?,
    val nombre: String?,
    val descripcion: String?,
    val imagen: String?,
    val activo: Int?,
    val precio: Double?,
    val tiempo_estimado: Int?,
    val servicio_id: Int?
) {
    val isActivo: Boolean
        get() = activo == 1
}