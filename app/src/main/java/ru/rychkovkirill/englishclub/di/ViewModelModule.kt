package ru.rychkovkirill.englishclub.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.rychkovkirill.englishclub.ui.admin.users.UsersViewModel
import ru.rychkovkirill.englishclub.ui.user.auth.AuthViewModel
import ru.rychkovkirill.englishclub.ui.user.mainpage.news.NewsViewModel

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    fun bindAuthViewModel(authViewModel : AuthViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UsersViewModel::class)
    fun bindUsersViewModel(usersViewModel: UsersViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel::class)
    fun bindNewsViewModel(newsViewModel: NewsViewModel) : ViewModel
}