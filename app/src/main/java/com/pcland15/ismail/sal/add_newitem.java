package com.pcland15.ismail.sal;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pcland15.ismail.sal.libs.dbOperations;
import com.pcland15.ismail.sal.libs.simpleList;
import com.pcland15.ismail.sal.libs.ui;
import com.pcland15.ismail.sal.libs.xmlDataModel;

import java.util.HashMap;
import java.util.List;

public class add_newitem extends AppCompatActivity {

    TextView title;
    TextView desc;
    String table = "";
    String catTable = "";
    String titleTxt = "";
    String status;
    String g_id;
    LinearLayout Img_Layout;
    LinearLayout File_Layout;
    dbOperations o = null;
    ui u = new ui(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newitem);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }
        status = getIntent().getStringExtra("status");


        //<editor-fold desc="getTools">
        desc = (TextView) findViewById(R.id.newitem_desc_input);
        Img_Layout = (LinearLayout) findViewById(R.id.imageareaLayout);
        File_Layout = (LinearLayout) findViewById(R.id.fileLayout);
        title = (TextView) findViewById(R.id.newitem_name_input);
        //</editor-fold>

        setData();
        o = new dbOperations(table, "insert");

        //<editor-fold  desc="Spinner">
        ui u = new ui(this);
        Spinner newuser_sp_cat = (Spinner) findViewById(R.id.newitem_cats);

        List<simpleList> sdata2 = null;
        if (status.equalsIgnoreCase(xmlDataModel.quizAnswersTable)) {
            sdata2 = u.fillSpinner(newuser_sp_cat, catTable, "question");


        } else {

            sdata2 = u.fillSpinner(newuser_sp_cat, catTable);

        }


        final List<simpleList> sdata = sdata2;

        g_id = sdata.get(0).getValue();
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

        table = status;
        File_Layout.setVisibility(View.VISIBLE);
        Img_Layout.setVisibility(View.VISIBLE);
        if (status.equalsIgnoreCase(xmlDataModel.quiz_questions)) {
            titleTxt += " " + this.getString(R.string.panel_quiz);
            catTable = xmlDataModel.quizCatTable;
            title.setHint(R.string.question);
            File_Layout.setVisibility(View.GONE);

        } else if (status.equalsIgnoreCase(xmlDataModel.signTable)) {
            titleTxt += " " + this.getString(R.string.sign);
            catTable = xmlDataModel.signCatTable;
        } else if (status.equalsIgnoreCase(xmlDataModel.quizAnswersTable)) {
            titleTxt += " " + this.getString(R.string.sign);
            File_Layout.setVisibility(View.GONE);
            Img_Layout.setVisibility(View.GONE);
            title.setHint(R.string.answer);

            catTable = xmlDataModel.quiz_questions;
        } else if (status.equalsIgnoreCase(xmlDataModel.booksTable)) {
            titleTxt += " " + this.getString(R.string.books);
            catTable = xmlDataModel.booksCatTable;
            File_Layout.setVisibility(View.GONE);
        } else {
            titleTxt += " " + this.getString(R.string.add_user);
            catTable = xmlDataModel.userCatTable;
        }

    }


    //<editor-fold desc="image_bt">
    String fileStatus = "image";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String path = "";
        if (fileStatus == "image") {
            path = u.getImage(resultCode, (ImageView) findViewById(R.id.newitem_ButtonImageView), data);
        } else {
            path = u.getImage(resultCode, (TextView) findViewById(R.id.filePath), data);

        }
        o.addImageData.put("image", path);

    }

    public void select_itemimage(View view) {


        fileStatus = "image";

        u.getImage(this);
    }


    public void select_file(View view) {

        if (status.equalsIgnoreCase(xmlDataModel.booksTable)) {
            fileStatus = "file";

        } else {
            fileStatus = "video";

        }
        u.getImage(this);

    }

    //</editor-fold>


    public void goBack(View view) {
        this.finish();
    }


    public void sendItem(View view) {


    }

    public void mySendItem(View view) {

        if (title.getText().toString().equalsIgnoreCase("")) {
            title.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            Toast t = Toast.makeText(this, this.getString(R.string.new_user_empty_msg), Toast.LENGTH_LONG);
            t.show();
            return;

        } else {
            title.setTextColor(getResources().getColor(R.color.textColorPrimary));
        }


        if (status.equalsIgnoreCase(xmlDataModel.quizAnswersTable)) {
            o.addData.put("q_id", g_id);

            o.addData.put("description", title.getText().toString());
        } else if (status.equalsIgnoreCase(xmlDataModel.quiz_questions)) {
            o.addData.put("question", title.getText().toString());
            o.addData.put("cat_id", g_id);

        } else {
            o.addData.put("des", desc.getText().toString());
            o.addData.put("name", title.getText().toString());
            o.addData.put("cat_id", g_id);


        }


        HashMap<String, HashMap<String, String>> a = o.commit();
        if (a.get("log").get("opstauts").equalsIgnoreCase("true")) {
            Toast t = Toast.makeText(this, "Done", Toast.LENGTH_LONG);
            t.show();
            title.setText("");
            desc.setText("");
        } else {
            Toast t = Toast.makeText(this, "Error", Toast.LENGTH_LONG);
            t.show();
        }


    }
}
