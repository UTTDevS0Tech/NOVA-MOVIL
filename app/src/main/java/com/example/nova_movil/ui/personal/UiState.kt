package com.example.nova_movil.ui.personal

import com.example.nova_movil.data.remote.dto.PersonalDto

data class AdminPersonalUiState(
    val isLoading: Boolean = false,
    val personales: List<PersonalDto> = emptyList(),
    val error: String? = null,
    val message: String? = null
)