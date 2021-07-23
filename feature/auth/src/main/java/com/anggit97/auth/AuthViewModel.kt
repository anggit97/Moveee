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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
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

    private val _sessionId = MutableLiveData<SessionIdState>()
    val sessionId: LiveData<SessionIdState>
        get() = _sessionId

    private val _requestToken = MutableLiveData<RequestTokenState>()
    val requestToken: LiveData<RequestTokenState>
        get() = _requestToken

    private var requestTokenTemp: String? = null

    init {
        viewModelScope.launch {
            getRequestToken()
        }
    }

    private fun getRequestToken() {
        viewModelScope.launch {
            authUseCase.getRequestToken()
                .catch { _requestToken.value = RequestTokenState.Error(it) }
                .onStart { _requestToken.value = RequestTokenState.ShowLoading }
                .onCompletion { _requestToken.value = RequestTokenState.HideLoading }
                .collect { result ->
                    requestTokenTemp = result.requestToken
                    _requestToken.value = RequestTokenState.Success(result)
                }
        }
    }

    suspend fun createSessionId() {
        requestTokenTemp?.let { requestToken ->
            authUseCase.createSessionId(request = SessionIdParam(requestToken))
                .catch { _sessionId.value = SessionIdState.Error(it) }
                .onStart { _sessionId.value = SessionIdState.ShowLoading }
                .onCompletion { _sessionId.value = SessionIdState.HideLoading }
                .collect { result ->
                    _sessionId.value = SessionIdState.Success(result)
                }
        }
    }
}

sealed class RequestTokenState {
    data class Success(val data: RequestToken) : RequestTokenState()
    data class Error(val error: Throwable) : RequestTokenState()
    object ShowLoading : RequestTokenState()
    object HideLoading : RequestTokenState()
}

sealed class SessionIdState {
    data class Success(val data: SessionId) : SessionIdState()
    data class Error(val error: Throwable) : SessionIdState()
    object ShowLoading : SessionIdState()
    object HideLoading : SessionIdState()
}
