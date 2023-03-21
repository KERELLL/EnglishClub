package ru.rychkovkirill.englishclub

import android.app.Application
import ru.rychkovkirill.englishclub.di.AppComponent
import ru.rychkovkirill.englishclub.di.DaggerAppComponent

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appContext(this)
            .build()
        appComponent.inject(this)
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}