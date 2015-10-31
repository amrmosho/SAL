package com.pcland15.ismail.sal;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class panel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

    }



    public void goto_sign(View view) {
        Intent t = new Intent(this, new_user.class);
        t.putExtra("status","sign");
        startActivity(t);
    }

    public void goto_quiz_Q(View view) {
        Intent t = new Intent(this, new_user.class);
        t.putExtra("status","quiz_q");
        startActivity(t);
    }

    public void goto_quiz_A(View view) {
        Intent t = new Intent(this, new_user.class);
        t.putExtra("status","quiz_a");
        startActivity(t);
    }


    public void goto_user(View view) {
        Intent t = new Intent(this, new_user.class);
        t.putExtra("status","user");
        startActivity(t);
    }

    public void goto_user_Cats(View view) {
        Intent t = new Intent(this, user_cat.class);
        t.putExtra("status","user");
        startActivity(t);
    }

    public void goto_mysignCats(View view) {

        Intent t = new Intent(this, user_cat.class);
        t.putExtra("status","sign");
        startActivity(t);
    }

    public void goto_quiz_cat(View view) {
        Intent t = new Intent(this, user_cat.class);
        t.putExtra("status","quiz");
        startActivity(t);
    }
    public void goback(View view) {

        this.finish();
    }
}
