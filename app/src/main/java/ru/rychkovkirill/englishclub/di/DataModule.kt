package ru.rychkovkirill.englishclub.di

import dagger.Binds
import dagger.Module
import ru.rychkovkirill.englishclub.data.repository.AuthRepositoryImpl
import ru.rychkovkirill.englishclub.domain.repository.AuthRepository
import javax.inject.Singleton


@Module
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl) : AuthRepository
}