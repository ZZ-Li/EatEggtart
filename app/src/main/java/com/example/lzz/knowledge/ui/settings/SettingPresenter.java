package com.example.lzz.knowledge.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.preference.Preference;

import com.bumptech.glide.Glide;
import com.example.lzz.knowledge.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ASUS on 2018/3/7.
 */

public class SettingPresenter implements SettingContract.Presenter {

    private Context context;
    private SettingContract.View view;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static final int CLEAR_IMAGE_CACHE_DONE = 1;

    @SuppressWarnings("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CLEAR_IMAGE_CACHE_DONE:
                    view.showCleanImageCacheDone();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public SettingPresenter(Context context, SettingContract.View view){
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
        sharedPreferences = context.getSharedPreferences("user_settings", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    @Override
    public void start() {

    }

    @Override
    public void setNoPictureMode(Preference preference) {
        editor.putBoolean("no_picture_mode",
                preference.getSharedPreferences().getBoolean("no_picture_mode", false));
        editor.apply();
    }

    @Override
    public void cleanImageCache() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
                Message message = new Message();
                message.what = CLEAR_IMAGE_CACHE_DONE;
                handler.sendMessage(message);
            }
        }).start();
        Glide.get(context).clearMemory();
    }

    @Override
    public void setTimeOfSavingArticles(Preference preference, String newValue) {
        editor.putString("time_of_saving_articles", newValue);
        editor.apply();
    }

    @Override
    public String getTimeSummary() {
        String[] options = context.getResources().getStringArray(R.array.time_to_save_opts);
        String str = sharedPreferences.getString("time_of_saving_articles", "3");
        switch (str){
            case "1":
                return options[0];
            case "7":
                return options[2];
            default:
                return options[1];
        }
    }

}
