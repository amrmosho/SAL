package com.pcland15.ismail.sal;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pcland15.ismail.sal.libs.cat_list;
import com.pcland15.ismail.sal.libs.dbOperations;
import com.pcland15.ismail.sal.libs.xmlDataModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class categories extends AppCompatActivity {
    HashMap<String, HashMap<String, String>> dbdata;
    listArrayAdapte adapter;
    ImageView categoriesImage;
    TextView categoriesTtile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        table = getIntent().getStringExtra("status");


        steData();


        getData();
        serach();
    }


    String table = "";

    void steData() {
        categoriesImage = (ImageView) findViewById(R.id.categories_image);
        categoriesTtile = (TextView) findViewById(R.id.categories_title);


        if (table.equalsIgnoreCase(xmlDataModel.signCatTable)) {


            categoriesTtile.setText(this.getString(R.string.sign_categories));


            categoriesImage.setImageDrawable(this.getDrawable(R.drawable.sign_categries));


        } else if (table.equalsIgnoreCase(xmlDataModel.booksCatTable)) {
            categoriesTtile.setText(this.getString(R.string.books_categories));
            categoriesImage.setImageDrawable(this.getDrawable(R.drawable.books_cats));
        } else {
            categoriesTtile.setText(this.getString(R.string.quiz_categories));
            categoriesImage.setImageDrawable(this.getDrawable(R.drawable.quiz_bg));
        }

    }


    void serach() {
        final EditText editsearch = (EditText) findViewById(R.id.quiz_catserchin);

        editsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());



                adapter.getFilter().filter(text);



                Toast.makeText(categories.this, text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            }
        });
    }

    void getData() {

        dbOperations db = new dbOperations(table, "get_data");

        HashMap<String, HashMap<String, String>> data = db.commit();


        GridView l = (GridView) findViewById(R.id.quiz_cat_List);


        final List<cat_list> mydata = new ArrayList<>();

        for (String k : data.keySet()) {


            if (!k.equalsIgnoreCase("log")) {


                cat_list c = new cat_list();


                c.setTitle(data.get(k).get("title"));
                c.setImage(data.get(k).get("image"));
                c.setID(data.get(k).get("id"));
                mydata.add(c);


            }

        }


        adapter = new listArrayAdapte(this, 0, mydata);


        l.setAdapter(adapter);


        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                viewItemsList(mydata.get(position).getID());


            }
        });
    }


    void viewItemsList(String id) {

        Intent t = null;


        if (table.equalsIgnoreCase(xmlDataModel.signCatTable)) {
            t = new Intent(this, sign_items_list.class);

        } else if (table.equalsIgnoreCase(xmlDataModel.booksCatTable)) {
            t = new Intent(this, sign_items_list.class);


        } else {

            t = new Intent(this, quiz_itme.class);
        }


        t.putExtra("status", table);
        t.putExtra("id", id);
        startActivity(t);


    }


    public void goBack(View view) {

        this.finish();
    }
}
