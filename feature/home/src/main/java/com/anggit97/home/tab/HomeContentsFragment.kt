package com.anggit97.home.tab

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.WindowInsetsCompat.Type.systemBars
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ActivityNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.map
import androidx.recyclerview.widget.RecyclerView
import com.anggit97.core.ui.base.OnBackPressedListener
import com.anggit97.core.util.RoughAdapterDataObserver
import com.anggit97.core.util.autoCleared
import com.anggit97.core.util.setOnDebounceClickListener
import com.anggit97.home.HomeFragmentDirections
import com.anggit97.home.R
import com.anggit97.home.databinding.HomeTabFragmentBinding
import dev.chrisbanes.insetter.Insetter
import jp.wasabeef.recyclerview.animators.FadeInAnimator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

/**
 * Created by Anggit Prayogo on 04,July,2021
 * GitHub : https://github.com/anggit97
 */
abstract class HomeContentsFragment : HomeTabFragment(R.layout.home_tab_fragment),
    OnBackPressedListener {

    private var binding: HomeTabFragmentBinding by autoCleared {
        listView.adapter?.unregisterAdapterDataObserver(adapterDataObserver)
    }

    protected abstract val viewModel: HomeContentsViewModel

    private val adapterDataObserver = object : RoughAdapterDataObserver() {
        override fun onItemRangeUpdatedRoughly() {
//            getListView()?.scrollToTopInternal(force = true)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = HomeTabFragmentBinding.bind(view).apply {
            adaptSystemWindowInset()
            initViewState(viewModel)
            listView.adapter?.registerAdapterDataObserver(adapterDataObserver)
        }
    }

    private fun HomeTabFragmentBinding.adaptSystemWindowInset() {
        Insetter.builder()
            .setOnApplyInsetsListener { view, insets, initialState ->
                view.updatePadding(
                    bottom = initialState.paddings.bottom + insets.getInsets(systemBars()).bottom
                )
            }
            .applyToView(listView)
    }

    //NOTE: https://stackoverflow.com/questions/66471381/how-to-use-android-androidx-paging-pagingdataadapter-loadstateflow-to-show-empty
    private fun HomeTabFragmentBinding.initViewState(viewModel: HomeContentsViewModel) {
        val listAdapter = HomeContentsListAdapter(root.context) { movie, sharedElements ->
            findNavController().navigate(
                HomeFragmentDirections.actionToDetail(movie),
                ActivityNavigatorExtras(
                    activityOptions = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(requireActivity(), *sharedElements)
                )
            )
        }.also {
            lifecycleScope.launch {
                it.loadStateFlow.collectLatest { loadState ->
                    loadingView.isInProgress =
                        loadState.source.refresh is LoadState.Loading && it.itemCount == 0
                }
            }
        }

        listView.apply {
            val footerLoaderStateAdapter = FooterLoaderMovieListAdapter { listAdapter.retry() }
            adapter = listAdapter.withLoadStateFooter(footerLoaderStateAdapter)
            itemAnimator = FadeInAnimator()
        }
        errorView.root.setOnDebounceClickListener {
            viewModel.refresh()
        }

        lifecycleScope.launch {
            viewModel.fetchNowMovieList().distinctUntilChanged().collectLatest {
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
        return runCatching { binding.listView }.getOrNull()
    }
}