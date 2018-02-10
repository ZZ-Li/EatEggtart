package com.example.lzz.knowledge.home;

import com.example.lzz.knowledge.BasePresenter;
import com.example.lzz.knowledge.BaseView;
import com.example.lzz.knowledge.bean.ZhihuDaily;

import java.util.ArrayList;

/**
 * Created by ASUS on 2018/2/10.
 */

public interface ZhihuDailyContract {
    interface View extends BaseView<Presenter> {
        void showError();

        void showLoading();

        void stopLoading();

        void showResults(ArrayList<ZhihuDaily.StoriesBean> list);

    }

    interface Presenter extends BasePresenter {

        void load(String date, boolean isLoadMore);

        void refresh();

        void loadMore(String date);

        void starReading(int position);

    }
}
