package com.example.rhymebyrhyme.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rhymebyrhyme.R;
import com.example.rhymebyrhyme.model.Poem;

import java.util.LinkedList;



public class PoemsListAdapter extends RecyclerView.Adapter<PoemsListAdapter.MyListViewHolder> {
   LinkedList<Poem> poems = new LinkedList<>();
    Context context;

    public PoemsListAdapter(LinkedList list, Context context){
        this.poems = list;
        this.context = context;

    }

    @Override
    public PoemsListAdapter.MyListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.poem_item, parent, false);
        return new MyListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PoemsListAdapter.MyListViewHolder holder, int position) {
        holder.title.setText(poems.get(position).getTitle());
        holder.date.setText(poems.get(position).getDate());
        holder.likes.setText("" + poems.get(position).getLikes());
        holder.text.setText(poems.get(position).getText());
    }


    @Override
    public int getItemCount() {
        return poems.size();
    }

    public class MyListViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView date;
        TextView likes;
        TextView text;
        TextView full;
        ImageView heart;

        public MyListViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.poemTitle);
            title.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-BoldCondensed.ttf"));
            date = (TextView) itemView.findViewById(R.id.poemDate);
            date.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-LightItalic.ttf"));
            likes = (TextView) itemView.findViewById(R.id.poemLikes);
            likes.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf"));
            text = (TextView) itemView.findViewById(R.id.poemText);
            text.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf"));
            full = (TextView) itemView.findViewById(R.id.full);
            full.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf"));
            heart = (ImageView) itemView.findViewById(R.id.poem_item_heart);
        }
    }
}
