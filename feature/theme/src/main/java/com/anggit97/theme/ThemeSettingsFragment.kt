package com.anggit97.theme

import android.os.Bundle
import android.view.View
import androidx.core.view.WindowInsetsCompat.Type.systemBars
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.anggit97.theme.databinding.ThemeOptionFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.Insetter


/**
 * Created by Anggit Prayogo on 07,July,2021
 * GitHub : https://github.com/anggit97
 */
@AndroidEntryPoint
class ThemeSettingsFragment : Fragment(R.layout.theme_option_fragment) {

    private val viewModel: ThemeSettingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(ThemeOptionFragmentBinding.bind(view)) {
            initViewState(viewModel)
            adaptSystemWindowInset()
        }
    }

    private fun ThemeOptionFragmentBinding.initViewState(viewModel: ThemeSettingViewModel) {
        val listAdapter = ThemeSettingListAdapter {
            viewModel.onItemClick(it)
        }
        listView.adapter = listAdapter
        viewModel.uiModel.observe(viewLifecycleOwner) {
            listAdapter.submitList(it.items)
        }
    }

    private fun ThemeOptionFragmentBinding.adaptSystemWindowInset() {
        Insetter.builder()
            .setOnApplyInsetsListener { view, insets, initialState ->
                view.updatePadding(top = initialState.paddings.top + insets.getInsets(systemBars()).top)
            }
            .applyToView(themeOptionScene)
        Insetter.builder()
            .setOnApplyInsetsListener { view, insets, initialState ->
                view.updatePadding(
                    bottom = initialState.paddings.bottom + insets.getInsets(
                        systemBars()
                    ).bottom
                )
            }
            .applyToView(listView)
    }
}