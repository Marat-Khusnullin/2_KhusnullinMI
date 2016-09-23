package com.example.clclt;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);

        textView.setOnClickListener(this);

        Button btn0 = (Button) findViewById(R.id.id0);
        btn0.setOnClickListener(this);
        Button btn1 = (Button) findViewById(R.id.id1);
        btn1.setOnClickListener(this);
        Button btn2 = (Button) findViewById(R.id.id2);
        btn2.setOnClickListener(this);
        Button btn3 = (Button) findViewById(R.id.id3);
        btn3.setOnClickListener(this);
        Button btn4 = (Button) findViewById(R.id.id4);
        btn4.setOnClickListener(this);
        Button btn5 = (Button) findViewById(R.id.id5);
        btn5.setOnClickListener(this);
        Button btn6 = (Button) findViewById(R.id.id6);
        btn6.setOnClickListener(this);
        Button btn7 = (Button) findViewById(R.id.id7);
        btn7.setOnClickListener(this);
        Button btn8 = (Button) findViewById(R.id.id8);
        btn8.setOnClickListener(this);
        Button btn9 = (Button) findViewById(R.id.id9);
        btn9.setOnClickListener(this);
        Button btnPlus = (Button) findViewById(R.id.idplus);
        btnPlus.setOnClickListener(this);
        Button btnMinus = (Button) findViewById(R.id.idminus);
        btnMinus.setOnClickListener(this);
        Button btnMult = (Button) findViewById(R.id.idmult);
        btnMult.setOnClickListener(this);
        Button btnDiv = (Button) findViewById(R.id.iddiv);
        btnDiv.setOnClickListener(this);
        Button btnResult = (Button) findViewById(R.id.idresult);
        btnResult.setOnClickListener(this);
        Button btnOpen = (Button) findViewById(R.id.idopen);
        btnOpen.setOnClickListener(this);
        Button btnClose = (Button) findViewById(R.id.idclose);
        btnClose.setOnClickListener(this);
        Button btnClear = (Button) findViewById(R.id.idclear);
        btnClear.setOnClickListener(this);
        Button btnDelete = (Button) findViewById(R.id.iddelete);
        btnDelete.setOnClickListener(this);
        Button btnPoint = (Button) findViewById(R.id.idpoint);
        btnPoint.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id0:
                textView.setText(textView.getText().toString() + "0");
                break;
            case R.id.id1:
                textView.setText(textView.getText() + "1");
                break;
            case R.id.id2:
                textView.setText(textView.getText() + "2");
                break;
            case R.id.id3:
                textView.setText(textView.getText() + "3");
                break;
            case R.id.id4:
                textView.setText(textView.getText() + "4");
                break;
            case R.id.id5:
                textView.setText(textView.getText() + "5");
                break;
            case R.id.id6:
                textView.setText(textView.getText() + "6");
                break;
            case R.id.id7:
                textView.setText(textView.getText() + "7");
                break;
            case R.id.id8:
                textView.setText(textView.getText() + "8");
                break;
            case R.id.id9:
                textView.setText(textView.getText() + "9");
                break;
            case R.id.idplus:
                textView.setText(textView.getText() + "+");
                break;
            case R.id.idminus:
                textView.setText(textView.getText() + "-");
                break;
            case R.id.idmult:
                textView.setText(textView.getText() + "x");
                break;
            case R.id.iddiv:
                textView.setText(textView.getText() + "/");
                break;
            case R.id.idresult:
                Poland a = new Poland();
                String qw = "";

                try {

                    qw += a.eval(textView.getText().toString());
                    if (!qw.equals("Infinity")) {
                        textView.setText(qw);
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Не, на 0 делить нельзя", Toast.LENGTH_SHORT);
                        toast.show();
                        textView.setText("");
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Некорректный ввод", Toast.LENGTH_SHORT).show();
                }
                break;


            case R.id.iddelete:
                if (textView.getText().toString().length()!=0)
                    textView.setText(textView.getText().toString().substring(0, textView.getText().length() - 1));
                break;
            case R.id.idopen:
                textView.setText(textView.getText() + "(");
                break;
            case R.id.idclose:
                textView.setText(textView.getText() + ")");
                break;
            case R.id.idclear:
                textView.setText("");
                break;
            case R.id.idpoint:
                textView.setText(textView.getText()+".");
        }
    }


}
