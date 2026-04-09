package com.example.nova_movil.data.repository

import com.example.nova_movil.data.remote.TipoServicioApi
import com.example.nova_movil.data.remote.dto.BasicApiResponse
import com.example.nova_movil.data.remote.dto.TipoServicioListResponse
import com.example.nova_movil.data.remote.dto.TipoServicioRequestDto
import retrofit2.Response

class TipoServicioRepository(
    private val tipoServicioApi: com.example.nova_movil.data.remote.TipoServicioApi
) {
    suspend fun getTiposServicio(): Response<com.example.nova_movil.data.remote.dto.TipoServicioListResponse> {
        return tipoServicioApi.getTiposServicio()
    }

    suspend fun createTipoServicio(
        nombre: String,
        precio: Double,
        descripcion: String?,
        activo: Boolean,
        tiempoEstimado: Int,
        servicioId: Int
    ): Response<com.example.nova_movil.data.remote.dto.BasicApiResponse> {
        return tipoServicioApi.createTipoServicio(
            _root_ide_package_.com.example.nova_movil.data.remote.dto.TipoServicioRequestDto(
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
    ): Response<com.example.nova_movil.data.remote.dto.BasicApiResponse> {
        return tipoServicioApi.updateTipoServicio(
            id = id,
            request = _root_ide_package_.com.example.nova_movil.data.remote.dto.TipoServicioRequestDto(
                nombre = nombre,
                precio = precio,
                descripcion = descripcion,
                activo = activo,
                tiempo_estimado = tiempoEstimado,
                servicio_id = servicioId
            )
        )
    }

    suspend fun toggleTipoServicio(id: Int): Response<com.example.nova_movil.data.remote.dto.BasicApiResponse> {
        return tipoServicioApi.toggleTipoServicio(id)
    }

    suspend fun deleteTipoServicio(id: Int): Response<com.example.nova_movil.data.remote.dto.BasicApiResponse> {
        return tipoServicioApi.deleteTipoServicio(id)
    }
}