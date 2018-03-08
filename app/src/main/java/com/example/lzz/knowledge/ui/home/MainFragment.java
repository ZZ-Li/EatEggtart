package com.example.lzz.knowledge.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lzz.knowledge.R;
import com.example.lzz.knowledge.adapter.MainPagerAdapter;

import java.util.Calendar;

/**
 * Created by ASUS on 2018/1/12.
 */

public class MainFragment extends Fragment{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MainPagerAdapter adapter;
    private FloatingActionButton fab;

    private ZhihuDailyFragment zhihuDailyFragment;
    private AnotherFragment anotherFragment;

    private ZhihuDailyPresenter zhihuDailyPresenter;

    public static MainFragment newInstance(){
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            FragmentManager manager = getChildFragmentManager();
            zhihuDailyFragment = (ZhihuDailyFragment)manager.getFragment(savedInstanceState, "zhihuDaily");
            anotherFragment = (AnotherFragment)manager.getFragment(savedInstanceState, "another");
        } else {
            zhihuDailyFragment = ZhihuDailyFragment.newInstance();
            anotherFragment = AnotherFragment.newInstance();
        }

        zhihuDailyPresenter = new ZhihuDailyPresenter(getActivity(), zhihuDailyFragment);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        tabLayout = (TabLayout)view.findViewById(R.id.tab_layout);
        viewPager = (ViewPager)view.findViewById(R.id.view_pager);
        fab = (FloatingActionButton)view.findViewById(R.id.fab);

        adapter = new MainPagerAdapter(getChildFragmentManager(),
                getActivity(), zhihuDailyFragment, anotherFragment);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1){
                    fab.hide();
                }else {
                    fab.show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager manager = getChildFragmentManager();
        manager.putFragment(outState, "zhihuDaily", zhihuDailyFragment);
        manager.putFragment(outState, "another", anotherFragment);
    }
}
