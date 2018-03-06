package com.example.lzz.knowledge.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lzz.knowledge.R;
import com.example.lzz.knowledge.adapter.MeizhiAdapter;
import com.example.lzz.knowledge.bean.Meizhi;
import com.example.lzz.knowledge.tool.API;
import com.example.lzz.knowledge.tool.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by ASUS on 2018/1/26.
 */

public class MeizhiFragment extends Fragment {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private MeizhiAdapter adapter;

    // 加载妹纸图的页数
    private int page = 1;
    private List<Meizhi.ResultsBean> list = new ArrayList<Meizhi.ResultsBean>();

    private Gson gson = new Gson();

    public static MeizhiFragment newInstance(){
        return new MeizhiFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh_layout);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData( 1, false);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager)recyclerView.getLayoutManager();
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    int totalCount = layoutManager.getItemCount();
                    int[] lastPosition = layoutManager.findLastCompletelyVisibleItemPositions(
                            new int[layoutManager.getSpanCount()]);
                    int max = lastPosition[0];
                    for (int value : lastPosition){
                        if (value > max){
                            max = value;
                        }
                    }
                    int lastVisibleItem = max;
                    if (lastVisibleItem >= totalCount - 1 && isSlidingToLast){
                        requestData(++page, true);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isSlidingToLast = dy > 0;
            }
        });

        requestData(page, false);
        return view;
    }

    private void requestData(int page, final boolean isLoadMore){
        refreshLayout.setRefreshing(true);
        HttpUtil.sendOKHttpRequest(API.MEIZHI + page, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                        Toast.makeText(getActivity(), "加载失败啦！", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseStr = response.body().string();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Meizhi meizhi = gson.fromJson(responseStr, Meizhi.class);
                        if (!isLoadMore){

                            if (list == null){

                                for (Meizhi.ResultsBean item : meizhi.getResults())
                                    list.add(item);

                            } else {

                                list.clear();
                                for (Meizhi.ResultsBean item : meizhi.getResults())
                                    list.add(item);

                            }

                            if (adapter == null){
                                adapter = new MeizhiAdapter(list);
                                recyclerView.setAdapter(adapter);
                            }else {
                                adapter.notifyDataSetChanged();
                            }

                        } else {

                            for (Meizhi.ResultsBean item : meizhi.getResults())
                                list.add(item);
                            adapter.notifyDataSetChanged();

                        }
                        refreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }
}
