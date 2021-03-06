package com.anggit97.movieee

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.core.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.distinctUntilChanged
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.anggit97.auth.AuthActivity
import com.anggit97.core.ext.*
import com.anggit97.core.ui.base.consumeBackEventInChildFragment
import com.anggit97.core.util.viewBindings
import com.anggit97.model.model.Account
import com.anggit97.movieee.databinding.ActivityMainBinding
import com.anggit97.movieee.worker.GetLatestMovieWorker
import com.anggit97.navigation.SystemEvent
import com.anggit97.navigation.SystemViewModel
import com.anggit97.session.SessionViewModel
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.Insetter

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBindings(ActivityMainBinding::inflate)

    private val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
        binding.navigationView.setCheckedItem(destination.id)
    }

    private val systemViewModel: SystemViewModel by viewModels()
    private val sessionViewModel: SessionViewModel by viewModels()

    private val navHostFragment: Fragment
        get() = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Moop_Main)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        Insetter.builder()
            .setOnApplyInsetsListener { container, insets, initialState ->
                val systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                container.updatePadding(
                    left = initialState.paddings.left + systemInsets.left,
                    right = initialState.paddings.right + systemInsets.right
                )
            }
            .applyToView(binding.root)
        Insetter.builder()
            .setOnApplyInsetsListener { view, insets, initialState ->
                view.updatePadding(
                    top = initialState.paddings.top + insets.getInsets(WindowInsetsCompat.Type.systemBars()).top
                )
            }
            .applyToView(binding.navigationView)

        binding.navigationView.setNavigationItemSelectedListener {
            consume {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                val navController = navHostFragment.findNavController()
                when (it.itemId) {
                    R.id.homeFragment -> navController.popBackStack(R.id.homeFragment, false)
                    R.id.authActivity -> {
                        val authenticated = sessionViewModel.isAuthenticated()
                        if (authenticated) {
                            sessionViewModel.logout()
                        } else {
                            navigateToAuthActivity()
                        }
                    }
                    else -> NavigationUI.onNavDestinationSelected(it, navController)
                }
            }
        }

        systemViewModel.systemEvent.observeEvent(this) {
            handleEvent(it)
        }

        sessionViewModel.authenticated.distinctUntilChanged().observe(this) {
            handleSession(it)
        }

        sessionViewModel.logout.observe(this) {
            showLongToast(getString(R.string.success_logout))
        }

        sessionViewModel.account.observe(this) {
            binding.navigationView.getHeaderView(0).setView(it)
        }

        scheduleWorker()
    }

    private fun View.setView(account: Account?) {
        val logo = findViewById<ImageView>(R.id.ivLogo)
        val group = findViewById<Group>(R.id.groupUser)
        val avatar = findViewById<ImageView>(R.id.ivAvatar)
        val username = findViewById<TextView>(R.id.tvUsername)

        val accountNull = account == null
        group.isVisible = !accountNull
        logo.isVisible = accountNull

        account?.let {
            avatar.loadAsyncCircle(account.getGravatarImageUrl())
            username.text = account.username
        }
    }

    private fun handleSession(authenticated: Boolean) {
        val menuAuth = binding.navigationView.menu.findItem(R.id.authActivity)
        val menuProfile = binding.navigationView.menu.findItem(R.id.profileFragment)
        menuProfile.isVisible = authenticated

        if (authenticated) {
            menuAuth.title = getString(R.string.menu_logout)
        } else {
            binding.navigationView.getHeaderView(0).setView(null)
            menuAuth.title = getString(R.string.menu_login)
        }
    }

    private fun navigateToAuthActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
    }

    private fun scheduleWorker() {
        GetLatestMovieWorker.enqueuePeriodicWork(this)
    }

    private fun handleEvent(event: SystemEvent) {
        when (event) {
            is SystemEvent.OpenDrawerMenuUiEvent -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerVisible(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            return
        }
        if (navHostFragment.consumeBackEventInChildFragment()) return
        super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        navHostFragment.findNavController().addOnDestinationChangedListener(listener)
    }

    override fun onPause() {
        super.onPause()
        navHostFragment.findNavController().removeOnDestinationChangedListener(listener)
    }
}