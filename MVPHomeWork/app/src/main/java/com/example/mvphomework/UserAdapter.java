package com.example.mvphomework;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;



public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    LinkedList<User> users;
    Context context;


    public UserAdapter(LinkedList<User> users, Context context) {
        this.users = users;
        this.context = context;

    }

    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        final User currentUser = users.get(position);
        final int positionIntent = position;
        holder.user.setText(currentUser.getName());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InfoActivity.class);
                intent.putExtra("name", currentUser.getName());
                intent.putExtra("surname", currentUser.getSurname());
                intent.putExtra("email", currentUser.getEmail());
                intent.putExtra("id", positionIntent);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public class UserViewHolder extends RecyclerView.ViewHolder {

        TextView user;
        View view;



        public UserViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            user = (TextView) itemView.findViewById(R.id.user);

        }


    }
}
