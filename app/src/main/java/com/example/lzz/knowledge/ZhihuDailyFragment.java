package com.example.lzz.knowledge;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lzz.knowledge.adapter.ZhihuDailyAdapter;
import com.example.lzz.knowledge.bean.ZhihuDaily;
import com.example.lzz.knowledge.util.API;
import com.example.lzz.knowledge.util.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by ZZ on 2018/1/12.
 */

public class ZhihuDailyFragment extends Fragment {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private ZhihuDailyAdapter adapter;

    private ArrayList<ZhihuDaily.StoriesBean> list = new ArrayList<ZhihuDaily.StoriesBean>();

    private Gson gson = new Gson();

    public static ZhihuDailyFragment newInstance(){
        return new ZhihuDailyFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh_layout);

        requestData();
        return view;
    }

    private void requestData(){
        HttpUtil.sendOKHttpRequest(API.ZHIHU_NEWS_LATEST, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseStr = response.body().string();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ZhihuDaily zhihuDaily = gson.fromJson(responseStr, ZhihuDaily.class);
                        for (ZhihuDaily.StoriesBean item : zhihuDaily.getStories()){
                            list.add(item);
                            adapter = new ZhihuDailyAdapter(list);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                });
            }
        });
    }


}
