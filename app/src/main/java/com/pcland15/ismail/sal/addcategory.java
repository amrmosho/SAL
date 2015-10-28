package com.pcland15.ismail.sal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class addcategory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcategory);
    }

    public void goback(View view) {
        this.finish();
    }
}
