package com.anggit97.session

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SessionManagerStoreImpl @Inject constructor(
    private val context: Context
) : SessionManagerStore {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "sessions")

    val SESSION_ID = stringPreferencesKey("session_id")
    override fun getSessionId(): Flow<String> = context.dataStore.data.map { it[SESSION_ID] ?: "" }
    override suspend fun setSessionId(sessionId: String) {
        context.dataStore.edit { it[SESSION_ID] = sessionId }
    }


    val LOGIN = booleanPreferencesKey("login")
    override fun isLogin(): Flow<Boolean> = context.dataStore.data.map { it[LOGIN] ?: false }
    override suspend fun setLogin(login: Boolean) {
        context.dataStore.edit { it[LOGIN] = login }
    }
}