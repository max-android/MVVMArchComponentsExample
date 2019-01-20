package com.my_project.mvvmarchcomponentsexample.ui.adapters

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView


abstract class RecyclerOnScrollListener(private val mLinearLayoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    private val visibleThreshold: Int = 3
    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        totalItemCount = mLinearLayoutManager.itemCount
        lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition()
        if (totalItemCount <= (lastVisibleItem + visibleThreshold)) {
            onLoadMore()
        }
    }

    abstract fun onLoadMore()
}