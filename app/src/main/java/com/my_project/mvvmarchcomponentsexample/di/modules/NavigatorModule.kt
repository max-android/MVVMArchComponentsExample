package ru.electronicstores.notebookstore.di.modules

import dagger.Module
import dagger.Provides
import ru.electronicstores.notebookstore.ui.common.FragmentNavigator
import javax.inject.Singleton


@Module
class NavigatorModule {

    @Provides
    @Singleton
    fun provideNavigator(): FragmentNavigator = FragmentNavigator()

}