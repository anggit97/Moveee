/*
 * Copyright 2021 SOUP
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.anggit97.movieee.settings

import android.content.Context
import androidx.preference.PreferenceManager
import com.anggit97.core.settings.AppSettings
import kotlinx.coroutines.flow.Flow

class AppSettingsImpl(context: Context) : AppSettings {

    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    private val themeOptionPref = StringPreference(prefs, "theme_option", "")
    override var themeOption by themeOptionPref
    override fun getThemeOptionFlow(): Flow<String> {
        return themeOptionPref.asFlow()
    }
}
