package ru.rychkovkirill.englishclub.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.rychkovkirill.englishclub.App
import ru.rychkovkirill.englishclub.ui.SplashActivity
import ru.rychkovkirill.englishclub.ui.user.auth.LoginFragment
import ru.rychkovkirill.englishclub.ui.user.auth.RegisterFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [DataModule::class, NetworkModule::class, ViewModelModule::class]
)
interface AppComponent {

    fun inject(app: App)

    fun inject(splashActivity: SplashActivity)

    fun inject(loginFragment: LoginFragment)

    fun inject(registerFragment: RegisterFragment)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun appContext(context: Context): Builder

    }
}