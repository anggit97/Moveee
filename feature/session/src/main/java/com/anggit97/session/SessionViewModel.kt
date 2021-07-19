package com.anggit97.session

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Anggit Prayogo on 19,July,2021
 * GitHub : https://github.com/anggit97
 */
@HiltViewModel
class SessionViewModel @Inject constructor(
    private val sessionManagerStore: SessionManagerStore
) : ViewModel() {

    private val _authenticated = MutableLiveData<Boolean>()
    val authenticated: LiveData<Boolean>
        get() = _authenticated

    init {
        viewModelScope.launch {
            val sessionId = sessionManagerStore.getSessionId()
            val login = sessionManagerStore.isLogin()
            combine(sessionId, login) { session, login ->
                session.isNotEmpty() && login
            }.collect { result ->
                _authenticated.value = result
            }
        }
    }

    fun isAuthenticated() = authenticated.value ?: false
}
