package com.example.mvvmhomework;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mvvmhomework.databinding.UserItemBinding;

import java.util.LinkedList;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    LinkedList<User> users;


    public UserAdapter() {
        users = ModelUser.getInstance().getUsers();

    }

    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        UserItemBinding userItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.user_item,
                        parent, false);
        return new UserViewHolder(userItemBinding);

    }


    @Override
    public void onBindViewHolder(final UserViewHolder holder, final int position) {
        User currentUser = users.get(position);
        final User user1 = currentUser;
        holder.userItemBinding.setUser(currentUser);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.userItemBinding.getMainViewModel() == null)
                    holder.userItemBinding.setMainViewModel(new MainViewModel(view.getContext()));
                holder.userItemBinding.getMainViewModel().click(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public class UserViewHolder extends RecyclerView.ViewHolder {

        TextView user;
        UserItemBinding userItemBinding;


        public UserViewHolder(UserItemBinding userItemBinding) {
            super(userItemBinding.userItem);
            this.userItemBinding = userItemBinding;

            user = (TextView) itemView.findViewById(R.id.user);

        }


    }
}
