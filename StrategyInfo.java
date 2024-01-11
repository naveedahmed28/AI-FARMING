package com.example.fertilizer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class StrategyInfo extends AppCompatActivity {

    WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_government_schemes);
        webView = findViewById(R.id.web_view_zee);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://agriculturepost.com/opinion/top-6-strategies-to-fortify-large-farm-management-and-magnify-productivity/");

    }

    private class Callback extends WebViewClient {
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event){
            return false;
        }
    }
}