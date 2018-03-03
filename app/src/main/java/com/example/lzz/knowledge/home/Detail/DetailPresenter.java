package com.example.lzz.knowledge.home.Detail;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.webkit.WebView;

import com.example.lzz.knowledge.R;
import com.example.lzz.knowledge.bean.ZhihuDailyStory;
import com.example.lzz.knowledge.db.DatabaseHelper;
import com.example.lzz.knowledge.tool.API;
import com.example.lzz.knowledge.tool.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

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

    private ZhihuDailyStory zhihuDailyStory;

    private Gson gson;
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    private int id;
    private String title;

    public DetailPresenter(Context context, DetailContract.View view){
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);

        gson = new Gson();
        helper = new DatabaseHelper(context, "DataBase.db", null,3);
        db = helper.getWritableDatabase();
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
        if (zhihuDailyStory == null){
            view.showLoadingError();
            return;
        }

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(zhihuDailyStory.getShare_url()));
            context.startActivity(intent);

        } catch (android.content.ActivityNotFoundException e){
            view.showBrowserNotFoundError();
        }
    }

    @Override
    public void shareAsText() {
        if (zhihuDailyStory == null){
            view.showSharingError();
            return;
        }

        try {
            Intent shareIntent = new Intent().setAction(Intent.ACTION_SEND).setType("text/plain");
            String shareText = "" + title + " ";

            shareText += zhihuDailyStory.getShare_url();
            shareText += "\n" + context.getString(R.string.share_extra);

            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share_to)));

        } catch (android.content.ActivityNotFoundException e){
            view.showLoadingError();
        }
    }

    @Override
    public void openUrl(WebView webView, String url) {
        try{
            context.startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)));
        } catch (android.content.ActivityNotFoundException ex){
            view.showBrowserNotFoundError();
        }
//        CustomTabsIntent.Builder customTabsIntent = new CustomTabsIntent.Builder()
//                .setToolbarColor(context.getResources().getColor(R.color.colorPrimary))
//                .setShowTitle(true);
//        CustomTabActivityHelper.openCustomTab(
//                (Activity)context,
//                customTabsIntent.build(),
//                Uri.parse(url),
//                new CustomFallback() {
//                    @Override
//                    public void openUri(Activity activity, Uri uri) {
//                        super.openUri(activity, uri);
//                    }
//                }
//        );
    }

    @Override
    public void copyLink() {
        if (zhihuDailyStory == null) {
            view.showCopyTextError();
            return;
        }

        ClipboardManager manager = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text", Html.fromHtml(zhihuDailyStory.getShare_url()));
        manager.setPrimaryClip(clipData);

        view.showTextCopied();
    }

    @Override
    public void addToOrDeleteFromBookmarks() {
        if (queryIfIsBookmarks()){
            ContentValues values = new ContentValues();
            values.put("bookmark", 0);
            db.update("Zhihu", values,"zhihu_id=?", new String[]{String.valueOf(id)});
            values.clear();

            view.showDeletedFromBookmarks();
        } else {
            ContentValues values = new ContentValues();
            values.put("bookmark", 1);
            db.update("Zhihu", values,"zhihu_id=?", new String[]{String.valueOf(id)});
            values.clear();

            Cursor cursor = db.query("Zhihu",null,"zhihu_id=?",
                    new String[]{String.valueOf(id)},null,null,null);
            if (cursor.moveToFirst()){
                do {
                    Log.d("Detail123", "isBookmark:" + cursor.getInt(cursor.getColumnIndex("bookmark")));
                } while (cursor.moveToNext());
            }
            cursor.close();

            view.showAddedToBookmarks();
        }
    }

    @Override
    public boolean queryIfIsBookmarks() {
        if (id == 0) {
            view.showLoadingError();
            return false;
        }

        Cursor cursor = helper.getReadableDatabase().query("Zhihu",null,"zhihu_id=?",
                new String[]{String.valueOf(id)},null,null,null);
        if (cursor.moveToFirst()){
            do {
                int isBookmarked = cursor.getInt(cursor.getColumnIndex("bookmark"));
                if (isBookmarked == 1){
                    return true;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        return false;
    }

    @Override
    public void requestData() {
        if (id == 0){
            view.showLoadingError();
            return;
        }
        view.showLoading();
        view.setTitle(title);

        HttpUtil.sendOKHttpRequest(API.ZHIHU_NEWS_DETAIL + id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                view.stopLoading();
                view.showLoadingError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            zhihuDailyStory = gson.fromJson(result, ZhihuDailyStory.class);
                            if (zhihuDailyStory.getBody() == null) {
                                view.showResultWithoutBody(zhihuDailyStory.getShare_url());
                            } else {
                                view.showResult(convertZhihuContent(zhihuDailyStory.getBody()));
                            }
                            view.showCover(zhihuDailyStory.getImage());
                        } catch (JsonSyntaxException e) {
                            view.showLoadingError();
                        }
                        view.stopLoading();
                    }
                });

            }

        });

    }

    private String convertZhihuContent(String preResult) {

        preResult = preResult.replace("<div class=\"img-place-holder\">", "");
        preResult = preResult.replace("<div class=\"headline\">", "");

        // 在api中，css的地址是以一个数组的形式给出，这里需要设置
        // api中还有js的部分，这里不再解析js
        // 不再选择加载网络css，而是加载本地assets文件夹中的css
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/zhihu_daily.css\" type=\"text/css\">";


        // 根据主题的不同确定不同的加载内容
        String theme = "<body className=\"\" onload=\"onLoaded()\">";
        if ((context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES){
            theme = "<body className=\"\" onload=\"onLoaded()\" class=\"night\">";
        }

        return new StringBuilder()
                .append("<!DOCTYPE html>\n")
                .append("<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n")
                .append("<head>\n")
                .append("\t<meta charset=\"utf-8\" />")
                .append(css)
                .append("\n</head>\n")
                .append(theme)
                .append(preResult)
                .append("</body></html>").toString();
    }

}
