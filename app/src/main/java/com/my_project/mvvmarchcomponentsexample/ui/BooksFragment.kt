package com.my_project.mvvmarchcomponentsexample.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.my_project.mvvmarchcomponentsexample.R
import com.my_project.mvvmarchcomponentsexample.model.entities.Book
import com.my_project.mvvmarchcomponentsexample.ui.adapters.BooksAdapter
import com.my_project.mvvmarchcomponentsexample.ui.base.BaseFragment
import com.my_project.mvvmarchcomponentsexample.viewmodel.SharedViewModel
import com.my_project.mvvmarchcomponentsexample.viewmodel.ViewState


class BooksFragment : BaseFragment() {

    private lateinit var viewModel: SharedViewModel
    private lateinit var booksRecyclerView: RecyclerView
    private lateinit var booksLoadingView: FrameLayout
    private lateinit var emptyView: TextView
    private var bookClickCallback: BookClickCallback? = null
    private var actionBar: ActionBar? = null

    companion object {
        fun newInstance(): BooksFragment {
            return BooksFragment()
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.books_fragment
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BookClickCallback) {
            bookClickCallback = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents(view)
        viewModel = ViewModelProviders.of(activity!!).get(SharedViewModel::class.java)
        viewModel.listBooks.observe(this, Observer<ViewState> { showData(it) })

    }

    override fun onDetach() {
        super.onDetach()
        bookClickCallback = null
    }

    private fun initComponents(view: View) {
        booksRecyclerView = view.findViewById(R.id.booksRecyclerView)
        booksLoadingView = view.findViewById(R.id.booksLoadingView)
        emptyView = view.findViewById(R.id.emptyView)
        actionBar = (activity as MainActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)
        actionBar?.setDisplayShowHomeEnabled(false)
        booksRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@BooksFragment.context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(this@BooksFragment.context, LinearLayout.VERTICAL))
        }
        (activity as MainActivity).supportActionBar?.title = getString(R.string.books)
    }

    private fun showData(viewState: ViewState?) {
        viewState?.let {
            when (it) {
                is ViewState.Loading -> showLoading()
                is ViewState.SuccessBooks -> showBooks(it.books)
                is ViewState.Error -> showError()
            }
        }
    }

    private fun showLoading() {
        booksLoadingView.visibility = View.VISIBLE
        emptyView.visibility = View.GONE
        booksRecyclerView.visibility = View.GONE
    }

    private fun showBooks(books: List<Book>?) {
        if (books == null) {
            return
        }

        if (books.isEmpty()) {
            viewModel.loadBooks()
        }
        booksLoadingView.visibility = View.GONE
        emptyView.visibility = View.GONE
        booksRecyclerView.apply {
            visibility = View.VISIBLE
            adapter = BooksAdapter(books) { book ->
                run {
                    viewModel.setBook(book)
                    bookClickCallback!!.bookClick()
                }
            }
        }
    }

    private fun showError() {
        booksLoadingView.visibility = View.GONE
        emptyView.visibility = View.VISIBLE
        booksRecyclerView.visibility = View.GONE
    }

    interface BookClickCallback {
        fun bookClick()
    }
}