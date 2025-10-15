package com.example.speediz.core.network.di

import android.content.Context
import com.example.speediz.core.network.interceptor.NetworkConnectionInterceptor
import com.example.speediz.core.network.services.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

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
}