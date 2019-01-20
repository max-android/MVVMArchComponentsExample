package com.my_project.mvvmarchcomponentsexample.model.network

import com.my_project.mvvmarchcomponentsexample.model.entities.Book
import com.my_project.mvvmarchcomponentsexample.model.entities.Hero
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface BooksService {
    @GET("api/books")
    fun booksRequest(): Single<List<Book>>

    @GET("api/characters/{user}")
    fun heroesRequest(@Path("user")user:String): Single<Hero>
}