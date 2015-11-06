package com.pcland15.ismail.sal;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.pcland15.ismail.sal.libs.xmlDataModel;

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
        Intent t = new Intent(this, add_item.class);
        t.putExtra("status", xmlDataModel.signTable);
        startActivity(t);
    }

    public void goto_quiz_Q(View view) {
        Intent t = new Intent(this, add_item.class);
        t.putExtra("status", xmlDataModel.quiz_questions);
        startActivity(t);
    }

    public void goto_quiz_A(View view) {
        Intent t = new Intent(this, add_item.class);
        t.putExtra("status", xmlDataModel.quizAnswersTable);
        startActivity(t);
    }


    public void goto_user(View view) {
        Intent t = new Intent(this, add_item.class);
        t.putExtra("status", xmlDataModel.userTable);
        startActivity(t);
    }



    public void goto_book(View view) {

        Intent t = new Intent(this, add_item.class);
        t.putExtra("status", xmlDataModel.booksTable);
        startActivity(t);

    }



    public void goto_user_Cats(View view) {
        Intent t = new Intent(this, add_category.class);
        t.putExtra("status", xmlDataModel.userCatTable);
        startActivity(t);
    }

    public void goto_mysignCats(View view) {

        Intent t = new Intent(this, add_category.class);
        t.putExtra("status", xmlDataModel.signCatTable);
        startActivity(t);
    }

    public void goto_quiz_cat(View view) {
        Intent t = new Intent(this, add_category.class);
        t.putExtra("status",xmlDataModel.quizCatTable);
        startActivity(t);
    }



    public void goto_booksCats(View view) {

        Intent t = new Intent(this, add_category.class);
        t.putExtra("status",xmlDataModel.booksCatTable);
        startActivity(t);
    }


    public void goback(View view) {

        this.finish();
    }


}
