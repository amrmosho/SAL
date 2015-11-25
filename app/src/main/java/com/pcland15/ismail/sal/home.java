package com.pcland15.ismail.sal;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.pcland15.ismail.sal.libs.xmlDataModel;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }




       // TextView  home_name_title= (TextView)findViewById(R.id.home_name_title);



      //  home_name_title.setText(dbOperations.userData.get("title"));
//
    }









    public void goBack(View view) {
        this.finish();
    }






    public void gotoSigns(View view) {


        Intent t = new Intent(this, categories.class);
        t.putExtra("status", xmlDataModel.signCatTable);
        startActivity(t);


    }

    public void gotoQuizs(View view) {
        Intent t = new Intent(this, categories.class);
        t.putExtra("status", xmlDataModel.quizCatTable);

        startActivity(t);
    }


    public void gotoBooks(View view) {

        Intent t = new Intent(this, categories.class);
        t.putExtra("status", xmlDataModel.booksCatTable);
        startActivity(t);

    }

    public void gotoUpdate(View view) {
        Intent t = new Intent(this, update.class);
        t.putExtra("status", "update");
        startActivity(t);
    }
}
