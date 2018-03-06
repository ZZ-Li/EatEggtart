package com.example.lzz.knowledge.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.lzz.knowledge.UI.home.GankFragment;
import com.example.lzz.knowledge.R;
import com.example.lzz.knowledge.UI.home.ZhihuDailyFragment;

/**
 * Created by ASUS on 2018/1/24.
 */

public class MainPagerAdapter extends FragmentPagerAdapter{

    private String[] titles;
    private Context context;

    private ZhihuDailyFragment zhihuDailyFragment;
    private GankFragment gankFragment;

    public MainPagerAdapter(FragmentManager fm, Context context,
                            ZhihuDailyFragment zhihuDailyFragment,
                            GankFragment gankFragment) {
        super(fm);
        this.context = context;
        this.zhihuDailyFragment = zhihuDailyFragment;
        this.gankFragment = gankFragment;
        titles = new String[] {
                context.getResources().getString(R.string.zhihu_daily),
                context.getResources().getString(R.string.buzhidao)};
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1){
            return gankFragment;
        } else {
            return zhihuDailyFragment;
        }
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
