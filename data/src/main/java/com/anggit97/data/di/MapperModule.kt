package com.anggit97.data.di

import com.anggit97.data.repository.internal.mapper.auth.RequestTokenMapper
import com.anggit97.data.repository.internal.mapper.auth.RequestTokenMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Created by Anggit Prayogo on 24,July,2021
 * GitHub : https://github.com/anggit97
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class MapperModule {

    @Binds
    @Singleton
    abstract fun provideRequestTokenMapper(requestTokenMapperImpl: RequestTokenMapperImpl): RequestTokenMapper
}