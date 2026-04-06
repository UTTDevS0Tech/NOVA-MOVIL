package com.example.nova_movil.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
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
            preferences[TOKEN_KEY] = token
            preferences[TOKEN_TYPE_KEY] = tokenType
            preferences[ROLE_ID_KEY] = roleId
            preferences[USER_EMAIL_KEY] = userEmail ?: ""
        }
    }

    suspend fun getToken(): String? {
        return context.dataStore.data.first()[TOKEN_KEY]
    }

    suspend fun getTokenType(): String? {
        return context.dataStore.data.first()[TOKEN_TYPE_KEY]
    }

    suspend fun getRoleId(): Int? {
        return context.dataStore.data.first()[ROLE_ID_KEY]
    }

    suspend fun clearSession() {
        context.dataStore.edit { it.clear() }
    }
}