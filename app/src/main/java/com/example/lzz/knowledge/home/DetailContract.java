package com.example.lzz.knowledge.home;

import android.webkit.WebView;

import com.example.lzz.knowledge.base.BasePresenter;
import com.example.lzz.knowledge.base.BaseView;

/**
 * Created by ASUS on 2018/2/28.
 */

public interface DetailContract {

    interface View extends BaseView<Presenter> {

        void showLoading();

        void stopLoading();

        void showResult(String result);

        void showResultWithoutBody(String url);

        void showLoadingError();

        void showSharingError();

        void showCover(String url);

        void setTitle(String title);

        void setImageMode(boolean showImage);

        void showBrowserNotFoundError();

        void showTextCopied();

        void showCopyTextError();

        void showAddedToBookmarks();

        void showDeletedFromBookmarks();
    }

    interface Presenter extends BasePresenter {

        void requestData();

        void openUrl(WebView webView, String url);

        void openInBrowser();

        void shareAsText();

        void copyText();

        void copyLike();

        void addToOrDeleteFromBookmarks();

        boolean queryIfIsBookmarks();

    }
}
