package com.pcland15.ismail.sal;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.pcland15.ismail.sal.libs.cat_list;
import com.pcland15.ismail.sal.libs.dbOperations;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.ConsoleHandler;

public class sign_items_list extends AppCompatActivity {
    String catID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_items_list);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }
        catID = getIntent().getStringExtra("id");

        getData();

    }


    void getData() {

        dbOperations db = new dbOperations("sign", "get_data");
        db.where = "cat_id=" + this.catID;
        HashMap<String, HashMap<String, String>> data = db.commit();


        final List<cat_list> mydata = new ArrayList<>();

        for (String k : data.keySet()) {
            if (!k.equalsIgnoreCase("log")) {
                cat_list c = new cat_list();
                c.setTitle(data.get(k).get("name"));
                c.setImage(data.get(k).get("image"));
                c.setID(data.get(k).get("id"));
                mydata.add(c);
            }

        }

        final Context o = this;

        ListView l = (ListView) findViewById(R.id.sign_items_List);
        l.setAdapter(new ItemslistArrayAdapte(this, 0, mydata));
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewItemsList(mydata.get(position).getID());
            }
        });
    }


    void viewItemsList(String id) {

        Intent t = new Intent(this, sign_item.class);
        t.putExtra("id", id);
        startActivityForResult(t, 0);
    }
}
