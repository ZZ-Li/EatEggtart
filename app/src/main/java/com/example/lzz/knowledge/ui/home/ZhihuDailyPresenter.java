package com.example.lzz.knowledge.ui.home;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lzz.knowledge.bean.ZhihuDaily;
import com.example.lzz.knowledge.db.DatabaseHelper;
import com.example.lzz.knowledge.ui.detail.DetailActivity;
import com.example.lzz.knowledge.utils.API;
import com.example.lzz.knowledge.utils.DateFormatTool;
import com.example.lzz.knowledge.utils.HttpUtil;
import com.example.lzz.knowledge.utils.NetworkState;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by ASUS on 2018/1/12.
 */

public class ZhihuDailyPresenter implements ZhihuDailyContract.Presenter{

    private Context context;
    private ZhihuDailyContract.View view;

    private ArrayList<ZhihuDaily.StoriesBean> list = new ArrayList<ZhihuDaily.StoriesBean>();

    private DateFormatTool formatTool = new DateFormatTool();
    private Gson gson = new Gson();

    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public ZhihuDailyPresenter(Context context, ZhihuDailyContract.View view) {
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
        helper = new DatabaseHelper(context, "DataBase.db", null, 3);
        db = helper.getWritableDatabase();
    }

    @Override
    public void start() {
        loadData(formatTool.ZhihuDailyDateFormat(Calendar.getInstance().getTimeInMillis()),
                false);
    }

    @Override
    public void loadData(String date,final boolean isLoadMore) {

        if (!isLoadMore){
            view.showLoading();
        }
        if (NetworkState.networkConnected(context)){
            HttpUtil.sendOKHttpRequest(API.ZHIHU_HISTORY + date, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.showError();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String result = response.body().string();
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                ZhihuDaily zhihuDaily = gson.fromJson(result, ZhihuDaily.class);

                                if (!isLoadMore) {
                                    list.clear();
                                }

                                ContentValues values = new ContentValues();
                                for (ZhihuDaily.StoriesBean item : zhihuDaily.getStories()){
                                    list.add(item);
                                    if (!queryIdExists(item.getId())){
                                        db.beginTransaction();
                                        try {
                                            DateFormat format = new SimpleDateFormat("yyyyMMdd");
                                            Date date = format.parse(zhihuDaily.getDate());
                                            values.put("zhihu_id", item.getId());
                                            values.put("zhihu_news", gson.toJson(item));
                                            values.put("zhihu_time", date.getTime() / 1000);
                                            db.insert("Zhihu", null, values);
                                            values.clear();
                                            db.setTransactionSuccessful();
                                        } catch (Exception e){
                                            e.printStackTrace();
                                        } finally {
                                            db.endTransaction();
                                        }
                                    }
                                }

                                view.showResults(list);
                            } catch (Exception e){
                                e.printStackTrace();
                                view.showError();
                            }
                            view.stopLoading();
                        }
                    });
                }
            });

        } else {
            if (!isLoadMore){
                list.clear();
                Cursor cursor = db.query("Zhihu", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        ZhihuDaily.StoriesBean storiesBean = gson.fromJson(
                                cursor.getString(cursor.getColumnIndex("zhihu_news")), ZhihuDaily.StoriesBean.class);
                        list.add(storiesBean);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                view.stopLoading();
                view.showResults(list);
            } else {
                view.showError();
            }

        }

    }

    @Override
    public void refresh() {
        loadData(formatTool.ZhihuDailyDateFormat(Calendar.getInstance().getTimeInMillis()),
                false);
    }

    @Override
    public void loadMore(String date) {
        loadData(date, true);
    }

    @Override
    public void starReading(int position) {

        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("id", list.get(position).getId());
        intent.putExtra("title", list.get(position).getTitle());
        context.startActivity(intent);
    }

    private boolean queryIdExists(int id){
        Cursor cursor = db.query("Zhihu",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                if (id == cursor.getInt(cursor.getColumnIndex("zhihu_id"))){
                    return true;
                }
            } while (cursor.moveToNext());
        }
//        if (cursor.moveToNext()){
//            if (id == cursor.getInt(cursor.getColumnIndex("zhihu_id"))){
//                return true;
//            }
//        }
        cursor.close();
        return false;
    }
}
