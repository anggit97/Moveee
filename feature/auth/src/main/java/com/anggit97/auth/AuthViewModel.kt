package com.anggit97.auth

import androidx.lifecycle.*
import com.anggit97.model.domain.auth.AuthUseCase
import com.anggit97.model.model.RequestToken
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

    fun getRequestToken() = liveData {
        val result = authUseCase.getRequestToken()
        emit(result)
    }
}