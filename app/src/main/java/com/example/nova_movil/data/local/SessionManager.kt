package com.example.nova_movil.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.nova_movil.data.local.dataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "nova_session")

class SessionManager(private val context: Context) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val TOKEN_TYPE_KEY = stringPreferencesKey("token_type")
        private val ROLE_ID_KEY = intPreferencesKey("role_id")
        private val USER_EMAIL_KEY = stringPreferencesKey("user_email")
    }

    suspend fun saveSession(
        token: String,
        tokenType: String,
        roleId: Int,
        userEmail: String?
    ) {
        context.dataStore.edit { preferences ->
            preferences[_root_ide_package_.com.example.nova_movil.data.local.SessionManager.Companion.TOKEN_KEY] = token
            preferences[_root_ide_package_.com.example.nova_movil.data.local.SessionManager.Companion.TOKEN_TYPE_KEY] = tokenType
            preferences[_root_ide_package_.com.example.nova_movil.data.local.SessionManager.Companion.ROLE_ID_KEY] = roleId
            preferences[_root_ide_package_.com.example.nova_movil.data.local.SessionManager.Companion.USER_EMAIL_KEY] = userEmail ?: ""
        }
    }

    suspend fun getToken(): String? {
        return context.dataStore.data.first()[_root_ide_package_.com.example.nova_movil.data.local.SessionManager.Companion.TOKEN_KEY]
    }

    suspend fun getTokenType(): String? {
        return context.dataStore.data.first()[_root_ide_package_.com.example.nova_movil.data.local.SessionManager.Companion.TOKEN_TYPE_KEY]
    }

    suspend fun getRoleId(): Int? {
        return context.dataStore.data.first()[_root_ide_package_.com.example.nova_movil.data.local.SessionManager.Companion.ROLE_ID_KEY]
    }

    suspend fun clearSession() {
        context.dataStore.edit { it.clear() }
    }
}