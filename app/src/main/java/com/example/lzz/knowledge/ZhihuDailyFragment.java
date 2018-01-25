package com.example.lzz.knowledge;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lzz.knowledge.adapter.ZhihuDailyAdapter;
import com.example.lzz.knowledge.bean.ZhihuDaily;
import com.example.lzz.knowledge.util.API;
import com.example.lzz.knowledge.util.DateFormat;
import com.example.lzz.knowledge.util.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

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

    private DateFormat format = new DateFormat();
    private Gson gson = new Gson();

    private int mYear = Calendar.getInstance().get(Calendar.YEAR);
    private int mMonth = Calendar.getInstance().get(Calendar.MONTH);
    private int mDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

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
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);

        requestData(format.ZhihuDailyDateFormat(Calendar.getInstance().getTimeInMillis()));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData(format.ZhihuDailyDateFormat(Calendar.getInstance().getTimeInMillis()));
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        return view;
    }

    private void requestData(String date){
        refreshLayout.setRefreshing(true);
        HttpUtil.sendOKHttpRequest(API.ZHIHU_HISTORY + date, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
                        refreshLayout.setRefreshing(false);
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
                            refreshLayout.setRefreshing(false);
                        }
                    }
                });
            }
        });
    }


}
