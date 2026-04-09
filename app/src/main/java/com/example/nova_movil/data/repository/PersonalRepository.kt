package com.example.nova_movil.data.repository

import com.example.nova_movil.data.remote.PersonalApi
import com.example.nova_movil.data.remote.dto.BasicApiResponse
import com.example.nova_movil.data.remote.dto.PersonalListResponse
import com.example.nova_movil.data.remote.dto.PersonalRequestDto
import retrofit2.Response

class PersonalRepository(
    private val api: PersonalApi
) {
    suspend fun getPersonales(): Response<PersonalListResponse> {
        return api.getPersonales()
    }

    suspend fun createPersonal(
        nombre: String,
        descripcion: String?,
        userId: Int
    ): Response<BasicApiResponse> {
        return api.createPersonal(
            PersonalRequestDto(
                nombre = nombre,
                descripcion = descripcion,
                user_id = userId
            )
        )
    }

    suspend fun togglePersonalUser(userId: Int): Response<BasicApiResponse> {
        return api.togglePersonalUser(userId)
    }
}