package com.my_project.mvvmarchcomponentsexample.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.view.View
import android.widget.TextView
import com.my_project.mvvmarchcomponentsexample.R
import com.my_project.mvvmarchcomponentsexample.model.entities.Hero
import com.my_project.mvvmarchcomponentsexample.ui.base.BaseFragment
import com.my_project.mvvmarchcomponentsexample.viewmodel.SharedViewModel


class DetailHeroFragment : BaseFragment() {

    private lateinit var viewModel: SharedViewModel
    private var actionBar: ActionBar? = null

    companion object {
        fun newInstance(): DetailHeroFragment {
            return DetailHeroFragment()
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.detail_hero_fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
        viewModel = ViewModelProviders.of(activity!!).get(SharedViewModel::class.java)
        viewModel.selectHero.observe(this, Observer<Hero> { selectHero -> showHero(selectHero, view) })
    }

    private fun initComponents() {
        actionBar = (activity as MainActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun showHero(hero: Hero?, view: View) {
        (activity as MainActivity).supportActionBar?.title = hero?.name
        view.findViewById<TextView>(R.id.nameHeroTextView).text = hero?.name
        view.findViewById<TextView>(R.id.valueGenderTextView).text = hero?.gender
        view.findViewById<TextView>(R.id.valueCultureTextView).text = hero?.culture
        view.findViewById<TextView>(R.id.valueBornTextView).text = hero?.born
    }
}