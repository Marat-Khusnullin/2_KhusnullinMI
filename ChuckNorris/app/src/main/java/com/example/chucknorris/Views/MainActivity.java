package com.example.chucknorris.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.chucknorris.AsyncWork.FragmentWithAsync;
import com.example.chucknorris.AsyncWork.TaskListener;
import com.example.chucknorris.R;

public class MainActivity extends AppCompatActivity  implements TaskListener {

    public static final String FRAGMENT_TAG = "fragment_tag";
    public static final String FRAGMENT_TAG2 = "fragment_tag2";
    FragmentWithAsync fragmentWithAsync;
    ProgressBar progressBar;
    DataFragment dataFragment;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.button);
        tv = (TextView) findViewById(R.id.tv);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        fragmentWithAsync = getFragmentByTag(FRAGMENT_TAG);

        dataFragment = getDataFragment();
        if(savedInstanceState!=null){
          // dataFragment.setText(savedInstanceState.getString("joke"));
            tv.setText(savedInstanceState.getString("joke"));
            if(savedInstanceState.getInt("progress") == View.VISIBLE)
                progressBar.setVisibility(ProgressBar.VISIBLE);


        }

       // dataFragment.setText(data);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentWithAsync.startAsync();
            }
        });
    }


    private FragmentWithAsync getFragmentByTag(String tag) {
        FragmentWithAsync fragment = (FragmentWithAsync) getFragmentManager().
                findFragmentByTag(tag);
        if (fragment == null) {
            fragment = new FragmentWithAsync();
            getFragmentManager().beginTransaction().
                    add(fragment, tag).commit();
        }
        //fragment.setContext(this);
        return fragment;
    }

    private DataFragment getDataFragment() {
        DataFragment fragment = (DataFragment) getFragmentManager().findFragmentByTag(FRAGMENT_TAG2);

        if(fragment == null) {
            fragment = new DataFragment();
        }
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, FRAGMENT_TAG2).commit();
        return fragment;
    }

    @Override
    public void onTaskFinish(String joke) {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
        tv.setText(joke);
         //dataFragment.setText(joke);
    }

    @Override
    public void onTaskStarted() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("joke",  tv.getText().toString());
        outState.putInt("progress", progressBar.getVisibility());

    }






}
