package com.example.lzz.knowledge.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lzz.knowledge.R;
import com.example.lzz.knowledge.bean.ZhihuDaily;

import java.util.List;

/**
 * Created by ASUS on 2018/1/18.
 */

public class ZhihuDailyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = 1;
    private boolean isShowFooter = true;

    private Context context;
    private List<ZhihuDaily.StoriesBean> list;
    private OnRecyclerViewOnClickListener mListener;

    public ZhihuDailyAdapter(List<ZhihuDaily.StoriesBean> list) {
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
        switch (viewType){
            case TYPE_NORMAL:
                View view = LayoutInflater.from(context)
                        .inflate(R.layout.list_main_item_layout, parent, false);
                NormalViewHolder holder = new NormalViewHolder(view, mListener);
                return holder;
            case TYPE_FOOTER:
                return new FootViewHolder(LayoutInflater.from(context)
                        .inflate(R.layout.list_footer_layout, parent, false));
        }
       return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NormalViewHolder){
            ZhihuDaily.StoriesBean item = list.get(position);
            if (item.getImages().get(0) == null){
                ((NormalViewHolder) holder).imageView.setImageResource(R.drawable.image_load_error);
            } else {
                Glide.with(context)
                        .load(item.getImages().get(0))
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .error(R.drawable.image_load_error)
                        .centerCrop()
                        .into(((NormalViewHolder) holder).imageView);
            }
            ((NormalViewHolder) holder).titleTextView.setText(item.getTitle());
        }
    }

    public void isShowFooter(boolean isShowFooter){
        this.isShowFooter = isShowFooter;
    }

    @Override
    public int getItemCount() {
        if (isShowFooter) {
            return list.size() + 1;
        } else {
            return list.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == list.size() && isShowFooter){
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    static class NormalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imageView;
        private TextView titleTextView;
        private OnRecyclerViewOnClickListener listener;

        public NormalViewHolder(View itemView, OnRecyclerViewOnClickListener listener) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.item_imageView);
            titleTextView = (TextView)itemView.findViewById(R.id.item_textView);
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

    static class FootViewHolder extends RecyclerView.ViewHolder{
        public FootViewHolder(View itemView) {
            super(itemView);
        }
    }
}
