package com.anggit97.home.plan

import androidx.fragment.app.viewModels
import com.anggit97.home.tab.HomeContentsFragment
import dagger.hilt.android.AndroidEntryPoint


/**
 * Created by Anggit Prayogo on 04,July,2021
 * GitHub : https://github.com/anggit97
 */
@AndroidEntryPoint
class PlanFragment : HomeContentsFragment() {

    override val viewModel: HomePlanViewModel by viewModels()
}