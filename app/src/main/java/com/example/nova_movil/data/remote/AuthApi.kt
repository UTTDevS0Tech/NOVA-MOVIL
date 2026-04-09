package com.example.nova_movil.data.remote

import com.example.nova_movil.data.remote.dto.LoginRequest
import com.example.nova_movil.data.remote.dto.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("api/login")
    suspend fun login(
        @Body request: com.example.nova_movil.data.remote.dto.LoginRequest
    ): Response<com.example.nova_movil.data.remote.dto.LoginResponse>
}