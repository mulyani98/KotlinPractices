package com.mououoo.kotlinpractices.activity

import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.mououoo.kotlinpractices.R

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        enableEdgeToEdge()

        val url = intent.getStringExtra("url")
        val webView: WebView = findViewById(R.id.webViewId)

        // Set the WebViewClient to prevent opening an external browser
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val uri = request?.url
                return if (uri != null && isTrustedDomain(uri.toString())) {
                    false // allow loading
                } else {
                    true // block loading for untrusted domains
                }
            }
        }

        // Enable JavaScript only if you are sure the content is safe
//        webView.settings.javaScriptEnabled = true

        // Load only trusted URLs (use fallback if null)
        val safeUrl = url?.takeIf { isTrustedDomain(it) }
            ?: "https://mulyani98.github.io/Home-page-Kotlin-Practices/"
        webView.loadUrl(safeUrl)
    }

    // Function to limit access to approved domains only.
    private fun isTrustedDomain(url: String): Boolean {
        return url.startsWith("https://mulyani98.github.io/")
    }

}

