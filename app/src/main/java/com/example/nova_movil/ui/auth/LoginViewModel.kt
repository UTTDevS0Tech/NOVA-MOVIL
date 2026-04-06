package com.example.nova_movil.ui.auth

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nova_movil.data.local.SessionManager
import com.example.nova_movil.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        val cleanEmail = email.trim()
        _uiState.value = _uiState.value.copy(
            email = cleanEmail,
            emailError = validateEmail(cleanEmail),
            loginError = null
        )
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(
            password = password,
            passwordError = validatePassword(password),
            loginError = null
        )
    }

    fun onTogglePasswordVisibility() {
        _uiState.value = _uiState.value.copy(
            passwordVisible = !_uiState.value.passwordVisible
        )
    }

    fun onLoginClick() {
        val currentState = _uiState.value

        val emailError = validateEmail(currentState.email)
        val passwordError = validatePassword(currentState.password)

        if (emailError != null || passwordError != null) {
            _uiState.value = currentState.copy(
                emailError = emailError,
                passwordError = passwordError
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = currentState.copy(
                isLoading = true,
                loginError = null
            )

            try {
                val response = authRepository.login(
                    email = currentState.email,
                    password = currentState.password
                )

                val body = response.body()

                if (response.isSuccessful && body?.status == "success") {
                    val loginData = body.data
                    val token = loginData?.token
                    val tokenType = loginData?.token_type ?: "Bearer"
                    val user = loginData?.user
                    val roleId = user?.rol_id

                    if (!token.isNullOrBlank() && roleId != null) {
                        sessionManager.saveSession(
                            token = token,
                            tokenType = tokenType,
                            roleId = roleId,
                            userEmail = user.email
                        )

                        if (roleId == 2) {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                navigateToAdminDashboard = true
                            )
                        } else {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                loginError = "Esta cuenta no tiene acceso al panel de administrador"
                            )
                        }
                    } else {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            loginError = "No llegó el token o el rol del usuario"
                        )
                    }
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        loginError = body?.message ?: "Credenciales inválidas"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    loginError = e.localizedMessage ?: "Error de conexión"
                )
            }
        }
    }

    fun consumeAdminNavigation() {
        _uiState.value = _uiState.value.copy(
            navigateToAdminDashboard = false
        )
    }

    private fun validateEmail(email: String): String? {
        return when {
            email.isBlank() -> "El correo es obligatorio"
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Ingresa un correo válido"
            else -> null
        }
    }

    private fun validatePassword(password: String): String? {
        return when {
            password.isBlank() -> "La contraseña es obligatoria"
            password.length < 8 -> "La contraseña debe tener al menos 8 caracteres"
            else -> null
        }
    }
}