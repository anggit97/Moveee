package com.anggit97.auth

import androidx.lifecycle.*
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
                .catch { RequestTokenState.Error(it) }
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
                .catch { Timber.e(it) }
                .collect { result ->
                    _sessionId.value = result
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
