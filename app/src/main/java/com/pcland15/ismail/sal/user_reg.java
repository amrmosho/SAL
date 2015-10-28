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

        /// create database connection
        dbOperations o = new dbOperations("studentes", "insert");



        /// check email valid
      if (email.getText().toString().equalsIgnoreCase("")){ // if email empty
          email.setBackgroundColor(getResources().getColor(R.color.colorAccent));
          Toast t = Toast.makeText(this, "Empty", Toast.LENGTH_LONG);
          t.show();
          return;

      }else{
          email.setTextColor(getResources().getColor(R.color.textColorPrimary));

      }


        /// check name valid
        if (title.getText().toString().equalsIgnoreCase("")){ // if empty
            title.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            Toast t = Toast.makeText(this, "Empty", Toast.LENGTH_LONG);
            t.show();
            return;

        }else{
            title.setTextColor(getResources().getColor(R.color.textColorPrimary));

        }



        /// add data to database
        o.addData.put("email", email.getText().toString());
        o.addData.put("title", title.getText().toString());
        o.addData.put("password", password.getText().toString());



        /// commit
        HashMap<String, HashMap<String, String>>a = o.commit();





        /// check db operation status
        if (a.get("log").get("opstauts").equalsIgnoreCase("true")) { // opsstatus = success

            Toast t = Toast.makeText(this, this.getString(R.string.back), Toast.LENGTH_LONG);
            t.show();
            email.setText("");
            title.setText("");
            password.setText("");

        } else { // failed return message

            Toast t = Toast.makeText(this, this.getString(R.string.back), Toast.LENGTH_LONG);
            t.show();

        }


    }
}
