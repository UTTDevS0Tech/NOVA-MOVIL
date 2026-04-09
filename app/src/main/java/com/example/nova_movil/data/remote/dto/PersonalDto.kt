package com.example.nova_movil.data.remote.dto

data class PersonalListResponse(
    val status: String?,
    val error: Any?,
    val data: List<PersonalDto>?,
    val message: String?
)

data class PersonalDto(
    val id: Int?,
    val nombre: String?,
    val descripcion: String?,
    val user_id: Int?,
    val horarios: List<HorarioDto>?
)

data class HorarioDto(
    val dia: String?,
    val inicio: String?,
    val fin: String?,
    val activo: Boolean?
)