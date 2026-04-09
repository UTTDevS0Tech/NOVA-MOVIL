package com.example.nova_movil.ui.personal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nova_movil.data.repository.PersonalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AdminPersonalViewModel(
    private val repo: PersonalRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AdminPersonalUiState(isLoading = true))
    val uiState: StateFlow<AdminPersonalUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                val response = repo.getPersonales()
                val body = response.body()

                if (response.isSuccessful && body?.status == "success") {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        personales = body.data ?: emptyList(),
                        error = null
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = body?.message ?: "No se pudieron cargar los estilistas"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.localizedMessage ?: "Error de conexión"
                )
            }
        }
    }

    fun createPersonal(
        nombre: String,
        descripcion: String?,
        userId: Int
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null, message = null)

            try {
                val response = repo.createPersonal(nombre, descripcion, userId)
                val body = response.body()

                if (response.isSuccessful && body?.status == "success") {
                    load()
                    _uiState.value = _uiState.value.copy(
                        message = body.message ?: "Estilista creado correctamente"
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = body?.message ?: "No se pudo crear el estilista"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.localizedMessage ?: "Error al crear el estilista"
                )
            }
        }
    }

    fun togglePersonal(userId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null, message = null)

            try {
                val response = repo.togglePersonalUser(userId)
                val body = response.body()

                if (response.isSuccessful && body?.status == "success") {
                    load()
                    _uiState.value = _uiState.value.copy(
                        message = body.message ?: "Estado actualizado correctamente"
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = body?.message ?: "No se pudo cambiar el estado"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.localizedMessage ?: "Error al cambiar el estado"
                )
            }
        }
    }
}