package com.my_project.mvvmarchcomponentsexample.di.components

import com.my_project.mvvmarchcomponentsexample.ui.BooksFragment
import com.my_project.mvvmarchcomponentsexample.ui.DetailHeroFragment
import com.my_project.mvvmarchcomponentsexample.ui.HeroesFragment
import com.my_project.mvvmarchcomponentsexample.ui.MainActivity
import com.my_project.mvvmarchcomponentsexample.viewmodel.SharedViewModel
import dagger.Component
import ru.electronicstores.notebookstore.di.modules.NavigatorModule
import ru.electronicstores.notebookstore.di.modules.RepositoryModule
import ru.electronicstores.notebookstore.di.modules.ServiceModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        ServiceModule::class,
        RepositoryModule::class,
        NavigatorModule::class
    ]
)
interface AppComponent {
    fun injectMainActivity(mainActivity: MainActivity)
    fun injectBooksFragment(homeFragment: BooksFragment)
    fun injectHeroesFragment(homeFragment: HeroesFragment)
    fun injectDetailHeroFragment(homeFragment: DetailHeroFragment)
    fun injectSharedViewModel(sharedViewModel: SharedViewModel)
}