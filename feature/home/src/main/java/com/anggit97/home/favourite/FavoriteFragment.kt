package com.anggit97.home.favourite

import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ActivityNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.anggit97.core.util.autoCleared
import com.anggit97.home.HomeFragmentDirections
import com.anggit97.home.R
import com.anggit97.home.databinding.HomeTabFavouriteBinding
import com.anggit97.home.databinding.HomeTabFragmentBinding
import com.anggit97.home.tab.HomeTabFragment
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.FadeInAnimator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch


/**
 * Created by Anggit Prayogo on 04,July,2021
 * GitHub : https://github.com/anggit97
 */
@AndroidEntryPoint
class FavoriteFragment : HomeTabFragment(R.layout.home_tab_favourite){

    private var binding: HomeTabFavouriteBinding by autoCleared()

    private val viewModel: HomeFavouriteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = HomeTabFavouriteBinding.bind(view).apply {
            initViewState(viewModel)
        }
    }

    private fun HomeTabFavouriteBinding.initViewState(viewModel: HomeFavouriteViewModel) {
        val listAdapter = HomeFavoriteListAdapter(root.context) { movie, sharedElements ->
            findNavController().navigate(
                HomeFragmentDirections.actionToDetail(movie),
                ActivityNavigatorExtras(
                    activityOptions = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(requireActivity(), *sharedElements)
                )
            )
        }
        listView.apply {
            adapter = listAdapter
            itemAnimator = FadeInAnimator()
        }

        lifecycleScope.launch {
            viewModel.fetchMovieFavourite().distinctUntilChanged().collectLatest {
                listAdapter.submitData(it)
            }
        }
    }

    override fun scrollToTop() {
        getListView()?.scrollToTopInternal()
    }

    override fun onBackPressed(): Boolean {
        return getListView()?.scrollToTopInternal() ?: false
    }

    private fun getListView(): RecyclerView? {
        return binding.listView
    }
}