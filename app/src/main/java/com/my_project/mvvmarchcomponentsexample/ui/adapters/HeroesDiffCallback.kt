package com.my_project.mvvmarchcomponentsexample.ui.adapters

import android.support.v7.util.DiffUtil
import com.my_project.mvvmarchcomponentsexample.model.entities.Hero

class HeroesDiffCallback(
    private val oldHeroes: MutableList<Hero> = mutableListOf(),
    private val newHeroes: MutableList<Hero> = mutableListOf()
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldHeroes.size

    override fun getNewListSize(): Int = newHeroes.size

    override fun areContentsTheSame(p0: Int, p1: Int): Boolean = oldHeroes[p0] == newHeroes[p1]

    override fun areItemsTheSame(p0: Int, p1: Int): Boolean=  oldHeroes[p0].name == newHeroes[p1].name
}