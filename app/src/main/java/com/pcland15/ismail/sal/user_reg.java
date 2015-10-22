package com.pcland15.ismail.sal;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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


        email = (TextView) findViewById(R.id.uersreg_email_input);
        password = (TextView) findViewById(R.id.uersreg_password_input);
        title = (TextView) findViewById(R.id.uersreg_title_input);


    }

    public void userRegSend(View view) {


        dbOperations o = new dbOperations("studentes", "insert");




      if (email.getText().toString().equalsIgnoreCase("")){
          email.setBackgroundColor(getResources().getColor(R.color.colorAccent));
          Toast t = Toast.makeText(this, "Empty", Toast.LENGTH_LONG);
          t.show();
          return;

      }else{
          email.setTextColor(getResources().getColor(R.color.textColorPrimary));

      }



        if (title.getText().toString().equalsIgnoreCase("")){
            title.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            Toast t = Toast.makeText(this, "Empty", Toast.LENGTH_LONG);
            t.show();
            return;

        }else{
            title.setTextColor(getResources().getColor(R.color.textColorPrimary));

        }




        o.addData.put("email", email.getText().toString());
        o.addData.put("title", title.getText().toString());
        o.addData.put("password", password.getText().toString());




        HashMap<String, HashMap<String, String>>a = o.commit();
        if (a.get("log").get("opstauts").equalsIgnoreCase("true")) {

            Toast t = Toast.makeText(this, "Done", Toast.LENGTH_LONG);
            t.show();
            email.setText("");
            title.setText("");
            password.setText("");

        } else {

            Toast t = Toast.makeText(this, "Error", Toast.LENGTH_LONG);
            t.show();

        }


    }
}
