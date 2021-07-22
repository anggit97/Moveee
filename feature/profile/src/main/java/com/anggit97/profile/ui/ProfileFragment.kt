package com.anggit97.profile.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.WindowInsetsCompat.Type.systemBars
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.anggit97.core.ext.loadAsyncCircle
import com.anggit97.core.util.autoCleared
import com.anggit97.core.util.setOnDebounceClickListener
import com.anggit97.profile.AccountViewModel
import com.anggit97.profile.R
import com.anggit97.profile.databinding.FragmentProfileBinding
import com.anggit97.profile.databinding.ProfileContentBinding
import com.anggit97.profile.databinding.ToolbarHeaderBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.Insetter


/**
 * Created by Anggit Prayogo on 22,July,2021
 * GitHub : https://github.com/anggit97
 */
@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var binding: FragmentProfileBinding by autoCleared()

    private val viewModel: AccountViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view).apply {
            header.setup()
            profileContent.setup()
            adaptSystemWindowInset()
        }
    }

    private fun ToolbarHeaderBinding.setup() {
        searchBack.setOnDebounceClickListener {
            findNavController().navigateUp()
        }
    }

    private fun ProfileContentBinding.setup(){
        viewModel.account.observe(viewLifecycleOwner){
            ivAvatar.loadAsyncCircle(it.getGravatarImageUrl())
            tvUsername.text = it.username
        }
    }

    private fun FragmentProfileBinding.adaptSystemWindowInset() {
        Insetter.builder().setOnApplyInsetsListener { view, insets, initialState ->
            view.updatePadding(top = initialState.paddings.top + insets.getInsets(systemBars()).top)
        }.applyToView(root)
    }
}