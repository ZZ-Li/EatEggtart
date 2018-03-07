package com.example.lzz.knowledge.ui.settings;


import android.support.v7.preference.Preference;

import com.example.lzz.knowledge.base.BasePresenter;
import com.example.lzz.knowledge.base.BaseView;

/**
 * Created by ASUS on 2018/3/6.
 */

public interface SettingContract {

    interface View extends BaseView<Presenter>{

        void showCleanImageCacheDone();
    }

    interface Presenter extends BasePresenter{

        void setNoPictureMode(Preference preference);

        void cleanImageCache();
    }
}
