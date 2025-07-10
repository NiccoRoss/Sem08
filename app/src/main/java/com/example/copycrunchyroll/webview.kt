package com.example.copycrunchyroll

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class webview : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        // Toolbar
        val toolbar: Toolbar = findViewById(R.id.web_toolbar)
        setSupportActionBar(toolbar)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Título dinámico
        val pageTitle = intent.getStringExtra("title") ?: "Términos"
        supportActionBar?.title = pageTitle

        // WebView
        webView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progressBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.visibility = View.GONE
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url ?: "")
                return true
            }
        }


        val url = intent.getStringExtra("url")
            ?: "https://www.crunchyroll.com/es-es/tos/"
        webView.loadUrl(url)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
