package com.example.nova_movil.data.remote.dto

data class BasicApiResponse(
    val status: String?,
    val error: Any?,
    val data: Any?,
    val message: String?
)