package com.my_project.mvvmarchcomponentsexample.viewmodel

import com.my_project.mvvmarchcomponentsexample.model.entities.Book
import com.my_project.mvvmarchcomponentsexample.model.entities.Hero

sealed class ViewState {
    class SuccessBooks(val books: List<Book>): ViewState()
    class SuccessHero(val hero: Hero): ViewState()
    class Loading: ViewState()
    class Error(val error: String): ViewState()
}