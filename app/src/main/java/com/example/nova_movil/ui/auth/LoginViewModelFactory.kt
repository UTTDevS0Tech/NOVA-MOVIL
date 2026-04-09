package com.example.nova_movil.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nova_movil.data.local.SessionManager
import com.example.nova_movil.data.repository.AuthRepository

class LoginViewModelFactory(
    private val authRepository: com.example.nova_movil.data.repository.AuthRepository,
    private val sessionManager: com.example.nova_movil.data.local.SessionManager
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(authRepository, sessionManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}