package com.pcland15.ismail.sal;

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

        email=(TextView)findViewById(R.id.uersreg_email_input);
        password=(TextView)findViewById(R.id.uersreg_password_input);
        title=(TextView)findViewById(R.id.uersreg_title_input);





    }

    public void userRegSend(View view) {
        email.getText();


       dbOperations o=new dbOperations("usres","insert");
        o.addData.put("email",email.getText().toString());
        o.addData.put("title",title.getText().toString());
        o.addData.put("password",password.getText().toString());


o.commit();



    }
}
