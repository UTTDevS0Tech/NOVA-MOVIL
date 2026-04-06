package com.example.nova_movil.data.remote

import android.content.Context
import com.example.nova_movil.data.local.SessionManager
import com.example.nova_movil.data.remote.interceptor.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "https://api.carlosd-dev.me/"

    private fun buildClient(context: Context): OkHttpClient {
        val sessionManager = SessionManager(context)

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(sessionManager))
            .addInterceptor(logging)
            .build()
    }

    private fun buildRetrofit(context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(buildClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun provideAuthApi(context: Context): AuthApi {
        return buildRetrofit(context).create(AuthApi::class.java)
    }

    fun provideServicioApi(context: Context): ServicioApi {
        return buildRetrofit(context).create(ServicioApi::class.java)
    }

    fun provideTipoServicioApi(context: Context): TipoServicioApi {
        return buildRetrofit(context).create(TipoServicioApi::class.java)
    }
}