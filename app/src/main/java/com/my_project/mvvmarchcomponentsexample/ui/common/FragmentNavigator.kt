package ru.electronicstores.notebookstore.ui.common

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.my_project.mvvmarchcomponentsexample.R
import com.my_project.mvvmarchcomponentsexample.ui.BooksFragment
import com.my_project.mvvmarchcomponentsexample.ui.DetailHeroFragment
import com.my_project.mvvmarchcomponentsexample.ui.HeroesFragment
import ru.laptopsstore.laptopsstore.ui.common.Screens

class FragmentNavigator {

     lateinit var screen: String

    fun createFragment(screenKey: String, fragmentManager: FragmentManager) = when (screenKey) {

        Screens.BOOKS -> {
            screen = Screens.BOOKS
            showScreen(fragmentManager, BooksFragment.newInstance())
        }

        Screens.HEROES -> {
            screen = Screens.HEROES
            showScreen(fragmentManager, HeroesFragment.newInstance())
        }

        Screens.DETAIL_HERO -> {
            screen = Screens.DETAIL_HERO
            showScreen(fragmentManager, DetailHeroFragment.newInstance())
        }

        else -> {
            screen = Screens.BOOKS
            showScreen(fragmentManager, BooksFragment.newInstance())
        }
    }

    private fun showScreen(fragmentManager: FragmentManager, fragment: Fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.main_container, fragment)
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right)
                .commitAllowingStateLoss()
    }
}