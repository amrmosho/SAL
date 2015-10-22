package com.pcland15.ismail.sal;

import android.app.Activity;
import android.content.Context;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

        getData();
    }


    void getData() {
        dbOperations db = new dbOperations("sign_categries", "get_data");
        HashMap<String, HashMap<String, String>> data = db.commit();

        List<cat_list> mydata = new ArrayList<>();
        for (String k : data.keySet()) {
            if (!k.equalsIgnoreCase("log")) {
                cat_list c = new cat_list();
                c.setTitle(data.get(k).get("title"));
                c.setImage(data.get(k).get("image"));
                mydata.add(c);
            }

        }


        GridView l = (GridView) findViewById(R.id.sign_cat_List);
        ArrayAdapter<cat_list> c = new listArrayAdapte(this, 0, mydata);

        l.setAdapter(c);
    }


    class listArrayAdapte extends ArrayAdapter<cat_list> {



        Context context;
        List<cat_list> objects;

        public listArrayAdapte(Context context, int resource, List<cat_list> objects) {
            super(context, resource, objects);
            this.context = context;
            this.objects = objects;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            cat_list cat = objects.get(position);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.cat_list, null);

            TextView t = (TextView) view.findViewById(R.id.list_cat_title);
            ImageView i = (ImageView) view.findViewById(R.id.list_cat_image);
            t.setText(cat.getTitle());
          i.setImageDrawable(ui.LoadImageFromWebOperations(config.imagePath+cat.getImage()));
            return view;
        }
    }



}
