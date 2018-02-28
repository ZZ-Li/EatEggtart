package com.example.lzz.knowledge.home;

import android.webkit.WebView;

import com.example.lzz.knowledge.base.BasePresenter;
import com.example.lzz.knowledge.base.BaseView;

/**
 * Created by ASUS on 2018/2/28.
 */

public interface DetailContract {

    interface View extends BaseView<Persenter> {

        void showLoading();

        void stopLoading();

        void showLoadingError();

        void showSharingError();

        void showResult(String result);

        void showResultWithoutBody(String url);

        void showCover(String url);

        void setTitle(String title);

        void setImageMode(boolean showImage);

        void showBrowserNotFoundError();

        void showTextCopied();

        void showCopyTextError();

        void showAddedToBookmarks();

        void showDeletedFromBookmarks();
    }

    interface Persenter extends BasePresenter {

        void openInBrowser();

        void shareAsText();

        void openUrl(WebView webView, String url);

        void copyText();

        void copyLike();

        void addToOrDeleteFromBookmarks();

        boolean queryIfIsBookmarks();

        void requestData();
    }
}
