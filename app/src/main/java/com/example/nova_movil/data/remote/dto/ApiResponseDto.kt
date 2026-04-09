package com.example.nova_movil.data.remote.dto

data class ApiResponseDto<T>(
    val message: String,
    val data: T
)