package com.anggit97.home.tab

import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.WindowInsetsCompat.Type.systemBars
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.navigation.ActivityNavigatorExtras
import androidx.navigation.fragment.findNavController
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
            getListView()?.scrollToTopInternal(force = true)
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

    private fun HomeTabFragmentBinding.initViewState(viewModel: HomeContentsViewModel) {
        val listAdapter = HomeContentsListAdapter(root.context) { movie, sharedElements ->
            findNavController().navigate(
                HomeFragmentDirections.actionToDetail(movie),
                ActivityNavigatorExtras(
                    activityOptions = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(requireActivity(), *sharedElements)
                )
            )
        }
        listView.apply {
            setItemViewCacheSize(20)
            adapter = listAdapter
            itemAnimator = FadeInAnimator()
        }
        errorView.root.setOnDebounceClickListener {
            viewModel.refresh()
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            loadingView.isInProgress = it
        }
        viewModel.isError.observe(viewLifecycleOwner) {
            errorView.root.isVisible = it
        }
        viewModel.contentsUiModel.observe(viewLifecycleOwner) {
            noItemsView.isVisible = it.movies.isEmpty()
            listAdapter.submitList(it.movies)
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