package com.example.lzz.knowledge.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lzz.knowledge.DetailActivity;
import com.example.lzz.knowledge.R;
import com.example.lzz.knowledge.adapter.ZhihuDailyAdapter;
import com.example.lzz.knowledge.bean.ZhihuDaily;
import com.example.lzz.knowledge.interfa.OnRecyclerViewOnClickListener;
import com.example.lzz.knowledge.util.API;
import com.example.lzz.knowledge.util.DateFormat;
import com.example.lzz.knowledge.util.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by ASUS on 2018/1/12.
 */

public class ZhihuDailyFragment extends Fragment {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private ZhihuDailyAdapter adapter;

    private List<ZhihuDaily.StoriesBean> list = new ArrayList<ZhihuDaily.StoriesBean>();

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

        requestData(format.ZhihuDailyDateFormat(Calendar.getInstance().getTimeInMillis()), false);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                calendar.set(mYear, mMonth, mDay);
                requestData(format.ZhihuDailyDateFormat(Calendar.getInstance().getTimeInMillis()), false);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = (LinearLayoutManager)recyclerView.getLayoutManager();
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();

                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast){
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(mYear, mMonth, --mDay);
                        requestData(format.ZhihuDailyDateFormat(calendar.getTimeInMillis()), true);
                    }
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isSlidingToLast = dy > 0;
            }
        });

        return view;
    }

    private void requestData(String date, final boolean isLoadMore){
        if (!isLoadMore){
            refreshLayout.setRefreshing(true);
        }
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
                        if (!isLoadMore){

                            if (list == null){

                                for (ZhihuDaily.StoriesBean item : zhihuDaily.getStories())
                                    list.add(item);

                            } else {

                                list.clear();
                                for (ZhihuDaily.StoriesBean item : zhihuDaily.getStories())
                                    list.add(item);

                            }
                            adapter = new ZhihuDailyAdapter(list);
                            adapter.setItemClickListener(new OnRecyclerViewOnClickListener() {
                                @Override
                                public void OnItemClick(View v, int position) {
                                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                                    intent.putExtra("id", list.get(position).getId());
                                    startActivity(intent);
                                }
                            });
                            recyclerView.setAdapter(adapter);
                            refreshLayout.setRefreshing(false);
                        } else {

                            for (ZhihuDaily.StoriesBean item : zhihuDaily.getStories())
                                list.add(item);
                            adapter.notifyDataSetChanged();

                        }

                    }
                });
            }
        });
    }

}
