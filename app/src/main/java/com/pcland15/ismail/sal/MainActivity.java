package com.pcland15.ismail.sal;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.pcland15.ismail.sal.libs.*;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.pcland15.ismail.sal.libs.dbOperations;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    LinearLayout home_bar_layout;
    RelativeLayout login_layout;
    LinearLayout home_txt_layout;


    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }





        login_layout = (RelativeLayout) findViewById(R.id.login_layout);
        home_bar_layout = (LinearLayout) findViewById(R.id.home_bar_layout);
        home_txt_layout = (LinearLayout) findViewById(R.id.home_txt_layout);


        email = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);




        email.setText("empcland@gmail.com");
        password.setText("3mrmosho");



    }




    public void login(View view) {
        dbOperations db = new dbOperations(xmlDataModel.userTable, "login");

        db.addData.put("email", email.getText().toString());
        db.addData.put("password", password.getText().toString());

        HashMap<String, HashMap<String, String>> userdata = db.commit();

        if (userdata.size() > 1) {
            dbOperations.userData = userdata.get("0");
            Intent t = new Intent(this, home.class);
            startActivity(t);

        } else {

            Toast.makeText(this, this.getString(R.string.msg_errorlogin), Toast.LENGTH_SHORT).show();

        }


    }





    public void goto_new_user(View view) {
        Intent t = new Intent(this, add_user.class);
        t.putExtra("status", "user");
        startActivity(t);
    }

    public void go_to_about(View view) {
        Intent t = new Intent(this, about.class);
        startActivity(t);
    }






    public void hide_login(View view) {

        home_txt_layout.setVisibility(View.VISIBLE);
        home_bar_layout.setVisibility(View.VISIBLE);
        login_layout.setVisibility(View.GONE);
    }

    public void show_login(View view) {

        home_txt_layout.setVisibility(View.GONE);
        home_bar_layout.setVisibility(View.GONE);
        login_layout.setVisibility(View.VISIBLE);

    }



}
