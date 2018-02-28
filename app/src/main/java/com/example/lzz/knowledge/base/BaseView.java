package com.example.lzz.knowledge.base;

import android.view.View;

/**
 * Created by ASUS on 2018/2/10.
 */

public interface BaseView<T> {

    void setPresenter(T presenter);

    void initViews(View view);
}
