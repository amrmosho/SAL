package com.pcland15.ismail.sal;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.pcland15.ismail.sal.libs.dbOperations;

import java.util.ArrayList;
import java.util.HashMap;

public class user_reg extends AppCompatActivity {

    TextView email;
    TextView title;
    TextView password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reg);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
     /*   dbOperations db =new dbOperations("studentes" , "get_row");
        db.commit();*/

        email = (TextView) findViewById(R.id.uersreg_email_input);
        password = (TextView) findViewById(R.id.uersreg_password_input);
        title = (TextView) findViewById(R.id.uersreg_title_input);


    }

    public void userRegSend(View view) {
        email.getText();
        dbOperations o = new dbOperations("studentes", "insert");
        o.addData.put("email", email.getText().toString());
        o.addData.put("title", title.getText().toString());
        o.addData.put("password", password.getText().toString());
        o.commit();
    }
}
