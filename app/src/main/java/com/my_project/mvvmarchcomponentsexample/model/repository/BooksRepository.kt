package com.my_project.mvvmarchcomponentsexample.model.repository

import com.my_project.mvvmarchcomponentsexample.model.entities.Book
import com.my_project.mvvmarchcomponentsexample.model.entities.Hero
import com.my_project.mvvmarchcomponentsexample.model.network.BooksService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BooksRepository @Inject constructor(private val service: BooksService) {

    fun books(): Single<List<Book>> = service
        .booksRequest()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun heroes(hero: String): Single<Hero> = service
        .heroesRequest(hero)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}