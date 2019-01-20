package com.my_project.mvvmarchcomponentsexample.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.my_project.mvvmarchcomponentsexample.App
import com.my_project.mvvmarchcomponentsexample.model.entities.Book
import com.my_project.mvvmarchcomponentsexample.model.entities.Hero
import com.my_project.mvvmarchcomponentsexample.model.repository.BooksRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SharedViewModel : ViewModel {

    @Inject
    lateinit var repository: BooksRepository
    private val subscriptions = CompositeDisposable()
    internal val listBooks = MutableLiveData<ViewState>()
    internal val selectBook = MutableLiveData<Book>()
    internal val selectHero = MutableLiveData<Hero>()
    internal val character = MutableLiveData<ViewState>()
    internal val heroes = MutableLiveData<List<Hero>>()

    constructor() : super() {
        App.appComponent.injectSharedViewModel(this)
        listBooks.value = ViewState.SuccessBooks(arrayListOf())
    }

    fun loadBooks() {
        subscriptions.add(
            repository.books()
                .doOnSubscribe { listBooks.value = ViewState.Loading() }
                .subscribe(
                    { books -> listBooks.value = ViewState.SuccessBooks(books) },
                    { error -> listBooks.value = ViewState.Error(error.message!!) }
                )
        )
    }

    fun loadHeroes(person: String) {
        subscriptions.add(
            repository.heroes(person)
                .doOnSubscribe { character.value = ViewState.Loading() }
                .subscribe(
                    { hero -> character.value = ViewState.SuccessHero(hero) },
                    { error -> character.value = ViewState.Error(error.message!!) }
                )
        )
    }

    fun setBook(book: Book) {
        selectBook.value = book
    }

    fun setHero(hero: Hero) {
        selectHero.value = hero
    }

    fun setHeroes(list: List<Hero>) {
        heroes.value = list
    }

    override fun onCleared() {
        subscriptions.clear()
    }
}