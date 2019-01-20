package com.my_project.mvvmarchcomponentsexample.ui.common


object BookConverter {

    fun getNumberHeroes(characters: List<String>): List<String> {
        val list = arrayListOf<String>()
        characters.forEach { list.add(it.replace(Constants.CONVERTER_URL, Constants.CONVERTER_DEFAULT_VALUE)) }
        return list
    }

    fun convertDate(date: String): String {
        return date.replace(Constants.CONVERTER_DATE, Constants.CONVERTER_DEFAULT_VALUE)
    }
}