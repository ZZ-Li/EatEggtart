package com.example.lzz.knowledge.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.lzz.knowledge.R;
import com.example.lzz.knowledge.bean.Meizhi;

import java.util.List;

/**
 * Created by ASUS on 2018/1/26.
 */

public class MeizhiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Meizhi.ResultsBean> list;
    private OnRecyclerViewOnClickListener mListener;

    public MeizhiAdapter(List<Meizhi.ResultsBean> list) {
        this.list = list;
    }

    public void setItemClickListener(OnRecyclerViewOnClickListener listener){
        this.mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.meizhi_item_layout, parent, false);
        ViewHolder holder = new ViewHolder(view, mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Meizhi.ResultsBean item = list.get(position);

        if (holder instanceof ViewHolder){
            Glide.with(context)
                    .load(item.getUrl())
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .error(R.drawable.image_load_error)
                    .into(((ViewHolder) holder).imageView);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView;
        OnRecyclerViewOnClickListener listener;

        public ViewHolder(View itemView, OnRecyclerViewOnClickListener listener) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.meizhi_image_view);
            this.listener = listener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (listener != null){
                listener.OnItemClick(v, getLayoutPosition());
            }
        }
    }
}
