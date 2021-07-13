package com.anggit97.home.test

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.flatMap
import androidx.paging.map
import com.anggit97.core.util.viewBindings
import com.anggit97.home.databinding.ActivityTestPagingBinding
import com.anggit97.home.now.HomeNowViewModel
import com.anggit97.home.tab.HomeContentsListAdapter
import com.anggit97.home.tab.LoaderMovieListAdapter
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.FadeInAnimator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TestPaging : AppCompatActivity() {

    private val binding: ActivityTestPagingBinding by viewBindings(ActivityTestPagingBinding::inflate)

    private val viewModel: HomeNowViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show()

        binding.initViewState(viewModel)
    }

    private fun ActivityTestPagingBinding.initViewState(viewModel: HomeNowViewModel){

        Toast.makeText(this@TestPaging, "Hello Gaes", Toast.LENGTH_SHORT).show()
        val listAdapter = HomeContentsListAdapter(root.context) { movie, sharedElements ->

        }

        listView.apply {
            val loaderStateAdapter = LoaderMovieListAdapter { listAdapter.retry() }
            adapter = listAdapter.withLoadStateFooter(loaderStateAdapter)
            itemAnimator = FadeInAnimator()
        }

        lifecycleScope.launch {
            viewModel.fetchNowMovieList().distinctUntilChanged().collectLatest {
                Log.d("TAG", "initViewState: ${it?.map { it2 -> it2.title }}")
                listAdapter.submitData(it)
            }
        }
    }
}