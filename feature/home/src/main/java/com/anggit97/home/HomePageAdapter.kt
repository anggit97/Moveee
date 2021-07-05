package com.anggit97.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.anggit97.home.favourite.FavoriteFragment
import com.anggit97.home.now.NowFragment
import com.anggit97.home.plan.PlanFragment
import com.anggit97.home.tab.HomeTabFragment


/**
 * Created by Anggit Prayogo on 04,July,2021
 * GitHub : https://github.com/anggit97
 */
class HomePageAdapter(fragment: HomeFragment) : FragmentStateAdapter(fragment) {

    private val items = arrayListOf(
        NowFragment(),
        PlanFragment(),
        FavoriteFragment()
    )

    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        return items[position]
    }

    fun getFragment(position: Int): HomeTabFragment? {
        return items.getOrNull(position) as? HomeTabFragment
    }

    fun scrollToTop(position: Int) {
        getFragment(position)?.scrollToTop()
    }
}