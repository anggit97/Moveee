package com.anggit97.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.anggit97.core.util.autoCleared
import com.anggit97.home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * Created by Anggit Prayogo on 04,July,2021
 * GitHub : https://github.com/anggit97
 */
@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var binding: FragmentHomeBinding by autoCleared {
        header.tabs.clearOnTabSelectedListeners()
        viewPager.unregisterOnPageChangeCallback(pageChangeCallback)
    }

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            when (position) {
                0 -> {

                }
                1 -> {

                }
                2 -> {

                }
            }
        }
    }
}