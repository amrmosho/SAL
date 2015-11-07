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
    dbOperations o = null;
    ui u = new ui(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newitem);


        status = getIntent().getStringExtra("status");
        setData();
        o = new dbOperations(table, "insert");

        //<editor-fold desc="getTools">
        desc = (TextView) findViewById(R.id.newitem_desc_input);
        title = (TextView) findViewById(R.id.newitem_name_input);
        //</editor-fold>

        //<editor-fold  desc="Spinner">
        ui u = new ui(this);
        Spinner newuser_sp_cat = (Spinner) findViewById(R.id.newitem_cats);
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

        table = status;


        if (status.equalsIgnoreCase(xmlDataModel.quiz_questions)) {
            titleTxt += " " + this.getString(R.string.panel_quiz);
            catTable = xmlDataModel.quizCatTable;
        } else if (status.equalsIgnoreCase(xmlDataModel.signTable)) {
            titleTxt += " " + this.getString(R.string.sign);
            catTable = xmlDataModel.signCatTable;
        } else if (status.equalsIgnoreCase(xmlDataModel.quizAnswersTable)) {
            titleTxt += " " + this.getString(R.string.sign);
            catTable = xmlDataModel.quiz_questions;
        } else if (status.equalsIgnoreCase(xmlDataModel.booksTable)) {
            titleTxt += " " + this.getString(R.string.books);
            catTable = xmlDataModel.booksCatTable;
        } else {
            titleTxt += " " + this.getString(R.string.add_user);
            catTable = xmlDataModel.userCatTable;
        }

    }


    //<editor-fold desc="image_bt">
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        o.addImageData.put("image", u.getImage(resultCode, (ImageView) findViewById(R.id.newitem_ButtonImageView), data));

    }

    public void select_itemimage(View view) {
        u.getImage(this);
    }


    public void select_file(View view) {



    }

    //</editor-fold>


    public void goBack(View view) {
        this.finish();
    }




    public void sendItem(View view) {

        if (title.getText().toString().equalsIgnoreCase("")) {
            title.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            Toast t = Toast.makeText(this, this.getString(R.string.new_user_empty_msg), Toast.LENGTH_LONG);
            t.show();
            return;

        } else {
            title.setTextColor(getResources().getColor(R.color.textColorPrimary));
        }

        o.addData.put("title", title.getText().toString());
        o.addData.put("des", desc.getText().toString());

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
