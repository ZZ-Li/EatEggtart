package com.example.lzz.knowledge.bookmarks;

import com.example.lzz.knowledge.base.BasePresenter;
import com.example.lzz.knowledge.base.BaseView;
import com.example.lzz.knowledge.bean.ZhihuDaily;

import java.util.ArrayList;

/**
 * Created by ASUS on 2018/3/1.
 */

public interface BookmarksContract {

    interface View extends BaseView<Presenter>{

        void showResults(ArrayList<ZhihuDaily.StoriesBean> list);

        void notifyDataChanged();

        void showLoading();

        void stopLoading();

    }

    interface Presenter extends BasePresenter{

        void loadData(boolean isRefresh);

        void startReading(int position);

        void checkForFreshData();

    }
}
