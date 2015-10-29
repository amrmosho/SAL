package com.pcland15.ismail.sal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class add_newsign extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_newsign);
    }


    public void goBack(View view) {
        this.finish();
    }

    public void userRegSend(View view) {
    }
}
