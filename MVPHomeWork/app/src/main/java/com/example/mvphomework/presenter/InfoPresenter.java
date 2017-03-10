package com.example.mvphomework.presenter;

import android.content.Intent;

import com.example.mvphomework.InfoInterface;
import com.example.mvphomework.ModelUser;


public class InfoPresenter {
    InfoInterface infoInterface;
    ModelUser modelUser;

    public InfoPresenter(InfoInterface infoInterface) {
        this.infoInterface = infoInterface;
    }


    public void saveNewInfo(String name, String surname, String email, int id){
        modelUser = ModelUser.getInstance();
        modelUser.newInfo(name, surname, email, id);

    }

    public void setNewData(Intent intent) {
        infoInterface.setData(intent.getStringExtra("name"), intent.getStringExtra("surname"), intent.getStringExtra("email"));
    }

    public void setNewData() {
        infoInterface.setDialogData();
    }


}
