package com.anggit97.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anggit97.model.domain.account.AccountUseCase
import com.anggit97.model.model.Account
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Anggit Prayogo on 21,July,2021
 * GitHub : https://github.com/anggit97
 */
@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountUseCase: AccountUseCase
) : ViewModel() {

    private val _account = MutableLiveData<Account>()
    val account: LiveData<Account>
        get() = _account

    init {
        getAccount()
    }

    private fun getAccount() {
        viewModelScope.launch {
            val response = accountUseCase.getAccount()
            _account.value = response
        }
    }
}