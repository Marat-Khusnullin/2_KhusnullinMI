package com.example.mvvmhomework;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mvvmhomework.databinding.ActivityInfoBinding;

public class InfoActivity extends AppCompatActivity {
    ActivityInfoBinding binding;
    InfoViewModel infoViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_info);
        super.onCreate(savedInstanceState);

        infoViewModel = new InfoViewModel();
        binding.setViewModel(infoViewModel);
        infoViewModel.setData(getIntent().getIntExtra("position", 0));

    }


}
