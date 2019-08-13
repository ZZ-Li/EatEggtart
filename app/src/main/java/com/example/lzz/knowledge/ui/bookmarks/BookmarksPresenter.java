package com.example.lzz.knowledge.ui.bookmarks;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.lzz.knowledge.bean.ZhihuDaily;
import com.example.lzz.knowledge.db.DatabaseHelper;
import com.example.lzz.knowledge.ui.detail.DetailActivity;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by ASUS on 2018/3/2.
 */

public class BookmarksPresenter implements BookmarksContract.Presenter {

    private Context context;
    private BookmarksContract.View view;

    private ArrayList<ZhihuDaily.StoriesBean> list = new ArrayList<ZhihuDaily.StoriesBean>();
    private Gson gson;

    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public BookmarksPresenter(Context context, BookmarksContract.View view) {
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
        gson = new Gson();
        helper = new DatabaseHelper(context,"DataBase.db",null,4);
        db = helper.getWritableDatabase();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadData(boolean isRefresh) {
        if (!isRefresh){
            view.showLoading();
        } else {
            list.clear();
        }
        checkForFreshData();

        view.showResults(list);

        view.stopLoading();
    }

    @Override
    public void checkForFreshData() {
        Cursor cursor = db.query("Zhihu",null,
                "bookmark = ?", new String[]{"1"},null,null,null);
        if (cursor.moveToFirst()){
            do {
                ZhihuDaily.StoriesBean zhihuDaily = gson.fromJson(cursor.getString(cursor.getColumnIndex("zhihu_news")), ZhihuDaily.StoriesBean.class);
                list.add(zhihuDaily);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    @Override
    public void startReading(int position) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("id", list.get(position).getId());
        intent.putExtra("title", list.get(position).getTitle());
        context.startActivity(intent);
    }

    @Override
    public void deleteSelectedData(ArrayList deletedList) {
        if (deletedList != null){
            ContentValues values = new ContentValues();
            for (Object position : deletedList){
                int id = list.get((int)position).getId();
                Log.d("BookTest","id: " +id);
                values.put("bookmark", 0);
                db.update("Zhihu",values,"zhihu_id = ?", new String[]{String.valueOf(id)});
                values.clear();
            }
        }

        view.hideDeleteBottomLayout();
        view.notifyDataChanged();
    }

}
