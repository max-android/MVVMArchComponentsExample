package com.my_project.mvvmarchcomponentsexample.ui

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.my_project.mvvmarchcomponentsexample.App
import com.my_project.mvvmarchcomponentsexample.R
import com.my_project.mvvmarchcomponentsexample.ui.common.MainLifecycleObserver
import ru.electronicstores.notebookstore.ui.common.FragmentNavigator
import ru.laptopsstore.laptopsstore.ui.common.Screens
import javax.inject.Inject

class MainActivity : AppCompatActivity(), BooksFragment.BookClickCallback, HeroesFragment.HeroClickCallback {

    @Inject
    lateinit var navigator: FragmentNavigator
    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.injectMainActivity(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponents()
        if (savedInstanceState == null) {
            navigator.createFragment(Screens.BOOKS, fragmentManager)
        }
        lifecycle.addObserver(MainLifecycleObserver())
    }

    override fun onBackPressed() {
        val currentScreen = supportFragmentManager.findFragmentById(R.id.main_container)
        when (currentScreen) {
            is BooksFragment -> super.onBackPressed()
            is HeroesFragment -> navigator.createFragment(Screens.BOOKS, fragmentManager)
            is DetailHeroFragment -> navigator.createFragment(Screens.HEROES, fragmentManager)
        }
    }

    override fun bookClick() {
        navigator.createFragment(Screens.HEROES, fragmentManager)
    }

    override fun heroClick() {
        navigator.createFragment(Screens.DETAIL_HERO, fragmentManager)
    }

    private fun initComponents() {
        fragmentManager = supportFragmentManager
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
