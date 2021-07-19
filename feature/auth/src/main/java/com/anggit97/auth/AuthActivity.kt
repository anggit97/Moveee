package com.anggit97.auth

import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.anggit97.auth.databinding.ActivityAuthBinding
import com.anggit97.core.util.viewBindings
import com.anggit97.data.BuildConfig
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private val binding: ActivityAuthBinding by viewBindings(ActivityAuthBinding::inflate)

    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var webViewClient: WebViewClient
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        url = intent.getStringExtra(URL_PAYLOAD)

        authViewModel.getRequestToken().observe(this) {
            val url = BuildConfig.ASK_PERMISSION_MOVIE_URL.plus(it.requestToken)
            binding.initWebView(url)
        }
    }

    private fun ActivityAuthBinding.initWebView(url: String?) {
        webViewClient = webViewClientCallback
        wvAuth.webViewClient = webViewClient
        url?.let { wvAuth.loadUrl(it) }
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

    companion object {
        const val URL_PAYLOAD = "url_payload"
    }
}