package com.example.lzz.knowledge.ui.home;

import com.example.lzz.knowledge.base.BasePresenter;
import com.example.lzz.knowledge.base.BaseView;

import java.util.ArrayList;

/**
 * Created by ASUS on 2018/2/10.
 */

public interface AnotherContract {
    interface View extends BaseView<Presenter>{

        void showError();

        void showLoading();

        void stopLoading();

        void showResults(ArrayList list);

    }

    interface Presenter extends BasePresenter{

        void load(String date, boolean isLoadMore);

        void refresh();

        void loadMore(String date);

        void starReading(int position);
    }
}
