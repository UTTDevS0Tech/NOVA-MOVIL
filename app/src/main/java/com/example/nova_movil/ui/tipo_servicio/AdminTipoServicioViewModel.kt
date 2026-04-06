package com.example.nova_movil.ui.tipo_servicio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nova_movil.data.repository.TipoServicioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class AdminTipoServicioViewModel(
    private val tipoServicioRepository: TipoServicioRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AdminTipoServicioUiState(isLoading = true))
    val uiState: StateFlow<AdminTipoServicioUiState> = _uiState.asStateFlow()

    init {
        loadTiposServicio()
    }

    fun loadTiposServicio() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            try {
                val response = tipoServicioRepository.getTiposServicio()
                val body = response.body()

                if (response.isSuccessful && body?.status == "success") {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        tiposServicio = body.data ?: emptyList(),
                        error = null
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = body?.message ?: "No se pudieron cargar los tipos de servicio"
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

    fun setFilter(filter: TipoServicioFilter) {
        _uiState.value = _uiState.value.copy(selectedFilter = filter)
    }

    fun createTipoServicio(
        nombre: String,
        precio: Double,
        descripcion: String?,
        activo: Boolean,
        tiempoEstimado: Int,
        servicioId: Int
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null, actionMessage = null)

            try {
                val response = tipoServicioRepository.createTipoServicio(
                    nombre = nombre,
                    precio = precio,
                    descripcion = descripcion,
                    activo = activo,
                    tiempoEstimado = tiempoEstimado,
                    servicioId = servicioId
                )
                val body = response.body()

                if (response.isSuccessful && body?.status == "success") {
                    loadTiposServicio()
                    _uiState.value = _uiState.value.copy(
                        actionMessage = body.message ?: "Tipo de servicio creado correctamente"
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = body?.message ?: "No se pudo crear el tipo de servicio"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.localizedMessage ?: "Error al crear el tipo de servicio"
                )
            }
        }
    }

    fun updateTipoServicio(
        id: Int,
        nombre: String,
        precio: Double,
        descripcion: String?,
        activo: Boolean,
        tiempoEstimado: Int,
        servicioId: Int
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null, actionMessage = null)

            try {
                val response = tipoServicioRepository.updateTipoServicio(
                    id = id,
                    nombre = nombre,
                    precio = precio,
                    descripcion = descripcion,
                    activo = activo,
                    tiempoEstimado = tiempoEstimado,
                    servicioId = servicioId
                )
                val body = response.body()

                if (response.isSuccessful && body?.status == "success") {
                    loadTiposServicio()
                    _uiState.value = _uiState.value.copy(
                        actionMessage = body.message ?: "Tipo de servicio actualizado correctamente"
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = body?.message ?: "No se pudo actualizar el tipo de servicio"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.localizedMessage ?: "Error al actualizar el tipo de servicio"
                )
            }
        }
    }

    fun toggleTipoServicio(id: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null, actionMessage = null)

            try {
                val response = tipoServicioRepository.toggleTipoServicio(id)
                val body = response.body()

                if (response.isSuccessful && body?.status == "success") {
                    loadTiposServicio()
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

    fun deleteTipoServicio(id: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null, actionMessage = null)

            try {
                val response = tipoServicioRepository.deleteTipoServicio(id)
                val body = response.body()

                if (response.isSuccessful && body?.status == "success") {
                    loadTiposServicio()
                    _uiState.value = _uiState.value.copy(
                        actionMessage = body.message ?: "Tipo de servicio eliminado"
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = body?.message ?: "No se pudo eliminar el tipo de servicio"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.localizedMessage ?: "Error al eliminar el tipo de servicio"
                )
            }
        }
    }
}