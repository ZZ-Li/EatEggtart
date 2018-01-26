package com.example.lzz.knowledge.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.lzz.knowledge.home.AnotherFragment;
import com.example.lzz.knowledge.R;
import com.example.lzz.knowledge.home.ZhihuDailyFragment;

/**
 * Created by ASUS on 2018/1/24.
 */

public class MainPagerAdapter extends FragmentPagerAdapter{

    private String[] titles;
    private Context context;

    private ZhihuDailyFragment zhihuDailyFragment;
    private AnotherFragment anotherFragment;

    public MainPagerAdapter(FragmentManager fm, Context context,
                            ZhihuDailyFragment zhihuDailyFragment,
                            AnotherFragment anotherFragment) {
        super(fm);
        this.context = context;
        this.zhihuDailyFragment = zhihuDailyFragment;
        this.anotherFragment = anotherFragment;
        titles = new String[] {
                context.getResources().getString(R.string.zhihu_daily),
                context.getResources().getString(R.string.buzhidao)};
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1){
            return anotherFragment;
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
