package com.example.chucknorris.Views;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chucknorris.R;

/**
 * Created by Марат on 23.12.2016.
 */

public class DataFragment extends Fragment {
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_item, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        textView = (TextView)  view.findViewById(R.id.text);
        //Bundle bundle = getArguments();
        //String text = bundle.getString("text");
        //textView.setText(text);
    }

    public void setText(String text) {

        textView.setText(text);
    }

    public String getText(){
        return textView.getText().toString();
    }



}
