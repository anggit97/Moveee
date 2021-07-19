package com.anggit97.auth

import androidx.lifecycle.*
import com.anggit97.model.domain.auth.AuthUseCase
import com.anggit97.model.model.RequestToken
import com.anggit97.model.model.SessionId
import com.anggit97.model.model.SessionIdParam
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Anggit Prayogo on 18,July,2021
 * GitHub : https://github.com/anggit97
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    private val requestToken = MutableLiveData<String>()

    private val _sessionId = MutableLiveData<SessionId>()
    val sessionId: LiveData<SessionId>
        get() = _sessionId

    fun getRequestToken() = liveData {
        val result = authUseCase.getRequestToken()
        requestToken.value = result.requestToken
        emit(result)
    }

    fun createSessionId(){
        viewModelScope.launch {
            val result = authUseCase.createSessionId(request = SessionIdParam(requestToken.value))
            _sessionId.value = result
        }
    }
}