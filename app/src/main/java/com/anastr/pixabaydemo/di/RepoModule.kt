package com.anastr.pixabaydemo.di

import com.anastr.pixabaydemo.data.repository.FakeLoginRepository
import com.anastr.pixabaydemo.data.repository.FakeRegisterRepository
import com.anastr.pixabaydemo.domain.repository.LoginRepository
import com.anastr.pixabaydemo.domain.repository.RegisterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    @Singleton
    abstract fun bindLoginRepository(
        fakeLoginRepository: FakeLoginRepository
    ): LoginRepository

    @Binds
    @Singleton
    abstract fun bindRegisterRepository(
        fakeLoginRepository: FakeRegisterRepository
    ): RegisterRepository
}