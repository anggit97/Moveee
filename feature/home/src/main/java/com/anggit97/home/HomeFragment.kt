package com.anggit97.home

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.WindowInsetsCompat.Type.systemBars
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.anggit97.core.util.autoCleared
import com.anggit97.home.databinding.FragmentHomeBinding
import com.anggit97.navigation.SystemViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.Insetter

/**
 * Created by Anggit Prayogo on 04,July,2021
 * GitHub : https://github.com/anggit97
 */
@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var binding: FragmentHomeBinding by autoCleared {
//        header.tabs.clearOnTabSelectedListeners()
        viewPager.unregisterOnPageChangeCallback(pageChangeCallback)
    }

    private val systemViewModel: SystemViewModel by activityViewModels()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view).apply {
//            initViewState(viewModel)
            adaptSystemWindowInset()
        }
    }

    private fun FragmentHomeBinding.adaptSystemWindowInset() {
//        Insetter.builder()
//            .setOnApplyInsetsListener { view, insets, initialState ->
//                view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
//                    topMargin = initialState.margins.top + insets.getInsets(systemBars()).top
//                }
//            }
//            .applyToView(headerHint.root)
        Insetter.builder()
            .setOnApplyInsetsListener { view, insets, initialState ->
                view.updatePadding(
                    top = initialState.paddings.top + insets.getInsets(systemBars()).top
                )
            }
            .applyToView(header.collapsingToolbar)
//        Insetter.builder()
//            .setOnApplyInsetsListener { view, insets, initialState ->
//                view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
//                    topMargin = initialState.margins.top + insets.getInsets(systemBars()).top
//                }
//            }
//            .applyToView(filterContainerView)
//        Insetter.builder()
//            .setOnApplyInsetsListener { view, insets, initialState ->
//                view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
//                    bottomMargin =
//                        initialState.margins.bottom + insets.getInsets(systemBars()).bottom
//                }
//            }
//            .applyToView(filterButton)
    }

    private fun FragmentHomeBinding.initViewState(viewModel: HomeViewModel) {
//        prepareSharedElements()
//        pageAdapter = HomePageAdapter(this@HomeFragment)
//        viewPager.adapter = pageAdapter
        header.apply {
            toolbar.setOnClickListener {
                Toast.makeText(requireActivity(), "Hallo", Toast.LENGTH_SHORT).show()
            }
            toolbar.setNavigationOnClickListener {
                systemViewModel.openNavigationMenu()
            }
//            tabs.setupWithViewPager2(viewPager, autoRefresh = true) { tab, position ->
//                when (position) {
//                    0 -> {
//                        tab.setIcon(R.drawable.asld_home_now)
//                        tab.setText(R.string.menu_now)
//                    }
//                    1 -> {
//                        tab.setIcon(R.drawable.asld_home_plan)
//                        tab.setText(R.string.menu_plan)
//                    }
//                    2 -> {
//                        tab.setIcon(R.drawable.asld_home_favorite)
//                        tab.setText(R.string.menu_favorite)
//                    }
//                }
//            }
        }
//        headerHint.hintButton.setOnDebounceClickListener {
//            header.appBar.setExpanded(true)
//            pageAdapter.scrollToTop(viewPager.currentItem)
//        }
//        viewModel.headerUiModel.observe(viewLifecycleOwner) {
//            headerHint.render(it)
//        }
//        header.tabs.setOnScrollChangeListener { _, scrollX, _, _, _ ->
//            header.tabDivider.isInvisible = scrollX == 0
//        }
//        header.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//
//            override fun onTabReselected(tab: TabLayout.Tab) {
//                pageAdapter.scrollToTop(tab.position)
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab) {
//            }
//
//            override fun onTabSelected(tab: TabLayout.Tab) {
//            }
//        })
//        viewPager.registerOnPageChangeCallback(pageChangeCallback)

//        filterBehavior = BottomSheetBehavior.from(filter).apply {
//            state = BottomSheetBehavior.STATE_HIDDEN
//        }
//        filterButton.setOnDebounceClickListener {
//            analytics.clickMenuFilter()
//
//            val tag = "filter"
//            if (childFragmentManager.findFragmentByTag(tag) == null) {
//                childFragmentManager.commit {
//                    replace<HomeFilterFragment>(R.id.filter_container_view, tag = tag)
//                }
//                it.postDelayed(200) {
//                    filterBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//                }
//            } else {
//                filterBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//            }
//        }
    }

    /** UI Renderer */

//    private fun HomeHeaderHintBinding.render(uiModel: HomeHeaderUiModel) {
//        hintLabel.setText(
//            when (uiModel) {
//                HomeHeaderUiModel.Now -> R.string.menu_now
//                HomeHeaderUiModel.Plan -> R.string.menu_plan
//                HomeHeaderUiModel.Favorite -> R.string.menu_favorite
//            }
//        )
//        hintLabel.apply {
//            scaleX = 1.2f
//            scaleY = 1.2f
//            animate()
//                .setDuration(100)
//                .setInterpolator(Interpolators.ACCELERATE_DECELERATE)
//                .scaleX(1f)
//                .scaleY(1f)
//        }
//    }

    /** Custom Back */

//    override fun onBackPressed(): Boolean {
//        if (filterBehavior.state != BottomSheetBehavior.STATE_HIDDEN) {
//            filterBehavior.state = BottomSheetBehavior.STATE_HIDDEN
//            return true
//        }
//        val currentPosition = binding.viewPager.currentItem
//        val current = pageAdapter.getFragment(currentPosition)
//        if (current.consumeBackEvent()) {
//            binding.header.appBar.setExpanded(true, true)
//            return true
//        }
//        if (currentPosition > 0) {
//            binding.viewPager.currentItem = 0
//            return true
//        }
//        return false
//    }
}