package com.example.gallery;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;



public  class AdapterHolderAndStuff extends RecyclerView.ViewHolder {
    private ImageView mFirstImage;


    public AdapterHolderAndStuff(View itemView) {
        super(itemView);
        mFirstImage = (ImageView) itemView.findViewById(R.id.first);
    }


    public void bind(int a, Context context){
        //mFirstImage.setImageResource(a);
        Picasso.with(context).load(a).resize(200,200).centerCrop().into(mFirstImage);
    }


    public static class Adapter extends RecyclerView.Adapter<AdapterHolderAndStuff> {
        int[] list;
        Context mContext;
        Context activityContext;

        public Adapter(int[] list, Context context, Context context1) {
            this.list = list;
            mContext = context;
            activityContext = context1;
        }

        @Override
        public AdapterHolderAndStuff onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
            return new AdapterHolderAndStuff(view);
    }

        @Override
        public void onBindViewHolder(AdapterHolderAndStuff holder, final int position) {
            int a = list[position];
            holder.bind(a, mContext);
            holder.mFirstImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activityContext, Second.class);
                    intent.putExtra("Image", list[position]);
                    activityContext.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

}
