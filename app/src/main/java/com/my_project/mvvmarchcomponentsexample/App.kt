package com.my_project.mvvmarchcomponentsexample

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.my_project.mvvmarchcomponentsexample.di.components.AppComponent
import com.my_project.mvvmarchcomponentsexample.di.components.DaggerAppComponent
import ru.electronicstores.notebookstore.di.modules.NavigatorModule
import ru.electronicstores.notebookstore.di.modules.RepositoryModule
import ru.electronicstores.notebookstore.di.modules.ServiceModule

class App : Application(){

    companion object {
        @JvmStatic
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .serviceModule(ServiceModule())
            .repositoryModule(RepositoryModule())
            .navigatorModule(NavigatorModule())
            .build()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}