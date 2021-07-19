package com.anggit97.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
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
    private lateinit var webViewClientCb: WebViewClient
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        url = intent.getStringExtra(URL_PAYLOAD)

        authViewModel.getRequestToken().observe(this) {
            val url = BuildConfig.ASK_PERMISSION_MOVIE_URL.plus(it.requestToken)
            binding.initWebView(url)
        }

        authViewModel.sessionId.observe(this) {
            Log.d("SESSION", "sessionId: " + it.sessionId)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun ActivityAuthBinding.initWebView(url: String?) {
        webViewClientCb = webViewClientCallback
        wvAuth.apply {
            webViewClient = webViewClientCb
            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = true
        }
        url?.let { wvAuth.loadUrl(it) }
    }

    private val webViewClientCallback = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            val url = request?.url.toString()
            when (url.split("/").last()) {
                "allow" -> {
                    authViewModel.createSessionId()
                }
                "deny" -> {
                    Toast.makeText(this@AuthActivity, "Yah ditolak", Toast.LENGTH_SHORT).show()
                }
            }
            Timber.d(url)
            return false
        }
    }

    companion object {
        const val URL_PAYLOAD = "url_payload"
    }
}