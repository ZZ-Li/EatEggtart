package com.example.lzz.knowledge.home;

import android.content.Context;
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

/**
 * Created by ASUS on 2018/2/28.
 */

public class DetailPresenter implements DetailContract.Presenter {

    private Context context;
    private DetailContract.View view;

    private Gson gson;

    private int id;
    private String title;

    public DetailPresenter(Context context, DetailContract.View view){
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);

        gson = new Gson();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void start() {

    }

    @Override
    public void openInBrowser() {

    }

    @Override
    public void shareAsText() {

    }

    @Override
    public void openUrl(WebView webView, String url) {

    }

    @Override
    public void copyText() {

    }

    @Override
    public void copyLike() {

    }

    @Override
    public void addToOrDeleteFromBookmarks() {

    }

    @Override
    public boolean queryIfIsBookmarks() {
        return false;
    }

    @Override
    public void requestData() {
        HttpUtil.sendOKHttpRequest(API.ZHIHU_NEWS_DETAIL + id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseStr = response.body().string();

            }
        });
    }
}
