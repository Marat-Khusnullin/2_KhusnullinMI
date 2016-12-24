package com.example.chucknorris.AsyncWork;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

public class FragmentWithAsync extends Fragment {
        TaskListener mTaskListener;
        AsyncClass async;

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            mTaskListener = (TaskListener) context;
            if(async!=null) {
                async.newListener(mTaskListener);
            }
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            mTaskListener = (TaskListener) activity;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

    @Override
    public void onDetach() {
        super.onDetach();
        mTaskListener = null;
    }

    public void startAsync() {
            async = new AsyncClass(mTaskListener);
            async.execute();
        }





    }

