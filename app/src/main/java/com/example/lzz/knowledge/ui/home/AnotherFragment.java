package com.example.lzz.knowledge.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lzz.knowledge.R;

import java.util.ArrayList;

/**
 * Created by ASUS on 2018/1/24.
 */

public class AnotherFragment extends Fragment implements AnotherContract.View{

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    public static AnotherFragment newInstance(){
        return new AnotherFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        initViews(view);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    @Override
    public void setPresenter(AnotherContract.Presenter presenter) {

    }

    @Override
    public void initViews(View view) {
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh_layout);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showResults(ArrayList list) {

    }
}
