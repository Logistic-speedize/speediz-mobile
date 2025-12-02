package com.example.speediz.core.repository.di

import com.example.speediz.core.repository.ExpressRepository
import com.example.speediz.core.repository.ExpressRepositoryImpl
import com.example.speediz.core.repository.InvoiceRepository
import com.example.speediz.core.repository.InvoiceRepositoryImpl
import com.example.speediz.core.repository.PackageHistoryRepository
import com.example.speediz.core.repository.PackageHistoryRepositoryImpl
import com.example.speediz.core.repository.PackageRepository
import com.example.speediz.core.repository.PackageRepositoryImpl
import com.example.speediz.core.repository.SignInRepository
import com.example.speediz.core.repository.SignInRepositoryImpl
import com.example.speediz.core.repository.SignUpRepository
import com.example.speediz.core.repository.SignUpRepositoryImpl
import com.example.speediz.core.repository.VendorRepository
import com.example.speediz.core.repository.VendorRepositoryImpl
import com.google.android.gms.common.SignInButton
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

    @Binds
    @Singleton
    abstract fun bindVendorRepository(
        impl: VendorRepositoryImpl
    ): VendorRepository

    @Binds
    @Singleton
    abstract fun bindPackageRepository(
        impl: PackageRepositoryImpl
    ): PackageRepository

    @Binds
    @Singleton
    abstract fun bindPackageHistoryRepository(
        impl: PackageHistoryRepositoryImpl
    ) : PackageHistoryRepository

    @Binds
    @Singleton
    abstract fun bindInvoiceRepository(
        impl: InvoiceRepositoryImpl
    ) : InvoiceRepository
}
