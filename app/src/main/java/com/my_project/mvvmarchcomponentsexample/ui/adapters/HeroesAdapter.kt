package com.my_project.mvvmarchcomponentsexample.ui.adapters

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.my_project.mvvmarchcomponentsexample.R
import com.my_project.mvvmarchcomponentsexample.model.entities.Hero

class HeroesAdapter(private var items: MutableList<Hero> = mutableListOf(), private val itemClick: (Hero) -> Unit) :
    RecyclerView.Adapter<HeroesAdapter.HerosHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HerosHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_hero, parent, false)
        return HerosHolder(view)
    }

    override fun onBindViewHolder(holder: HerosHolder, position: Int) {
        val hero = items[position]
        holder.bindTo(hero, itemClick)
    }

    override fun getItemCount() = items.size

    fun getItems() = items

    fun setItems(list: List<Hero>) {
        items.addAll(list)
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    fun addData(newData: Hero) {
        val updatedHeroesList: MutableList<Hero> = mutableListOf()
        updatedHeroesList.addAll(items)
        updatedHeroesList.add(newData)
        val diffCallback = HeroesDiffCallback(items, updatedHeroesList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items = updatedHeroesList
        diffResult.dispatchUpdatesTo(this)
    }

    inner class HerosHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var heroTextView: TextView = itemView.findViewById(R.id.heroTextView)
        private var genderTextView: TextView = itemView.findViewById(R.id.genderTextView)

        fun bindTo(hero: Hero, itemClick: (Hero) -> Unit) = with(hero) {
            heroTextView.text = name
            genderTextView.text = gender
            itemView.setOnClickListener { itemClick(hero) }
        }
    }
}