package com.example.speediz.core.repository.di

import com.example.speediz.core.repository.SignInRepository
import com.example.speediz.core.repository.SignInRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindSignInRepository(
        impl: SignInRepositoryImpl
    ): SignInRepository
}
