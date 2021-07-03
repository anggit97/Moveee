package com.anggit97.core.di

import android.content.Context
import com.anggit97.core.util.NetworkCheckerHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Created by Anggit Prayogo on 03,July,2021
 * GitHub : https://github.com/anggit97
 */
@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun provideNetworkCheckerHelper(
        @ApplicationContext context: Context
    ) = NetworkCheckerHelper(context)
}