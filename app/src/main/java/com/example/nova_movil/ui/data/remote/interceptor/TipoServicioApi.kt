package com.example.nova_movil.data.remote

import com.example.nova_movil.data.remote.dto.BasicApiResponse
import com.example.nova_movil.data.remote.dto.TipoServicioListResponse
import com.example.nova_movil.data.remote.dto.TipoServicioRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TipoServicioApi {

    @GET("api/tipo-servicios")
    suspend fun getTiposServicio(): Response<TipoServicioListResponse>

    @POST("api/tipo-servicios")
    suspend fun createTipoServicio(
        @Body request: TipoServicioRequestDto
    ): Response<BasicApiResponse>

    @PUT("api/tipo-servicios/{id}")
    suspend fun updateTipoServicio(
        @Path("id") id: Int,
        @Body request: TipoServicioRequestDto
    ): Response<BasicApiResponse>

    @PATCH("api/tipo-servicios/{id}/toggle-status")
    suspend fun toggleTipoServicio(
        @Path("id") id: Int
    ): Response<BasicApiResponse>

    @DELETE("api/tipo-servicios/{id}")
    suspend fun deleteTipoServicio(
        @Path("id") id: Int
    ): Response<BasicApiResponse>
}