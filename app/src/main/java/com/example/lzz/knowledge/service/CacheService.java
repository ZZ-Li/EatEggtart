package com.example.lzz.knowledge.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.lzz.knowledge.bean.ZhihuDaily;
import com.example.lzz.knowledge.bean.ZhihuDailyStory;
import com.example.lzz.knowledge.db.DatabaseHelper;
import com.example.lzz.knowledge.utils.API;
import com.example.lzz.knowledge.utils.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CacheService extends Service {

    private static final String TAG = "CacheServiceTest";

    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public static final int TYPE_ZHIHU = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        helper = new DatabaseHelper(this,"DataBase.db",null,4);
        db = helper.getWritableDatabase();

        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.lzz.knowledge.LOCAL_BROADCAST");
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        manager.registerReceiver(new ContentLocalReceiver(), filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        deleteTimeoutContent();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void startZhihuCache(final int id){
        Cursor cursor = db.query("Zhihu",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                if (cursor.getInt(cursor.getColumnIndex("zhihu_id")) == id){
                    HttpUtil.sendOKHttpRequest(API.ZHIHU_NEWS_DETAIL + id, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Gson gson = new Gson();
                            String content = response.body().string();
                            ZhihuDailyStory dailyStory = gson.fromJson(content,ZhihuDailyStory.class);
                            if (dailyStory.getType() == 1){
                                HttpUtil.sendOKHttpRequest(dailyStory.getShare_url(), new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {

                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        ContentValues values = new ContentValues();
                                        values.put("zhihu_content", response.body().string());
                                        db.update("Zhihu", values, "zhihu_id = ?", new String[]{String.valueOf(id)});
                                        values.clear();
                                    }
                                });
                            } else {
                                ContentValues values = new ContentValues();
                                values.put("zhihu_content", content);
                                db.update("Zhihu", values, "zhihu_id = ?", new String[]{String.valueOf(id)});
                                values.clear();
                            }

                        }
                    });

                }

            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void deleteTimeoutContent(){
        SharedPreferences preferences = getSharedPreferences("user_settings", MODE_PRIVATE);

        Calendar calendar = Calendar.getInstance();
        long timeStamp = (calendar.getTimeInMillis() / 1000) - Long.parseLong(preferences.getString("time_of_saving_articles", "3"))*24*60*60;

        String[] whereArgs = new String[]{String.valueOf(timeStamp)};
        db.delete("Zhihu","zhihu_time < ? and bookmark != 1", whereArgs);
    }

    class ContentLocalReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int id = intent.getIntExtra("id",0);
            switch (intent.getIntExtra("type",-1)){
                case TYPE_ZHIHU:
                    startZhihuCache(id);
                    break;
                default:

            }
        }
    }


}
