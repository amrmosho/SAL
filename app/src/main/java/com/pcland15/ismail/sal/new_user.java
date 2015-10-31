package com.pcland15.ismail.sal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pcland15.ismail.sal.libs.*;
import com.pcland15.ismail.sal.libs.dbOperations;
import com.pcland15.ismail.sal.libs.simpleList;

import java.util.HashMap;
import java.util.List;

public class new_user extends AppCompatActivity {
    TextView email;
    TextView title;
    TextView password;
    TextView repassword;
    String table = "";
    String catTable = "";
    String titleTxt = "";
    String status;
    String g_id;
    dbOperations o = null;
    ui u = new ui(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        status = getIntent().getStringExtra("status");

        //<editor-fold desc="getTools">
        repassword = (TextView) findViewById(R.id.new_uesr_repassword);
        email = (TextView) findViewById(R.id.new_uesr_email);
        password = (TextView) findViewById(R.id.new_uesr_password);
        title = (TextView) findViewById(R.id.new_uesr_name);
        //</editor-fold>

        setData();

        o = new dbOperations(table, "insert");

        //<editor-fold  desc="Spinner">
        ui u = new ui(this);
        Spinner newuser_sp_cat = (Spinner) findViewById(R.id.newuser_sp_cat);
        final List<simpleList> sdata = u.fillSpinner(newuser_sp_cat, catTable);
        newuser_sp_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                g_id = sdata.get(position).getValue();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //</editor-fold>

    }


    void setData() {
        titleTxt = this.getString(R.string.newcat_title);
        if (status.equalsIgnoreCase("quiz")) {
            titleTxt += " " + this.getString(R.string.panel_quiz);
            catTable = xmlDataModel.quizCatTable;
            table = xmlDataModel.quiz_questions;
        } else if (status.equalsIgnoreCase("sign")) {
            titleTxt += " " + this.getString(R.string.sign);
            catTable = xmlDataModel.signCatTable;
            table = xmlDataModel.signTable;

        } else if (status.equalsIgnoreCase("quiz_q")) {
            titleTxt += " " + this.getString(R.string.sign);
            catTable = xmlDataModel.quizCatTable;
            table = xmlDataModel.quiz_questions;


        } else if (status.equalsIgnoreCase("quiz_a")) {
            titleTxt += " " + this.getString(R.string.sign);
            catTable = xmlDataModel.quiz_questions;
            table = xmlDataModel.quizAnswersTable;





        } else {
            titleTxt += " " + this.getString(R.string.add_user);
            catTable = xmlDataModel.userCatTable;
            table = xmlDataModel.userTable;
        }

    }




    //<editor-fold desc="image_bt">
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        o.addImageData.put("image", u.getImage(resultCode, (ImageView) findViewById(R.id.new_userButtonImageView), data));

    }

    public void select_image(View view) {
        u.getImage(this);
    }
    //</editor-fold>

    public void goBack(View view) {
        this.finish();
    }

    public void goList(View view) {
        Intent t = new Intent(this, act_list.class);
        t.putExtra("status", this.status);
        startActivity(t);
    }

    public void naw_userRegSend(View view) {

        if (email.getText().toString().equalsIgnoreCase("")) {
            email.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            Toast t = Toast.makeText(this, this.getString(R.string.new_user_empty_msg), Toast.LENGTH_LONG);
            t.show();
            return;
        } else {
            email.setTextColor(getResources().getColor(R.color.textColorPrimary));
        }


        if (title.getText().toString().equalsIgnoreCase("")) {
            title.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            Toast t = Toast.makeText(this, this.getString(R.string.new_user_empty_msg), Toast.LENGTH_LONG);
            t.show();
            return;

        } else {
            title.setTextColor(getResources().getColor(R.color.textColorPrimary));
        }


        if (password.getText().toString().equalsIgnoreCase("")) {
            password.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            Toast t = Toast.makeText(this, this.getString(R.string.new_user_empty_msg), Toast.LENGTH_LONG);
            t.show();
            return;
        } else {
            password.setTextColor(getResources().getColor(R.color.textColorPrimary));
        }


        if (!password.getText().toString().equals(repassword.getText().toString())) {
            Toast t = Toast.makeText(this, this.getString(R.string.new_user_password_not_msg), Toast.LENGTH_LONG);
            t.show();
            return;

        }


        o.addData.put("email", email.getText().toString());
        o.addData.put("title", title.getText().toString());
        o.addData.put("password", password.getText().toString());

        HashMap<String, HashMap<String, String>> a = o.commit();
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
}
