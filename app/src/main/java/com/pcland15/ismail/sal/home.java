package com.pcland15.ismail.sal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void gotoSigns(View view) {


        Intent t = new Intent(this, sign_categories.class);
        startActivity(t);


    }


    public void gotoMySigns(View view) {
        Intent t = new Intent(this, quiz_cates.class);
        startActivity(t);
    }

    public void gotoQuizs(View view) {
        Intent t = new Intent(this, quiz_cates.class);
        startActivity(t);
    }

    public void gotoAdmin(View view) {
        Intent t = new Intent(this, sign_categories.class);
        startActivity(t);
    }

    public void goBack(View view) {
        this.finish();
    }
}
