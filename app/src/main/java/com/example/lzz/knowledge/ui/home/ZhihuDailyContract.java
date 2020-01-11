package com.example.lzz.knowledge.ui.home;

import com.example.lzz.knowledge.base.BasePresenter;
import com.example.lzz.knowledge.base.BaseView;
import com.example.lzz.knowledge.bean.ZhihuDaily;

import java.util.ArrayList;

/**
 * Created by ASUS on 2018/1/12.
 */

public interface ZhihuDailyContract {
    interface View extends BaseView<Presenter> {
        void showError();

        void showLoading();

        void stopLoading();

        void loadMorePage();

        void showResults(ArrayList<ZhihuDaily.StoriesBean> list);

    }

    interface Presenter extends BasePresenter {

        void loadData(String date, boolean isLoadMore);

        void refresh();

        void loadMore(String date);

        void starReading(int position);

    }
}
