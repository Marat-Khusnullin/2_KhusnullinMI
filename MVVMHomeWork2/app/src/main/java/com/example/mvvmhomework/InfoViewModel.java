package com.example.mvvmhomework;

import android.databinding.ObservableField;
import android.widget.EditText;

import java.util.LinkedList;



public class InfoViewModel {
    LinkedList<User> users;
    public ObservableField<String> name;
    public ObservableField<String> surname;
    public ObservableField<String> email;
    int position;

    public InfoViewModel() {
        users = ModelUser.getInstance().getUsers();
        name = new ObservableField<>("");
        surname = new ObservableField<>();
        email = new ObservableField<>("");

    }

    public void setData(int position) {
        this.position = position;
        name.set(users.get(position).getName());
        surname.set(users.get(position).getSurname());
        email.set(users.get(position).getEmail());
    }

    public void click() {
        ModelUser.getInstance().newInfo("" + name.get(), "" +surname.get(),""+ email.get(), position);
    }

}
