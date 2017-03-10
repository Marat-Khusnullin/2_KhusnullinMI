package com.example.mvphomework;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mvphomework.presenter.InfoPresenter;

public class InfoActivity extends AppCompatActivity implements InfoInterface {
    TextView name;
    TextView surname;
    TextView email;
    Intent intent;
    Dialog dialog;
    Button changeButton;
    Button dialogConfirmButton;
    InfoPresenter infoPresenter;

    EditText newName;
    EditText newSurname;
    EditText newEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        dialog = new Dialog(this);
        dialog.setTitle("Новое значение");
        dialog.setContentView(R.layout.dialog_view);

        infoPresenter = new InfoPresenter(this);

        name = (TextView) findViewById(R.id.name);
        surname = (TextView) findViewById(R.id.surname);
        email = (TextView) findViewById(R.id.email);
        changeButton = (Button) findViewById(R.id.changeButton);

        newName = (EditText) dialog.findViewById(R.id.newName);
        newSurname = (EditText) dialog.findViewById(R.id.newSurname);
        newEmail = (EditText) dialog.findViewById(R.id.newEmail);

        intent = getIntent();
        infoPresenter.setNewData(intent);


        dialogConfirmButton = (Button) dialog.findViewById(R.id.button);
        dialogConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogConfirmButtonClick();
            }
        });

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeClick();
            }
        });


    }


    public void setData(String name, String surname, String email) {
        this.name.setText(name);
        this.surname.setText(surname);
        this.email.setText(email);
    }

    public void changeClick() {
        newName.setText(name.getText());
        newSurname.setText(surname.getText());
        newEmail.setText(email.getText());
        dialog.show();
    }

    public void dialogConfirmButtonClick() {
        infoPresenter.saveNewInfo(newName.getText().toString(), newSurname.getText().toString(), newEmail.getText().toString(), intent.getIntExtra("id", 0));
        infoPresenter.setNewData();
        dialog.cancel();

    }

    public void setDialogData() {
        name.setText(newName.getText());
        surname.setText(newSurname.getText());
        email.setText(newEmail.getText());
    }

}
