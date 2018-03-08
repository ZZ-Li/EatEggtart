package com.example.lzz.knowledge.ui.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lzz.knowledge.R;

/**
 * Created by ASUS on 2018/2/28.
 */

public class DetailFragment extends Fragment implements DetailContract.View{

    private Context context;
    private DetailContract.Presenter presenter;

    private SwipeRefreshLayout refreshLayout;
    private CollapsingToolbarLayout toolbarLayout;
    private Toolbar toolbar;
    private NestedScrollView scrollView;
    private ImageView imageView;
    private WebView webView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_detail, container, false);

       initViews(view);

       setHasOptionsMenu(true);

       presenter.requestData();

       toolbar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               scrollView.smoothScrollTo(0, 0);
           }
       });

       refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {
               presenter.requestData();
           }
       });

        return view;
    }

    public DetailFragment() {
        super();
    }

    @Override
    public void initViews(View view) {
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh_layout);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);

        imageView = (ImageView)view.findViewById(R.id.detail_image_view);
        scrollView = (NestedScrollView)view.findViewById(R.id.scrollView);
        toolbarLayout = (CollapsingToolbarLayout)view.findViewById(R.id.collapsing_toolbar_layout);
        toolbar = (Toolbar)view.findViewById(R.id.detail_tool_bar);

        DetailActivity activity = (DetailActivity)getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView = (WebView)view.findViewById(R.id.web_view);
        webView.setScrollbarFadingEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        // 缩放，设置为不可缩放
        webView.getSettings().setBuiltInZoomControls(false);
        // 缓存
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 开启DOM storage API功能
        webView.getSettings().setDomStorageEnabled(true);
        // 开启application Cache功能
        webView.getSettings().setAppCacheEnabled(false);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                presenter.openUrl(view, url);
                return true;
            }
        });
    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {
        if (presenter != null){
            this.presenter = presenter;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_more, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            getActivity().onBackPressed();
        } else if (id == R.id.action_more){
            final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
            View view = getActivity().getLayoutInflater().inflate(R.layout.reading_action_sheet, null);

            if (presenter.queryIfIsBookmarks()){
                ((TextView)view.findViewById(R.id.bottom_sheet_bookmark_tv))
                        .setText(R.string.action_delete_from_bookmarks);
                ((ImageView)view.findViewById(R.id.bottom_sheet_bookmark_iv))
                        .setColorFilter(getContext().getResources().getColor(R.color.colorPrimary));
            }

            view.findViewById(R.id.layout_bookmark).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    presenter.addToOrDeleteFromBookmarks();
                }
            });

            view.findViewById(R.id.layout_copy_link).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    presenter.copyLink();
                }
            });

            view.findViewById(R.id.layout_open_in_browser).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    presenter.openInBrowser();
                }
            });

            view.findViewById(R.id.layout_share_text).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    presenter.shareAsText();
                }
            });

            dialog.setContentView(view);
            dialog.show();
        }
        return true;
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
    public void showResult(String result) {
        webView.loadDataWithBaseURL("x-data://base",result,"text/html",
                "utf-8",null);
    }

    @Override
    public void showResultWithoutBody(String url) {
        webView.loadUrl(url);
    }

    @Override
    public void showLoadingError() {
        Snackbar.make(imageView, R.string.loaded_failed, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.requestData();
                    }
                }).show();
    }

    @Override
    public void showSharingError() {
        Toast.makeText(context, R.string.share_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCover(String url) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void setTitle(String title) {
        toolbarLayout.setTitle(title);
        toolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        toolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
    }

    @Override
    public void setImageMode(boolean showImage) {
        webView.getSettings().setBlockNetworkImage(showImage);
    }

    @Override
    public void showBrowserNotFoundError() {
        Toast.makeText(context, R.string.no_browser_found, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showTextCopied() {
        Toast.makeText(context, R.string.copied_to_clipboard, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCopyTextError() {
        Toast.makeText(context, R.string.copied_to_clipboard_failed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAddedToBookmarks() {
        Toast.makeText(context, R.string.added_to_bookmarks, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDeletedFromBookmarks() {
        Toast.makeText(context, R.string.deleted_from_bookmarks, Toast.LENGTH_SHORT).show();
    }
}
