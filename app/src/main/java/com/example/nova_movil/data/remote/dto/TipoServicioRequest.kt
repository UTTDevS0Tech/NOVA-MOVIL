package com.example.nova_movil.data.remote.dto

data class TipoServicioRequestDto(
    val nombre: String,
    val precio: Double,
    val descripcion: String?,
    val activo: Boolean,
    val tiempo_estimado: Int,
    val servicio_id: Int
)