package com.anggit97.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.anggit97.core.settings.AppSettings
import com.anggit97.theme.ThemeOptionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject


/**
 * Created by Anggit Prayogo on 07,July,2021
 * GitHub : https://github.com/anggit97
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    themeOptionManager: ThemeOptionManager,
    appSettings: AppSettings
) : ViewModel() {

    val themeUiModel = appSettings.getThemeOptionFlow()
        .map { ThemeSettingUiModel(themeOptionManager.getCurrentOption()) }
        .distinctUntilChanged()
        .asLiveData()
}