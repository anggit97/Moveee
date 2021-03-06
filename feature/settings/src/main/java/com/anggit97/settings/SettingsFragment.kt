package com.anggit97.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.core.app.SharedElementCallback
import androidx.core.view.WindowInsetsCompat.Type.systemBars
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.anggit97.core.ext.startActivitySafely
import com.anggit97.core.util.LangUtil
import com.anggit97.core.util.autoCleared
import com.anggit97.core.util.setOnDebounceClickListener
import com.anggit97.navigation.SystemViewModel
import com.anggit97.settings.databinding.FragmentSettingsBinding
import com.anggit97.theme.setThemeOptionLabel
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.Insetter


/**
 * Created by Anggit Prayogo on 04,July,2021
 * GitHub : https://github.com/anggit97
 */
@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var binding: FragmentSettingsBinding by autoCleared()

    private val systemViewModel: SystemViewModel by activityViewModels()
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setExitSharedElementCallback(object : SharedElementCallback() {
            override fun onMapSharedElements(
                names: List<String>,
                sharedElements: MutableMap<String, View>
            ) {
                sharedElements.clear()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view).apply {
            initViewState(viewModel)
            adaptSystemWindowInset()
        }
    }

    private fun FragmentSettingsBinding.adaptSystemWindowInset() {
        Insetter.builder()
            .setOnApplyInsetsListener { settingsScene, insets, initialState ->
                settingsScene.updatePadding(
                    top = initialState.paddings.top + insets.getInsets(systemBars()).top
                )
            }
            .applyToView(settingsScene)
        Insetter.builder()
            .setOnApplyInsetsListener { listView, insets, initialState ->
                listView.updatePadding(
                    bottom = initialState.paddings.bottom + insets.getInsets(systemBars()).bottom
                )
            }
            .applyToView(listView)
    }

    private fun FragmentSettingsBinding.initViewState(viewModel: SettingsViewModel) {
        toolbar.setNavigationOnClickListener {
            systemViewModel.openNavigationMenu()
        }
        viewModel.themeUiModel.observe(viewLifecycleOwner) {
            themeItem.themeName.setThemeOptionLabel(it.themeOption)
        }
        themeItem.editThemeButton.setOnDebounceClickListener {
            onThemeEditClicked()
        }
        themeItem.themeName.setOnDebounceClickListener {
            onThemeEditClicked()
        }

        // TODO: Apply theater mode
        // tmPrepare.setOnClickListener {
        //    startActivity(Intent(requireContext(), TheaterModeTileActivity::class.java))
        // }
        feedbackItem.bugReportButton.setOnDebounceClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:")
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(HELP_EMAIL))
            intent.putExtra(Intent.EXTRA_SUBJECT, "App v${BuildConfig.VERSION_NAME} bug report")
            it.context.startActivitySafely(intent)
        }

        langItem.editThemeButton.setOnDebounceClickListener {
            onLangEditClicked()
        }

        langItem.themeName.setOnDebounceClickListener {
            onLangEditClicked()
        }

        langItem.themeName.text = LangUtil.getLanguageName().plus(" (${LangUtil.getLanguageCountryCode()})")
    }

    private fun onLangEditClicked() {
        val i = Intent(Settings.ACTION_LOCALE_SETTINGS)
        startActivity(i)
    }

    private fun onThemeEditClicked() {
        findNavController().navigate(
            SettingsFragmentDirections.actionToThemeOption()
        )
    }

    companion object {

        private const val HELP_EMAIL = "anggitprayogo@gmail.com"
    }
}