package com.anggit97.navigation

import androidx.lifecycle.ViewModel
import com.anggit97.core.ui.EventLiveData
import com.anggit97.core.ui.MutableEventLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


/**
 * Created by Anggit Prayogo on 04,July,2021
 * GitHub : https://github.com/anggit97
 */
@HiltViewModel
class SystemViewModel @Inject constructor() : ViewModel() {

    private val _systemEvent = MutableEventLiveData<SystemEvent>()
    val systemEvent: EventLiveData<SystemEvent>
        get() = _systemEvent

    fun openNavigationMenu() {
        _systemEvent.event = SystemEvent.OpenDrawerMenuUiEvent
    }
}