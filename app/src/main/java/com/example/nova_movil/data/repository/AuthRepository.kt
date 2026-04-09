package com.example.nova_movil.data.repository

import com.example.nova_movil.data.remote.AuthApi
import com.example.nova_movil.data.remote.dto.LoginRequest
import com.example.nova_movil.data.remote.dto.LoginResponse
import retrofit2.Response

class AuthRepository(
    private val authApi: com.example.nova_movil.data.remote.AuthApi
) {
    suspend fun login(email: String, password: String): Response<com.example.nova_movil.data.remote.dto.LoginResponse> {
        return authApi.login(
            _root_ide_package_.com.example.nova_movil.data.remote.dto.LoginRequest(
                email = email,
                password = password
            )
        )
    }
}