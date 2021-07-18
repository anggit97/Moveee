package com.anggit97.auth

import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.anggit97.auth.databinding.ActivityAuthBinding
import com.anggit97.core.util.viewBindings
import com.anggit97.navigation.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private val binding: ActivityAuthBinding by viewBindings(ActivityAuthBinding::inflate)

    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var webViewClient: WebViewClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        authViewModel.urlAuth.observe(this@AuthActivity) {
            binding.initWebView(it)
        }
    }

    private fun ActivityAuthBinding.initWebView(url: String) {
        webViewClient = webViewClientCallback
        wvAuth.loadUrl(url)
    }

    private val webViewClientCallback = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            val url = request?.url.toString()
            Timber.d(url)
            return false
        }
    }
}