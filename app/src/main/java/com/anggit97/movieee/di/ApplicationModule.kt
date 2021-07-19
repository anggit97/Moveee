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
package com.anggit97.movieee.di

import android.content.Context
import com.anggit97.core.device.ImageUriProvider
import com.anggit97.core.notification.NotificationBuilder
import com.anggit97.core.settings.AppSettings
import com.anggit97.movieee.device.ImageUriProviderImpl
import com.anggit97.movieee.notification.NotificationBuilderImpl
import com.anggit97.movieee.settings.AppSettingsImpl
import com.anggit97.session.SessionManagerStore
import com.anggit97.session.SessionManagerStoreImpl
import com.anggit97.theme.ThemeOptionManager
import com.anggit97.theme.ThemeOptionStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {


    @Singleton
    @Provides
    fun provideThemeOptionManager(
        appSettings: AppSettings
    ): ThemeOptionManager = ThemeOptionManager(object : ThemeOptionStore {

        override fun save(option: String) {
            appSettings.themeOption = option
        }

        override fun restore(): String {
            return appSettings.themeOption
        }
    })

    @Singleton
    @Provides
    fun provideSessionManager(
        @ApplicationContext context: Context
    ): SessionManagerStore {
        return SessionManagerStoreImpl(context)
    }

    @Singleton
    @Provides
    fun provideImageUriProvider(
        @ApplicationContext context: Context
    ): ImageUriProvider {
        return ImageUriProviderImpl(context)
    }

    @Singleton
    @Provides
    fun provideAppSettings(
        @ApplicationContext context: Context
    ): AppSettings = AppSettingsImpl(context)

    @Singleton
    @Provides
    fun provideNotificationBuilder(@ApplicationContext context: Context): NotificationBuilder =
        NotificationBuilderImpl(context)
}
