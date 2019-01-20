package ru.electronicstores.notebookstore.di.modules

import com.my_project.mvvmarchcomponentsexample.model.network.BooksService
import com.my_project.mvvmarchcomponentsexample.model.repository.BooksRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideServiceRepository(service: BooksService): BooksRepository = BooksRepository(service)

}