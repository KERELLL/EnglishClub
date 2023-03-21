package ru.rychkovkirill.englishclub.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.rychkovkirill.englishclub.ui.user.auth.AuthViewModel

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    fun bindAuthViewModel(authViewModel : AuthViewModel) : ViewModel
}