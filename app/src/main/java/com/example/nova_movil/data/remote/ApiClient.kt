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
        val sessionManager =
            _root_ide_package_.com.example.nova_movil.data.local.SessionManager(context)

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(
                _root_ide_package_.com.example.nova_movil.data.remote.interceptor.AuthInterceptor(
                    sessionManager
                )
            )
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

    fun provideAuthApi(context: Context): com.example.nova_movil.data.remote.AuthApi {
        return buildRetrofit(context).create(_root_ide_package_.com.example.nova_movil.data.remote.AuthApi::class.java)
    }

    fun provideServicioApi(context: Context): com.example.nova_movil.data.remote.ServicioApi {
        return buildRetrofit(context).create(_root_ide_package_.com.example.nova_movil.data.remote.ServicioApi::class.java)
    }

    fun provideTipoServicioApi(context: Context): com.example.nova_movil.data.remote.TipoServicioApi {
        return buildRetrofit(context).create(_root_ide_package_.com.example.nova_movil.data.remote.TipoServicioApi::class.java)
    }

    fun providePersonalApi(context: Context): PersonalApi {
        return buildRetrofit(context).create(PersonalApi::class.java)
    }
}