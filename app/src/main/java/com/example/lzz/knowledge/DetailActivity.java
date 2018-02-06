package com.example.lzz.knowledge;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.lzz.knowledge.bean.ZhihuDailyStory;
import com.example.lzz.knowledge.util.API;
import com.example.lzz.knowledge.util.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private WebView webView;

    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        toolbar = (Toolbar)findViewById(R.id.detail_tool_bar);
        setSupportActionBar(toolbar);
        webView = (WebView)findViewById(R.id.web_view);

        requestData(getIntent().getIntExtra("id", 0));
    }

    private void requestData(int id){
        HttpUtil.sendOKHttpRequest(API.ZHIHU_NEWS_DETAIL + id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseStr = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ZhihuDailyStory story = gson.fromJson(responseStr ,ZhihuDailyStory.class);
                        webView.getSettings().setJavaScriptEnabled(true);
                        webView.setWebViewClient(new WebViewClient());
                        webView.loadUrl(story.getShare_url());
                    }
                });

            }
        });

    }
}
