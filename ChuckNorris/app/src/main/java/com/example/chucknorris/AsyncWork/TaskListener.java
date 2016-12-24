package com.example.chucknorris.AsyncWork;

/**
 * Created by Марат on 23.12.2016.
 */

public interface TaskListener {
        void onTaskFinish(String joke);
        void onTaskStarted();
    }

