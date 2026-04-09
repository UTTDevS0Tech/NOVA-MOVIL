package com.example.nova_movil.data.remote.interceptor

import com.example.nova_movil.data.local.SessionManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val sessionManager: com.example.nova_movil.data.local.SessionManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { sessionManager.getToken() }
        val tokenType = runBlocking { sessionManager.getTokenType() } ?: "Bearer"

        val request = chain.request().newBuilder().apply {
            if (!token.isNullOrBlank()) {
                addHeader("Authorization", "$tokenType $token")
                addHeader("Accept", "application/json")
            }
        }.build()

        return chain.proceed(request)
    }
}