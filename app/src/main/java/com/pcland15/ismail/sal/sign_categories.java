package com.pcland15.ismail.sal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pcland15.ismail.sal.libs.cat_list;
import com.pcland15.ismail.sal.libs.config;
import com.pcland15.ismail.sal.libs.dbOperations;
import com.pcland15.ismail.sal.libs.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class sign_categories extends AppCompatActivity {

    HashMap<String, HashMap<String, String>> dbdata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_categories);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    void getData() {

        dbOperations db = new dbOperations("sign_categries", "get_data");

        HashMap<String, HashMap<String, String>> data = db.commit();


        final List<cat_list> mydata = new ArrayList<>();

        for (String k : data.keySet()) {
            if (!k.equalsIgnoreCase("log")) {
                cat_list c = new cat_list();
                c.setTitle(data.get(k).get("title"));
                c.setImage(data.get(k).get("image"));
                c.setImage(data.get(k).get("image"));
                c.setID(data.get(k).get("id"));
                mydata.add(c);
            }

        }

        final Context o = this;

        GridView l = (GridView) findViewById(R.id.sign_cat_List);
        l.setAdapter( new listArrayAdapte(this, 0, mydata));
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewItemsList(mydata.get(position).getID());
            }
        });
    }

    void viewItemsList(String id) {

        Intent t = new Intent(this, sign_items_list.class);
        t.putExtra("id", id);
        startActivity(t);
    }


}
