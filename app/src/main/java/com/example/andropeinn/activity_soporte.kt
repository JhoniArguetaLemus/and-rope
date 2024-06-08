package com.example.andropeinn

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_soporte : AppCompatActivity() {
    private lateinit var webView: WebView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_soporte)

        webView=findViewById<WebView>(R.id.webView_soporte)

        val url=intent.getStringExtra("url")

        // Enable JavaScript (if needed)
        webView.settings.javaScriptEnabled = true

        // Set WebViewClient to open links within the app
        webView.webViewClient = WebViewClient()

        // Load a URL
        webView.loadUrl("$url")

    }
}