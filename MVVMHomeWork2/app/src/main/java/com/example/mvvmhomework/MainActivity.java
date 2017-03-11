package com.example.mvvmhomework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;

import com.example.mvvmhomework.databinding.ActivityMainBinding;
public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    MainViewModel mainViewModel;
    UserAdapter userAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new MainViewModel(this);
        super.onCreate(savedInstanceState);
        userAdapter = new UserAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(userAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        userAdapter = new UserAdapter();
        binding.recyclerView.setAdapter(userAdapter);
    }
}
