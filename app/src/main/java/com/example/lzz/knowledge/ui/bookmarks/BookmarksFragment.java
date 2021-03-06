package com.example.lzz.knowledge.ui.bookmarks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lzz.knowledge.R;
import com.example.lzz.knowledge.adapter.BookmarksAdapter;
import com.example.lzz.knowledge.adapter.OnBookmarkListOnClickListener;
import com.example.lzz.knowledge.bean.ZhihuDaily;
import com.example.lzz.knowledge.ui.home.MainActivity;

import java.util.ArrayList;

/**
 * Created by ASUS on 2018/3/1.
 */

public class BookmarksFragment extends Fragment implements BookmarksContract.View{

    private static final String TAG = "BookmarksFragment";

    private View view;

    private RecyclerView recyclerView;
    private BookmarksAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private LinearLayout nothingLayout;

    private boolean isEditing = false;
    private LinearLayout bottomLayout;
    private TextView delete_tv;
    private ArrayList<Integer> deletedList = new ArrayList<Integer>();

    private BookmarksContract.Presenter presenter;

    public static BookmarksFragment newInstance(){
        return new BookmarksFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null){
            ViewGroup parent = (ViewGroup)view.getParent();
            if (parent != null){
                parent.removeView(view);
            }
        }

        view = inflater.inflate(R.layout.fragment_list, container, false);

        initViews(view);

        setHasOptionsMenu(true);

        presenter.loadData(false);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadData(true);
            }
        });

        delete_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deleteSelectedData(deletedList);
                deletedList.clear();
            }
        });

        return view;
    }

    @Override
    public void setPresenter(BookmarksContract.Presenter presenter) {
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

        bottomLayout = (LinearLayout)view.findViewById(R.id.bookmark_bottom_editor_layout);
        delete_tv = (TextView)view.findViewById(R.id.delete_textView) ;

        nothingLayout = (LinearLayout)view.findViewById(R.id.nothing_layout);

        Log.d(TAG, "init view");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_bookmark_editor,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_bookmark_editor){
            if (!isEditing){
                isEditing = true;
                bottomLayout.setVisibility(View.VISIBLE);
                adapter.setShowDeletion(true);
            }else {
                isEditing = false;
                bottomLayout.setVisibility(View.GONE);
                adapter.setShowDeletion(false);
            }
            adapter.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showResults(ArrayList<ZhihuDaily.StoriesBean> list) {
        if (adapter == null){
            adapter = new BookmarksAdapter(getActivity(), list);
            adapter.setItemClickListener(new OnBookmarkListOnClickListener() {
                @Override
                public void OnItemClick(View v, int position, boolean isCheck) {
                    if (isEditing){

                        if(isCheck){
                            deletedList.add(position);
                        } else {
                            deletedList.remove((Integer) position);
                        }

                    }else {
                        presenter.startReading(position);
                    }
                }
            });
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

        if (list == null || list.isEmpty()){
            nothingLayout.setVisibility(View.VISIBLE);
        } else {
            nothingLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void notifyDataChanged() {
        presenter.loadData(true);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void stopLoading() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void hideDeleteBottomLayout() {
        if (bottomLayout.getVisibility() != View.GONE){
            isEditing = false;
            bottomLayout.setVisibility(View.GONE);
            adapter.setShowDeletion(false);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d(TAG, "isHidden : " + hidden);

        if (hidden){
            if (bottomLayout.getVisibility() != View.GONE){
                isEditing = false;
                bottomLayout.setVisibility(View.GONE);
                adapter.setShowDeletion(false);
            }

            if (deletedList.size() != 0){
                deletedList.clear();
            }
        }
    }

}
