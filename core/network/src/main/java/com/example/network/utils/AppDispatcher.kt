package com.example.network.utils
import javax.inject.Qualifier
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val appDispatcher: AppDispatchers)

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PrivateServiced

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PublicServiced

enum class AppDispatchers {
    Default,
    IO,
    Main,
}