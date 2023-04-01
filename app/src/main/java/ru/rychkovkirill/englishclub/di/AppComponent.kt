package ru.rychkovkirill.englishclub.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.rychkovkirill.englishclub.App
import ru.rychkovkirill.englishclub.ui.SplashActivity
import ru.rychkovkirill.englishclub.ui.admin.reservations.ReservationsFragment
import ru.rychkovkirill.englishclub.ui.admin.users.UserDetailsFragment
import ru.rychkovkirill.englishclub.ui.admin.users.UsersFragment
import ru.rychkovkirill.englishclub.ui.profile.ProfileEditFragment
import ru.rychkovkirill.englishclub.ui.profile.ProfileFragment
import ru.rychkovkirill.englishclub.ui.user.auth.LoginFragment
import ru.rychkovkirill.englishclub.ui.user.auth.RegisterFragment
import ru.rychkovkirill.englishclub.ui.user.mainpage.activities.ActivitiesDetailsFragment
import ru.rychkovkirill.englishclub.ui.user.mainpage.activities.ActivitiesFragment
import ru.rychkovkirill.englishclub.ui.user.mainpage.activities.CheckTaskFragment
import ru.rychkovkirill.englishclub.ui.user.mainpage.activities.CheckTaskListFragment
import ru.rychkovkirill.englishclub.ui.user.mainpage.awards.AwardsFragment
import ru.rychkovkirill.englishclub.ui.user.mainpage.nearestsessions.NearestSessionsDetailsFragment
import ru.rychkovkirill.englishclub.ui.user.mainpage.nearestsessions.NearestSessionsFragment
import ru.rychkovkirill.englishclub.ui.user.mainpage.news.NewsDetailsFragment
import ru.rychkovkirill.englishclub.ui.user.mainpage.news.NewsFragment
import ru.rychkovkirill.englishclub.ui.user.mainpage.sessions.SessionsDetailsFragment
import ru.rychkovkirill.englishclub.ui.user.mainpage.sessions.SessionsFragment
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

    fun inject(usersFragment: UsersFragment)

    fun inject(newsFragment: NewsFragment)
    
    fun inject(newsDetailsFragment: NewsDetailsFragment)

    fun inject(nearestSessionsFragment: NearestSessionsFragment)

    fun inject(activitiesFragment: ActivitiesFragment)

    fun inject(activitiesDetailsFragment: ActivitiesDetailsFragment)

    fun inject(userDetailsFragment: UserDetailsFragment)

    fun inject(profileFragment: ProfileFragment)

    fun inject(profileEditFragment: ProfileEditFragment)

    fun inject(reservationsFragment: ReservationsFragment)

    fun inject(sessionsFragment: SessionsFragment)

    fun inject(sessionsDetailsFragment: SessionsDetailsFragment)

    fun inject(awardsFragment: AwardsFragment)

    fun inject(checkTaskListFragment: CheckTaskListFragment)

    fun inject(checkTaskFragment: CheckTaskFragment)

    fun inject(nearestSessionsDetailsFragment: NearestSessionsDetailsFragment)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun appContext(context: Context): Builder

    }
}