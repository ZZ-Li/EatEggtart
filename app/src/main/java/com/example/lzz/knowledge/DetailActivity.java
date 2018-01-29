package com.example.lzz.knowledge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.lzz.knowledge.util.API;

public class DetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        toolbar = (Toolbar)findViewById(R.id.detail_tool_bar);
        setSupportActionBar(toolbar);
        webView = (WebView)findViewById(R.id.web_view);
        Intent intent = getIntent();
        //String id = intent.getStringExtra("id");
        int id = intent.getIntExtra("id", 0);
        Log.d("Knowlegdge", "" + id);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(API.ZHIHU_NEWS_DETAIL + id);
    }
}
