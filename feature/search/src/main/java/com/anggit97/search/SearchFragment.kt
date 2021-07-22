package com.anggit97.search

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.WindowInsetsCompat.Type.systemBars
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ActivityNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.anggit97.core.util.AlwaysDiffCallback
import com.anggit97.core.util.ImeUtil
import com.anggit97.core.util.autoCleared
import com.anggit97.core.util.setOnDebounceClickListener
import com.anggit97.home.tab.FooterLoaderMovieListAdapter
import com.anggit97.home.tab.HomeContentsListAdapter
import com.anggit97.search.databinding.FragmentSearchBinding
import com.anggit97.search.databinding.SearchContentsBinding
import com.anggit97.search.databinding.SearchHeaderBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.Insetter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by Anggit Prayogo on 04,July,2021
 * GitHub : https://github.com/anggit97
 */
@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private var binding: FragmentSearchBinding by autoCleared()

    private val viewModel: SearchViewModel by viewModels()

    private lateinit var listAdapter: HomeContentsListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view).apply {
            header.setup()
            contents.setup()
            adaptSystemWindowInset()
        }
    }

    private fun FragmentSearchBinding.adaptSystemWindowInset() {
        Insetter.builder().setOnApplyInsetsListener { view, insets, initialState ->
            view.updatePadding(
                top = initialState.paddings.top + insets.getInsets(systemBars()).top
            )
        }
            .applyToView(root)
        Insetter.builder()
            .setOnApplyInsetsListener { view, insets, initialState ->
                view.updatePadding(
                    bottom = initialState.paddings.bottom + insets.getInsets(systemBars()).bottom
                )
            }
            .applyToView(contents.listView)
    }

    private fun SearchHeaderBinding.setup() {
        searchView.queryHint = getString(R.string.search_hint)
        searchView.inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
        searchView.imeOptions = searchView.imeOptions or
                EditorInfo.IME_ACTION_SEARCH or
                EditorInfo.IME_FLAG_NO_EXTRACT_UI or
                EditorInfo.IME_FLAG_NO_FULLSCREEN
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            private var lastQuery = ""

            override fun onQueryTextSubmit(query: String): Boolean {
                ImeUtil.hideIme(searchView)
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                val searchText = query.trim()
                if (searchText == lastQuery) {
                    return false
                }

                lastQuery = searchText

                lifecycleScope.launch {
                    delay(300)
                    if (searchText == lastQuery) {
                        viewModel.searchFor(query).collectLatest {
                            listAdapter.submitData(it)
                        }
                    }
                }
                return true
            }
        })
        searchBack.setOnDebounceClickListener {
            findNavController().navigateUp()
        }
    }

    private fun SearchContentsBinding.setup() {
        listAdapter =
            HomeContentsListAdapter(root.context, AlwaysDiffCallback()) { movie, sharedElements ->
                findNavController().navigate(
                    SearchFragmentDirections.actionToDetail(movie),
                    ActivityNavigatorExtras(
                        activityOptions = ActivityOptionsCompat
                            .makeSceneTransitionAnimation(requireActivity(), *sharedElements)
                    )
                )
            }
        listView.apply {
            val footerLoaderStateAdapter = FooterLoaderMovieListAdapter { listAdapter.retry() }
            adapter = listAdapter.withLoadStateFooter(footerLoaderStateAdapter)
            itemAnimator?.apply {
                addDuration = 300
                changeDuration = 0
                moveDuration = 0
                removeDuration = 300
            }
        }
    }
}