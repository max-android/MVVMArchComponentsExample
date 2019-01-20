package com.my_project.mvvmarchcomponentsexample.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
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
import com.my_project.mvvmarchcomponentsexample.model.entities.Hero
import com.my_project.mvvmarchcomponentsexample.ui.adapters.HeroesAdapter
import com.my_project.mvvmarchcomponentsexample.ui.adapters.RecyclerOnScrollListener
import com.my_project.mvvmarchcomponentsexample.ui.base.BaseFragment
import com.my_project.mvvmarchcomponentsexample.ui.common.BookConverter
import com.my_project.mvvmarchcomponentsexample.ui.common.Constants
import com.my_project.mvvmarchcomponentsexample.viewmodel.SharedViewModel
import com.my_project.mvvmarchcomponentsexample.viewmodel.ViewState


class HeroesFragment : BaseFragment() {

    private lateinit var viewModel: SharedViewModel
    private lateinit var heroesRecyclerView: RecyclerView
    private lateinit var heroesAdapter: HeroesAdapter
    private var heroesLayoutManager: LinearLayoutManager =
        LinearLayoutManager(this@HeroesFragment.context, LinearLayoutManager.VERTICAL, false)
    private lateinit var heroesLoadingView: FrameLayout
    private lateinit var emptyView: TextView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var actionBar: ActionBar? = null
    private var heroClickCallback: HeroClickCallback? = null
    private var book: Book? = null
    private var heroesList: MutableList<Hero> = mutableListOf()
    private var paginationCounter: Int = 0
    private var countItemsPage: Int = 10

    companion object {
        fun newInstance(): HeroesFragment {
            return HeroesFragment()
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.heroes_fragment
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is HeroClickCallback) {
            heroClickCallback = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents(view)
        viewModel = ViewModelProviders.of(activity!!).get(SharedViewModel::class.java)

        if (savedInstanceState != null) {
            paginationCounter = savedInstanceState.getInt(Constants.PAG_COUNTER)
            viewModel.heroes.observe(this, Observer<List<Hero>> { heroesAdapter.setItems(it!!) })
        }

        viewModel.selectBook.observe(this, Observer<Book> { selectBook ->
            run {
                book = selectBook
                actionBar?.title = selectBook?.name
                loadData(selectBook)
            }
        })

        viewModel.character.observe(this, Observer<ViewState> { showData(it) })

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.setHeroes(heroesAdapter.getItems())
        outState.putInt(Constants.PAG_COUNTER, paginationCounter)
    }

    override fun onDetach() {
        super.onDetach()
        heroClickCallback = null
    }

    private fun initComponents(view: View) {
        heroesRecyclerView = view.findViewById(R.id.heroesRecyclerView)
        heroesLoadingView = view.findViewById(R.id.heroesLoadingView)
        emptyView = view.findViewById(R.id.emptyView)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN)
        actionBar = (activity as MainActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)

        heroesAdapter = HeroesAdapter(heroesList) { hero ->
            run {
                viewModel.setHero(hero)
                heroClickCallback!!.heroClick()
            }
        }

        heroesRecyclerView.apply {
            layoutManager = heroesLayoutManager
            addItemDecoration(DividerItemDecoration(this@HeroesFragment.context, LinearLayout.VERTICAL))
            addOnScrollListener(recyclerScrollListener)
            adapter = heroesAdapter
        }
        swipeRefreshLayout.setOnRefreshListener { refreshScreen() }
    }

    private fun refreshScreen() {
        paginationCounter = 0
        swipeRefreshLayout.isRefreshing = true
        heroesAdapter.clear()
        loadData(book)
        swipeRefreshLayout.isRefreshing = false
    }

    private fun loadData(book: Book?) {
        if (book != null) {
            val totalSize = book.characters.size
            paginationCounter += countItemsPage
            val from = paginationCounter - countItemsPage

            if (paginationCounter < totalSize) {
                for (i in from until paginationCounter) {
                    viewModel.loadHeroes(BookConverter.getNumberHeroes(book.characters)[i])
                }
            } else {
                for (i in from until totalSize) {
                    viewModel.loadHeroes(BookConverter.getNumberHeroes(book.characters)[i])
                }
            }
        }
    }

    private fun showData(viewState: ViewState?) {
        viewState?.let {
            when (it) {
                is ViewState.Loading -> showLoading()
                is ViewState.SuccessHero -> showHeroes(it.hero)
                is ViewState.Error -> showError()
            }
        }
    }

    private fun showLoading() {
        heroesLoadingView.visibility = View.VISIBLE
        emptyView.visibility = View.GONE
    }

    private fun showHeroes(hero: Hero) {
        heroesLoadingView.visibility = View.GONE
        emptyView.visibility = View.GONE
        heroesRecyclerView.apply {
            heroesAdapter.addData(hero)
        }
    }

    private fun showError() {
        heroesLoadingView.visibility = View.GONE
        emptyView.visibility = View.VISIBLE
    }

    private val recyclerScrollListener = object : RecyclerOnScrollListener(heroesLayoutManager) {
        override fun onLoadMore() {
            loadData(book)
        }
    }

    interface HeroClickCallback {
        fun heroClick()
    }
}