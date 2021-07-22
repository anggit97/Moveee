package com.anggit97.session

import androidx.lifecycle.*
import com.anggit97.core.ui.EventLiveData
import com.anggit97.core.ui.MutableEventLiveData
import com.anggit97.model.domain.account.AccountUseCase
import com.anggit97.model.model.Account
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Anggit Prayogo on 19,July,2021
 * GitHub : https://github.com/anggit97
 */
@HiltViewModel
class SessionViewModel @Inject constructor(
    private val sessionManagerStore: SessionManagerStore,
    private val accountUseCase: AccountUseCase
) : ViewModel() {

    private val _authenticated = MutableLiveData<Boolean>()
    val authenticated: LiveData<Boolean>
        get() = _authenticated

    private val _sessionId = MutableLiveData<String>()
    private val sessionId: LiveData<String>
        get() = _sessionId

    private val _logout = MutableLiveData<Any>()
    val logout: LiveData<Any>
        get() = _logout

    val account: LiveData<Account> = sessionId.switchMap {
        liveData {
            try {
                val account = accountUseCase.getAccount()
                emit(account)
            }catch (e: Exception){
                Timber.e(e)
            }
        }
    }

    init {
        getSession()
    }

    private fun getSession() {
        viewModelScope.launch {
            Timber.d("START!")
            val sessionId = sessionManagerStore.getSessionId()
            val login = sessionManagerStore.isLogin()
            combine(sessionId, login) { session, login ->
                Session(
                    authenticated = session.isNotEmpty() && login,
                    sessionId = session
                )
            }.collect { result ->
                _authenticated.value = result.authenticated
                _sessionId.value = result.sessionId
            }
        }
    }

    fun isAuthenticated() = authenticated.value ?: false

    fun logout() {
        viewModelScope.launch {
            sessionManagerStore.logout()
            getSession()
            _logout.value = true
        }
    }
}

data class Session(val authenticated: Boolean, val sessionId: String)
