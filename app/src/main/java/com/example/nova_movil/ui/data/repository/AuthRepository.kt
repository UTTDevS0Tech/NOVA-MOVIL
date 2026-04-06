package com.example.nova_movil.data.repository

import com.example.nova_movil.data.remote.AuthApi
import com.example.nova_movil.data.remote.dto.LoginRequest
import com.example.nova_movil.data.remote.dto.LoginResponse
import retrofit2.Response

class AuthRepository(
    private val authApi: AuthApi
) {
    suspend fun login(email: String, password: String): Response<LoginResponse> {
        return authApi.login(
            LoginRequest(
                email = email,
                password = password
            )
        )
    }
}