package com.example.rhymebyrhyme.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rhymebyrhyme.PoemProfileActivity;
import com.example.rhymebyrhyme.PoemsByCategoryActivity;
import com.example.rhymebyrhyme.R;
import com.example.rhymebyrhyme.model.Poem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;

/**
 * Created by Марат on 16.07.2017.
 */

public class PoemsByCategoryListAdapter extends RecyclerView.Adapter< PoemsByCategoryListAdapter.MyListViewHolder> {

    private DatabaseReference mRef;
    LinkedList<Poem> poems;
    Context context;
    private FirebaseAuth mAuth;



    public PoemsByCategoryListAdapter(LinkedList<Poem> poems, Context context) {
        this.poems = poems;
        this.context = context;
    }

    @Override
    public PoemsByCategoryListAdapter.MyListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.poem_by_category_item, parent, false);
        return new PoemsByCategoryListAdapter.MyListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PoemsByCategoryListAdapter.MyListViewHolder holder, final int position) {
        mRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        holder.title.setText(poems.get(position).getTitle());
        holder.date.setText(poems.get(position).getDate());
        holder.likes.setText("" + poems.get(position).getLikes());
        holder.text.setText(Html.fromHtml(poems.get(position).getText()));
        mRef.child("users").child(poems.get(position).getuId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                holder.authorName.setText(""+ dataSnapshot.child("surname").getValue()+" " + dataSnapshot.child("name").getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mRef = FirebaseDatabase.getInstance().getReference();
        mRef.child("poems").child(poems.get(position).getuId()).child(""+ poems.get(position).getId())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.child("likesAuthors").child(mAuth.getCurrentUser().getUid()).child("like").getValue()!=null
                                && dataSnapshot.child("likesAuthors").child(mAuth.getCurrentUser().getUid()).child("like").getValue().equals("true")) {
                            holder.heart.setImageResource(R.drawable.blackheart);
                            poems.get(position).setLike(true);
                        } else {
                            holder.heart.setImageResource(R.drawable.heart);
                            poems.get(position).setLike(false);
                        }
                        holder.likes.setText("" + dataSnapshot.child("likes").getValue());

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        StorageReference mRef2 = FirebaseStorage.getInstance().getReference();
        mRef2.child("images/" + poems.get(position).getuId()).getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
            @Override
            public void onSuccess(StorageMetadata storageMetadata) {
                String path = storageMetadata.getDownloadUrl().toString();
                Picasso.with(context).load(path).resize(200,200).centerCrop().into(holder.avatar);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Picasso.with(context).load("https://firebasestorage.googleapis.com/v0/b/rhymebyrhyme.appspot.com/o/24649396.png?alt=media&token=374987c9-3582-4ee7-8861-5962af10cee0")
                        .resize(200, 200)
                        .centerCrop()
                        .into(holder.avatar);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PoemProfileActivity.class);
                intent.putExtra("poem", poems.get(position));
                context.startActivity(intent);
            }
        });
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
                ImageView avatar;
                TextView authorName;

                public MyListViewHolder(View itemView) {
                    super(itemView);
                    avatar = (ImageView) itemView.findViewById(R.id.authoravatar);
                    authorName = (TextView) itemView.findViewById(R.id.author_name);
                    authorName.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf"));
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

