package com.example.nova_movil.ui.servicios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nova_movil.data.repository.ServicioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AdminServiciosViewModel(
    private val servicioRepository: ServicioRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AdminServiciosUiState(isLoading = true))
    val uiState: StateFlow<AdminServiciosUiState> = _uiState.asStateFlow()

    init {
        loadServicios()
    }

    fun loadServicios() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            try {
                val response = servicioRepository.getServicios()
                val body = response.body()

                if (response.isSuccessful && body?.status == "success") {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        servicios = body.data ?: emptyList(),
                        error = null
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = body?.message ?: "No se pudieron cargar los servicios"
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

    fun setFilter(filter: ServicioFilter) {
        _uiState.value = _uiState.value.copy(selectedFilter = filter)
    }

    fun createServicio(nombre: String, activo: Boolean) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null, actionMessage = null)

            try {
                val response = servicioRepository.createServicio(nombre, activo)
                val body = response.body()

                if (response.isSuccessful && body?.status == "success") {
                    loadServicios()
                    _uiState.value = _uiState.value.copy(
                        actionMessage = body.message ?: "Servicio creado correctamente"
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = body?.message ?: "No se pudo crear el servicio"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.localizedMessage ?: "Error al crear el servicio"
                )
            }
        }
    }

    fun updateServicio(id: Int, nombre: String, activo: Boolean) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null, actionMessage = null)

            try {
                val response = servicioRepository.updateServicio(id, nombre, activo)
                val body = response.body()

                if (response.isSuccessful && body?.status == "success") {
                    loadServicios()
                    _uiState.value = _uiState.value.copy(
                        actionMessage = body.message ?: "Servicio actualizado correctamente"
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = body?.message ?: "No se pudo actualizar el servicio"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.localizedMessage ?: "Error al actualizar el servicio"
                )
            }
        }
    }

    fun toggleServicio(id: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null, actionMessage = null)

            try {
                val response = servicioRepository.toggleServicio(id)
                val body = response.body()

                if (response.isSuccessful && body?.status == "success") {
                    loadServicios()
                    _uiState.value = _uiState.value.copy(
                        actionMessage = body.message ?: "Estado actualizado"
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

    fun deleteServicio(id: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null, actionMessage = null)

            try {
                val response = servicioRepository.deleteServicio(id)
                val body = response.body()

                if (response.isSuccessful && body?.status == "success") {
                    loadServicios()
                    _uiState.value = _uiState.value.copy(
                        actionMessage = body.message ?: "Servicio eliminado"
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = body?.message ?: "No se pudo eliminar el servicio"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.localizedMessage ?: "Error al eliminar el servicio"
                )
            }
        }
    }
}