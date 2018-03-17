package com.example.lzz.knowledge.ui.home;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lzz.knowledge.R;
import com.example.lzz.knowledge.adapter.ZhihuDailyAdapter;
import com.example.lzz.knowledge.bean.ZhihuDaily;
import com.example.lzz.knowledge.adapter.OnRecyclerViewOnClickListener;
import com.example.lzz.knowledge.utils.DateFormatTool;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by ASUS on 2018/1/12.
 */

public class ZhihuDailyFragment extends Fragment implements ZhihuDailyContract.View{

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private ZhihuDailyAdapter adapter;
    private FloatingActionButton fab;
    private TabLayout tabLayout;

    private DateFormatTool formatTool = new DateFormatTool();

    private ZhihuDailyContract.Presenter presenter;

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

        initViews(view);

        presenter.start();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                presenter.refresh();
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
                        presenter.loadMore(formatTool.ZhihuDailyDateFormat(calendar.getTimeInMillis()));
                    }
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isSlidingToLast = dy > 0;

                if (dy > 0){
                    fab.hide();
                } else {
                    fab.show();
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tabLayout.getSelectedTabPosition() == 0){
                    Calendar now = Calendar.getInstance();
                    now.set(mYear, mMonth, mDay);
                    DatePickerDialog dialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                            mYear = year;
                            mMonth = monthOfYear;
                            mDay = dayOfMonth;
                            Calendar calendar = Calendar.getInstance();
                            calendar.clear();
                            calendar.set(year, monthOfYear, dayOfMonth);
                            presenter.loadData(formatTool.ZhihuDailyDateFormat(calendar.getTimeInMillis()),
                                    false);
                        }
                    }, now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH));

                    dialog.setMaxDate(Calendar.getInstance());
                    Calendar minDate = Calendar.getInstance();
                    // 2013.5.20是知乎日报api首次上线
                    minDate.set(2013,5,20);
                    dialog.setMinDate(minDate);
                    dialog.vibrate(false);

                    dialog.show(getActivity().getFragmentManager(), "DatePickDialog");
                }
            }
        });

        return view;
    }

    @Override
    public void setPresenter(ZhihuDailyContract.Presenter presenter) {
        if (presenter != null){
            this.presenter = presenter;
        }
    }

    @Override
    public void initViews(View view) {
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh_layout);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);

        fab = (FloatingActionButton)getActivity().findViewById(R.id.fab);
        tabLayout = (TabLayout)getActivity().findViewById(R.id.tab_layout);
    }

    @Override
    public void showError() {
        Toast.makeText(getActivity(), R.string.loaded_failed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void stopLoading() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void showResults(ArrayList<ZhihuDaily.StoriesBean> list) {
        if (adapter == null){
            adapter = new ZhihuDailyAdapter(list);
            adapter.setItemClickListener(new OnRecyclerViewOnClickListener() {
                @Override
                public void OnItemClick(View v, int position) {
                    presenter.starReading(position);
                }
            });
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

}
