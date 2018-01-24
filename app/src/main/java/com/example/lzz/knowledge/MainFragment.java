package com.example.lzz.knowledge;

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
import android.widget.Toast;

import com.example.lzz.knowledge.adapter.MainPagerAdapter;

/**
 * Created by ZZ on 2018/1/12.
 */

public class MainFragment extends Fragment{

    private ZhihuDailyFragment zhihuDailyFragment;
    private AnotherFragment anotherFragment;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MainPagerAdapter adapter;
    private FloatingActionButton fab;

    public static MainFragment newInstance(){
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            FragmentManager manager = getChildFragmentManager();
            zhihuDailyFragment = (ZhihuDailyFragment)manager.getFragment(savedInstanceState, "zhihudaily");
            anotherFragment = (AnotherFragment)manager.getFragment(savedInstanceState, "another");
        } else {
            zhihuDailyFragment = ZhihuDailyFragment.newInstance();
            anotherFragment = AnotherFragment.newInstance();
        }
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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "...", Toast.LENGTH_SHORT).show();

            }
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager manager = getChildFragmentManager();
        manager.putFragment(outState, "zhihudaily", zhihuDailyFragment);
        manager.putFragment(outState, "another", anotherFragment);
    }
}
