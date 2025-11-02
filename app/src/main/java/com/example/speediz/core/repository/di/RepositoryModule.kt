package com.example.speediz.core.repository.di

import com.example.speediz.core.repository.ExpressRepository
import com.example.speediz.core.repository.ExpressRepositoryImpl
import com.example.speediz.core.repository.SignInRepository
import com.example.speediz.core.repository.SignInRepositoryImpl
import com.example.speediz.core.repository.SignUpRepository
import com.example.speediz.core.repository.SignUpRepositoryImpl
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

    @Binds
    @Singleton
    abstract fun bindSignUpRepository(
        impl: SignUpRepositoryImpl
    ): SignUpRepository

    @Binds
    @Singleton
    abstract fun bindExpressRepository(
        impl: ExpressRepositoryImpl
    ): ExpressRepository
}
