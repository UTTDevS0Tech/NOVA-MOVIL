package com.example.nova_movil.data.repository

import TipoServicioListResponse
import com.example.nova_movil.data.remote.TipoServicioApi
import com.example.nova_movil.data.remote.dto.BasicApiResponse
import com.example.nova_movil.data.remote.dto.TipoServicioRequestDto
import retrofit2.Response

class TipoServicioRepository(
    private val tipoServicioApi: TipoServicioApi
) {
    suspend fun getTiposServicio(): Response<TipoServicioListResponse> {
        return tipoServicioApi.getTiposServicio()
    }

    suspend fun createTipoServicio(
        nombre: String,
        precio: Double,
        descripcion: String?,
        activo: Boolean,
        tiempoEstimado: Int,
        servicioId: Int
    ): Response<BasicApiResponse> {
        return tipoServicioApi.createTipoServicio(
            TipoServicioRequestDto(
                nombre = nombre,
                precio = precio,
                descripcion = descripcion,
                activo = activo,
                tiempo_estimado = tiempoEstimado,
                servicio_id = servicioId
            )
        )
    }

    suspend fun updateTipoServicio(
        id: Int,
        nombre: String,
        precio: Double,
        descripcion: String?,
        activo: Boolean,
        tiempoEstimado: Int,
        servicioId: Int
    ): Response<BasicApiResponse> {
        return tipoServicioApi.updateTipoServicio(
            id = id,
            request = TipoServicioRequestDto(
                nombre = nombre,
                precio = precio,
                descripcion = descripcion,
                activo = activo,
                tiempo_estimado = tiempoEstimado,
                servicio_id = servicioId
            )
        )
    }

    suspend fun toggleTipoServicio(id: Int): Response<BasicApiResponse> {
        return tipoServicioApi.toggleTipoServicio(id)
    }

    suspend fun deleteTipoServicio(id: Int): Response<BasicApiResponse> {
        return tipoServicioApi.deleteTipoServicio(id)
    }
}