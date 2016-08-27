package com.picmap.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.picmap.R;
import com.picmap.models.ImageModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Yur on 21/08/16.
 */
public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Integer> selected=new ArrayList<>();
    Context context;
    List<ImageModel> data = new ArrayList<>();
    ArrayList<Integer> selecteditems=new ArrayList<>();


    public GalleryAdapter(Context context, List<ImageModel> data) {
        this.context = context;
        this.data = data;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item, parent, false);
        viewHolder = new MyItemHolder(v);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        Glide.with(context).load(data.get(position).getUrl())
                .thumbnail(0.5f)
                .override(200,200)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(((MyItemHolder) holder).mImg);

    }


    @Override
    public int getItemCount() {
        return data.size();
    }



    public static class MyItemHolder extends RecyclerView.ViewHolder {
        ImageView mImg;


        public ImageView getmImg() {
            return mImg;
        }

        public void setmImg(ImageView mImg) {
            this.mImg = mImg;
        }


        public MyItemHolder(View itemView) {
            super(itemView);

            mImg = (ImageView) itemView.findViewById(R.id.item_img);;
        }


    }


}