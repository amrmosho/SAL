package com.pcland15.ismail.sal;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pcland15.ismail.sal.libs.dbOperations;

import java.util.HashMap;

public class new_user extends AppCompatActivity {
    TextView email;
    TextView title;
    TextView password;
    TextView repassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        repassword = (TextView) findViewById(R.id.new_uesr_repassword);
        email = (TextView) findViewById(R.id.new_uesr_email);
        password = (TextView) findViewById(R.id.new_uesr_password);
        title = (TextView) findViewById(R.id.new_uesr_name);
    }


    public void userRegSend(View view) {


        dbOperations o = new dbOperations("studentes", "insert");


        if (email.getText().toString().equalsIgnoreCase("")){
            email.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            Toast t = Toast.makeText(this, this.getString(R.string.new_user_empty_msg), Toast.LENGTH_LONG);
            t.show();
            return;

        }else{
            email.setTextColor(getResources().getColor(R.color.textColorPrimary));

        }


        if (title.getText().toString().equalsIgnoreCase("")){
            title.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            Toast t = Toast.makeText(this,this.getString(R.string.new_user_empty_msg), Toast.LENGTH_LONG);
            t.show();
            return;

        }else{
            title.setTextColor(getResources().getColor(R.color.textColorPrimary));
        }



        if (password.getText().toString().equalsIgnoreCase("")){
            password.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            Toast t = Toast.makeText(this,  this.getString(R.string.new_user_empty_msg), Toast.LENGTH_LONG);
            t.show();
            return;
        }else{
            password.setTextColor(getResources().getColor(R.color.textColorPrimary));
        }


        if (password.getText().toString().equals(repassword.getText().toString())){
            Toast t = Toast.makeText(this, this.getString(R.string.new_user_password_not_msg), Toast.LENGTH_LONG);
            t.show();
            return;

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

    public void goBack(View view) {
        this.finish();
    }
}
