package com.anggit97.session

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anggit97.core.ui.EventLiveData
import com.anggit97.core.ui.MutableEventLiveData
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

    private val _authenticated = MutableEventLiveData<Boolean>()
    val authenticated: EventLiveData<Boolean>
        get() = _authenticated

    private val _logout = MutableLiveData<Any>()
    val logout: LiveData<Any>
        get() = _logout

    init {
        getSession()
    }

    private fun getSession() {
        viewModelScope.launch {
            val sessionId = sessionManagerStore.getSessionId()
            val login = sessionManagerStore.isLogin()
            combine(sessionId, login) { session, login ->
                session.isNotEmpty() && login
            }.collect { result ->
                _authenticated.event = result
            }
        }
    }

    fun isAuthenticated() = authenticated.value?.peekContent() ?: false

    fun logout() {
        viewModelScope.launch {
            sessionManagerStore.logout()
            getSession()
            _logout.value = true
        }
    }
}
