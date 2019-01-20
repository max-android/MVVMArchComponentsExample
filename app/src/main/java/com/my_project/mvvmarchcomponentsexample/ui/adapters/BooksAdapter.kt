package com.my_project.mvvmarchcomponentsexample.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.my_project.mvvmarchcomponentsexample.R
import com.my_project.mvvmarchcomponentsexample.model.entities.Book
import com.my_project.mvvmarchcomponentsexample.ui.common.BookConverter

class BooksAdapter(val items: List<Book> = emptyList(), val itemClick: (Book) -> Unit) :
    RecyclerView.Adapter<BooksAdapter.BindingHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_book, parent, false)
        return BindingHolder(view)
    }

    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        val book = items[position]
        holder.bindTo(book, itemClick)
    }

    override fun getItemCount() = items.size

    inner class BindingHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var nameBookTextView: TextView = itemView.findViewById(R.id.nameBookTextView)
        private var nameAuthorTextView: TextView = itemView.findViewById(R.id.nameAuthorTextView)
        private var valueReleasedTextView: TextView = itemView.findViewById(R.id.valueReleasedTextView)

        fun bindTo(book: Book, itemClick: (Book) -> Unit) = with(book) {
            nameBookTextView.text = name
            nameAuthorTextView.text = publisher
            valueReleasedTextView.text = BookConverter.convertDate(released)
            itemView.setOnClickListener { itemClick(book) }
        }
    }
}