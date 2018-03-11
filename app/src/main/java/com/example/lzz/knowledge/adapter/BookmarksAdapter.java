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

import java.util.ArrayList;

/**
 * Created by ASUS on 2018/3/2.
 */

public class BookmarksAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private ArrayList<ZhihuDaily.StoriesBean> list;

    private OnRecyclerViewOnClickListener mListener;

    public BookmarksAdapter(Context context, ArrayList<ZhihuDaily.StoriesBean> list) {
        this.context = context;
        this.list = list;
    }

    public void setItemClickListener(OnRecyclerViewOnClickListener listener){
        this.mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false);
        NormalViewHolder holder = new NormalViewHolder(view, mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NormalViewHolder){
            ZhihuDaily.StoriesBean item = list.get(position);
            Glide.with(context)
                    .load(item.getImages().get(0))
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .centerCrop()
                    .error(R.drawable.image_load_error)
                    .into(((NormalViewHolder) holder).imageView);
            ((NormalViewHolder) holder).textView.setText(item.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class NormalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView;
        TextView textView;
        OnRecyclerViewOnClickListener listener;

        public NormalViewHolder(View itemView, OnRecyclerViewOnClickListener listener) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.item_imageView);
            textView = (TextView)itemView.findViewById(R.id.item_textView);
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
