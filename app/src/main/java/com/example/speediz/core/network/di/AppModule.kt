package com.example.speediz.core.network.di

import android.content.Context
import com.example.speediz.core.application.MySharePreferences
import com.example.speediz.core.network.interceptor.NetworkConnectionInterceptor
import com.example.speediz.core.network.services.ApiService
import com.example.speediz.core.repository.SignInRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideNetworkConnectionInterceptor(
        @ApplicationContext context: Context
    ) : NetworkConnectionInterceptor = NetworkConnectionInterceptor(context)

    @Provides
    @Singleton
    fun provideApiNetwork(
        @ApplicationContext context: Context,
        interceptor: NetworkConnectionInterceptor
    ) : ApiService = ApiService(context, interceptor)

    @Provides
    @Singleton
    fun provideSharePreference(
        @ApplicationContext context: Context
    ) : MySharePreferences = MySharePreferences(context)
}