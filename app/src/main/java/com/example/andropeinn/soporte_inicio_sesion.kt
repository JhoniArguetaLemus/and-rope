package com.example.andropeinn

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class soporte_inicio_sesion : AppCompatActivity() {
    private lateinit var webView:WebView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_soporte_inicio_sesion)
        webView=findViewById<WebView>(R.id.webView)


        // Enable JavaScript (if needed)
        webView.settings.javaScriptEnabled = true

        // Set WebViewClient to open links within the app
        webView.webViewClient = WebViewClient()

        // Load a URL
        webView.loadUrl("https://forms.gle/teapD1qTXza4v8m27")
    }
}