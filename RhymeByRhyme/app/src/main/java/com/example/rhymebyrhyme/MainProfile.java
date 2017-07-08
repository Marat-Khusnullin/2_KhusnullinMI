package com.example.rhymebyrhyme;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.rhymebyrhyme.database.Dao;
import com.example.rhymebyrhyme.model.User;


public class MainProfile extends AppCompatActivity {
    TextView name;
    TextView about;
    SharedPreferences sPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_profile);
        name = (TextView) findViewById(R.id.name);
        about = (TextView) findViewById(R.id.about);
        Dao dao = new Dao();
        User user = dao.getUserByEmail(getEmail());
        name.setText(user.getName());
    }



    private String getEmail(){
        sPref = getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString(MainActivity.CURRENT_EMAIL, "");
        return savedText;
    }
}
