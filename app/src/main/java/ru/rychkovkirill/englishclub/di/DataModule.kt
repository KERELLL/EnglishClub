package ru.rychkovkirill.englishclub.di

import dagger.Binds
import dagger.Module
import ru.rychkovkirill.englishclub.data.repository.AuthRepositoryImpl
import ru.rychkovkirill.englishclub.data.repository.MainRepositoryImpl
import ru.rychkovkirill.englishclub.data.repository.UserRepositoryImpl
import ru.rychkovkirill.englishclub.domain.repository.AuthRepository
import ru.rychkovkirill.englishclub.domain.repository.MainRepository
import ru.rychkovkirill.englishclub.domain.repository.UserRepository
import javax.inject.Singleton


@Module
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl) : AuthRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl) : UserRepository

    @Binds
    @Singleton
    abstract fun bindMainRepository(impl: MainRepositoryImpl) : MainRepository
}