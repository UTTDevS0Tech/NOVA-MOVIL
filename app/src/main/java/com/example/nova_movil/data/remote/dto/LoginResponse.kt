package com.example.nova_movil.data.remote.dto

data class LoginResponse(
    val status: String?,
    val error: Any?,
    val data: com.example.nova_movil.data.remote.dto.LoginData?,
    val message: String?
)

data class LoginData(
    val token: String?,
    val token_type: String?,
    val user: com.example.nova_movil.data.remote.dto.UserDto?
)

data class UserDto(
    val id: Int?,
    val email: String?,
    val activo: Boolean?,
    val rol_id: Int?,
    val created_at: String?,
    val acces_token: String?,
    val token_type: String?
)