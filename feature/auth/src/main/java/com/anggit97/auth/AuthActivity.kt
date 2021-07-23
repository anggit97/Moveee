package com.anggit97.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.anggit97.auth.databinding.ActivityAuthBinding
import com.anggit97.core.ext.showLongToast
import com.anggit97.core.ext.showToast
import com.anggit97.core.util.viewBindings
import com.anggit97.data.BuildConfig
import com.anggit97.session.SessionManagerStore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private val binding: ActivityAuthBinding by viewBindings(ActivityAuthBinding::inflate)

    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var webViewClientCb: WebViewClient
    private var url: String? = null

    @Inject
    lateinit var sessionManagerStore: SessionManagerStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        url = intent.getStringExtra(URL_PAYLOAD)


        authViewModel.requestToken.observe(this@AuthActivity) {
            binding.handleStateRequestToken(it)
        }

        authViewModel.sessionId.observe(this) {
            handleStateSessionId(it)
        }
    }

    private fun ActivityAuthBinding.handleStateRequestToken(state: RequestTokenState) {
        when (state) {
            is RequestTokenState.Error -> showToast(state.error.message ?: "error")
            is RequestTokenState.HideLoading -> showToast("Selesai sinkronisasi..")
            is RequestTokenState.ShowLoading -> showToast("Request to Movie DB api")
            is RequestTokenState.Success -> {
                val url = BuildConfig.ASK_PERMISSION_MOVIE_URL.plus(state.data.requestToken)
                initWebView(url)
            }
        }
    }

    private fun handleStateSessionId(state: SessionIdState) {
        when (state) {
            is SessionIdState.Error -> showToast("Gagal menyimpan session id")
            is SessionIdState.HideLoading -> showToast("Selesai sinkronisasi..")
            is SessionIdState.ShowLoading -> showToast("Request to session id")
            is SessionIdState.Success -> {
                showLongToast(R.string.success_login)
                finish()
            }
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
                    lifecycleScope.launch {
                        authViewModel.createSessionId()
                    }
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