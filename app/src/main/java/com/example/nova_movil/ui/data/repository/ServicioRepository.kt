package com.example.nova_movil.data.repository

import com.example.nova_movil.data.remote.ServicioApi
import com.example.nova_movil.data.remote.dto.BasicApiResponse
import com.example.nova_movil.data.remote.dto.ServicioListResponse
import com.example.nova_movil.data.remote.dto.ServicioRequestDto
import retrofit2.Response

class ServicioRepository(
    private val servicioApi: ServicioApi
) {
    suspend fun getServicios(): Response<ServicioListResponse> {
        return servicioApi.getServicios()
    }

    suspend fun createServicio(
        nombre: String,
        activo: Boolean = true
    ): Response<BasicApiResponse> {
        return servicioApi.createServicio(
            ServicioRequestDto(
                nombre = nombre,
                activo = activo
            )
        )
    }

    suspend fun updateServicio(
        id: Int,
        nombre: String,
        activo: Boolean?
    ): Response<BasicApiResponse> {
        return servicioApi.updateServicio(
            id = id,
            request = ServicioRequestDto(
                nombre = nombre,
                activo = activo
            )
        )
    }

    suspend fun toggleServicio(id: Int): Response<BasicApiResponse> {
        return servicioApi.toggleServicio(id)
    }

    suspend fun deleteServicio(id: Int): Response<BasicApiResponse> {
        return servicioApi.deleteServicio(id)
    }
}