package com.example.rhymebyrhyme.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rhymebyrhyme.PoemsByCategoryActivity;
import com.example.rhymebyrhyme.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.squareup.picasso.Picasso;


public class CategoryListAdapter extends RecyclerView.Adapter< CategoryListAdapter.MyListViewHolder> {

    int [] categories;
    Context context;
    String [] categoriesNames = {"Жизнь", "Война", "Любовь", "Религия",
            "Политика", "Семья", "Дружба", "Мистика", "История"};
    private DatabaseReference mRef;




    public CategoryListAdapter(int [] list, Context context) {
        categories = list;
        this.context = context;
    }

    @Override
    public CategoryListAdapter.MyListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CategoryListAdapter.MyListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryListAdapter.MyListViewHolder holder, final int position) {
        Picasso.with(context).load(categories[position]).resize(800,800).centerCrop().into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PoemsByCategoryActivity.class);
                intent.putExtra("category", categoriesNames[position]);
                context.startActivity(intent);


            }
        });




    }

    @Override
    public int getItemCount() {
      return  categories.length;
    }


    public class MyListViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;


        public MyListViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imagebutton);





        }
    }
}
