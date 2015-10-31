package com.pcland15.ismail.sal;

import android.content.Context;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pcland15.ismail.sal.libs.cat_list;
import com.pcland15.ismail.sal.libs.dbOperations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class act_list extends AppCompatActivity {

    String status = "";

    String title = "";
    String table = "";
    String id = "";
    HashMap<String, HashMap<String, String>> dbdata;
    listArrayAdapte adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_list);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }

        status = getIntent().getStringExtra("status");
        steData();

        TextView    cat_list_navbar_title=  (TextView) findViewById(R.id.cat_list_navbar_title);

        cat_list_navbar_title.setText(title);
        updateList();

    }


    void steData() {
        title = this.getString(R.string.newcat_title);
        if (status.equalsIgnoreCase("quiz")) {
            title += " " + this.getString(R.string.panel_quiz);
            table = "quiz_categries";
        } else if (status.equalsIgnoreCase("sign")) {
            title += " " + this.getString(R.string.sign);
            table = "sign_categries";
        } else {
            title += " " + this.getString(R.string.add_user);
            table = "studentes_groups";
        }

    }

    public void dodelete(View view) {

        dbOperations db = new dbOperations(table, "delete");
        db.where = "id=" + id;
        HashMap<String, HashMap<String, String>> a = db.commit();

        if (a.get("log").get("opstauts").equalsIgnoreCase("true")) {
            Toast t = Toast.makeText(this, "Done", Toast.LENGTH_LONG);

            t.show();
            updateList();
        } else {
            Toast t = Toast.makeText(this, "Error", Toast.LENGTH_LONG);
            t.show();
        }

    }

    void updateList() {

        dbOperations db = new dbOperations(table, "get_data");
        HashMap<String, HashMap<String, String>> data = db.commit();

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


        GridView l = (GridView) findViewById(R.id.sign_cat_List);
        adapter = new listArrayAdapte(this, 0, mydata);
        l.setAdapter(adapter);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long iid) {
                id = mydata.get(position).getID();
            }
        });
    }

    public void goback(View view) {
        this.finish();
    }
}
