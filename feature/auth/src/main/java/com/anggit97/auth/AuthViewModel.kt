package com.anggit97.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anggit97.model.domain.auth.AuthUseCase
import com.anggit97.model.model.RequestToken
import com.anggit97.model.model.SessionId
import com.anggit97.model.model.SessionIdParam
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Anggit Prayogo on 18,July,2021
 * GitHub : https://github.com/anggit97
 */
@ExperimentalCoroutinesApi
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    private val _sessionId = MutableLiveData<SessionId>()
    val sessionId: LiveData<SessionId>
        get() = _sessionId

    private val _requestToken = MutableLiveData<RequestToken>()
    val requestToken: LiveData<RequestToken>
        get() = _requestToken

    init {
        viewModelScope.launch {
            getRequestToken()
        }
    }

    private fun getRequestToken() {
        viewModelScope.launch {
            authUseCase.getRequestToken()
                .catch { Timber.e(it) }
                .onStart { Timber.d("START : REQUEST TOKEN") }
                .onCompletion { Timber.d("COMPLETE : ") }
                .onEmpty { Timber.d("EMPTY : ") }
                .collect {
                    _requestToken.value = it
                }
        }
    }

    suspend fun createSessionId() =
        authUseCase.createSessionId(request = SessionIdParam(requestToken.value?.requestToken))
            .catch { Timber.e(it) }
            .collect { result ->
                _sessionId.value = result
            }
}