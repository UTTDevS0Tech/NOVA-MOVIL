package com.example.nova_movil.data.remote

import com.example.nova_movil.data.remote.dto.BasicApiResponse
import com.example.nova_movil.data.remote.dto.ServicioListResponse
import com.example.nova_movil.data.remote.dto.ServicioRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ServicioApi {

    @GET("api/servicios")
    suspend fun getServicios(): Response<ServicioListResponse>

    @POST("api/servicios")
    suspend fun createServicio(
        @Body request: ServicioRequestDto
    ): Response<BasicApiResponse>

    @PUT("api/servicios/{id}")
    suspend fun updateServicio(
        @Path("id") id: Int,
        @Body request: ServicioRequestDto
    ): Response<BasicApiResponse>

    @PATCH("api/servicios/{id}/toggle")
    suspend fun toggleServicio(
        @Path("id") id: Int
    ): Response<BasicApiResponse>

    @DELETE("api/servicios/{id}")
    suspend fun deleteServicio(
        @Path("id") id: Int
    ): Response<BasicApiResponse>
}