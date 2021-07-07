package com.anggit97.settings

import androidx.fragment.app.Fragment
import com.anggit97.core.util.autoCleared
import com.anggit97.settings.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * Created by Anggit Prayogo on 04,July,2021
 * GitHub : https://github.com/anggit97
 */
@AndroidEntryPoint
class SettingsFragment: Fragment(R.layout.fragment_settings){

    private val binding: FragmentSettingsBinding by autoCleared()

}