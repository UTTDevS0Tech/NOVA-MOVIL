package com.example.nova_movil.data.remote

import com.example.nova_movil.data.remote.dto.BasicApiResponse
import com.example.nova_movil.data.remote.dto.PersonalListResponse
import com.example.nova_movil.data.remote.dto.PersonalRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PersonalApi {

    @GET("api/estilistas")
    suspend fun getPersonales(): Response<PersonalListResponse>

    @POST("api/estilistas")
    suspend fun createPersonal(
        @Body request: PersonalRequestDto
    ): Response<BasicApiResponse>

    @PATCH("api/users/{id}/toggle")
    suspend fun togglePersonalUser(
        @Path("id") userId: Int
    ): Response<BasicApiResponse>
}