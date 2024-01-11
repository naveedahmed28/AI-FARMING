package com.example.fertilizer;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class AgricultureInfo extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_government_schemes);
        webView = findViewById(R.id.web_view_zee);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://agricoop.nic.in/en/whos-who");

    }

    private class Callback extends WebViewClient {
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event){
            return false;
        }
    }
}