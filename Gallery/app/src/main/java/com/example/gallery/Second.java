package com.example.gallery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class Second extends AppCompatActivity {
private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        image = (ImageView) findViewById(R.id.image);
        Intent intent = getIntent();
        image.setImageResource(intent.getIntExtra("Image",0));
    }



}
