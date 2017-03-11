package com.example.mvvmhomework;


import android.content.Context;
import android.content.Intent;

public class MainViewModel {
Context context;

   public MainViewModel(Context context){
       this.context = context;
   }


 public void click (int position){
     Intent intent = new Intent(context, InfoActivity.class);
     intent.putExtra("position", position);
     context.startActivity(intent);
 }
}
