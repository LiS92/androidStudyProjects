package com.example.p0321_simplebrowser;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class BrowserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        WebView webView = (WebView) findViewById(R.id.webView);
        Uri data = getIntent().getData();
        webView.loadUrl(data.toString());
//      Определяем WebView, читаем data из Intent и передаем строку в WebView
    }
}
