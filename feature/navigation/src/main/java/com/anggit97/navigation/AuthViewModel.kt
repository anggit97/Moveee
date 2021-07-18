package com.anggit97.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


/**
 * Created by Anggit Prayogo on 18,July,2021
 * GitHub : https://github.com/anggit97
 */
@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

    private val _urlAuth = MutableLiveData<String>()
    val urlAuth: LiveData<String>
        get() = _urlAuth

    private val _resultAuth = MutableLiveData<Boolean>()
    val resultAuth: LiveData<Boolean>
        get() = _resultAuth

    fun setAuthUrl(url: String) {
        _urlAuth.value = url
    }

    fun setResultAuth(value: Boolean) {
        _resultAuth.value = value
    }
}