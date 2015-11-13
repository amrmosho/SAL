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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pcland15.ismail.sal.libs.cat_list;
import com.pcland15.ismail.sal.libs.dbOperations;
import com.pcland15.ismail.sal.libs.xmlDataModel;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.ConsoleHandler;

public class sign_items_list extends AppCompatActivity {
    String catID = "";


    ItemslistArrayAdapte
            adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_items_list);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }





        catID = getIntent().getStringExtra("id");

        cattable = getIntent().getStringExtra("status");



        steData();


        getData();
        serach();
    }


    TextView itemTtile;
    String table = "";
    String cattable = "";
    void steData() {
        itemTtile = (TextView) findViewById(R.id.item_title);
        if (cattable.equalsIgnoreCase(xmlDataModel.booksCatTable)) {

            table = xmlDataModel.booksTable;
            itemTtile.setText(this.getString(R.string.books));
        } else {
            itemTtile.setText(this.getString(R.string.signs));
            cattable = xmlDataModel.signCatTable;

        }

    }

    void serach() {
        final EditText editsearch = (EditText) findViewById(R.id.serchin);

        final Context o = this;

        // Capture Text in EditText
        editsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                //adapter.filter(text);
                adapter.getFilter().filter(text);

                Toast.makeText(o, text, Toast.LENGTH_SHORT).show();
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

    public void goBack(View view) {

        this.finish();
    }

    void getData() {


        TextView t = (TextView) findViewById(R.id.sign_items_title);
        dbOperations catdb = new dbOperations(cattable, "get_data");
        catdb.where = "id=" + this.catID;
        HashMap<String, String> catdata = catdb.commit().get("0");
        t.setText(catdata.get("title"));








        dbOperations db = new dbOperations(table, "get_data");
        db.where = "cat_id=" + this.catID;



        HashMap<String, HashMap<String, String>> data = db.commit();


        if (data.size() > 0) {



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

            adapter = new ItemslistArrayAdapte(this, 0, mydata);





            ListView l = (ListView) findViewById(R.id.sign_items_List);



            l.setAdapter(adapter);


            l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    viewItemsList(mydata.get(position).getID());
                }
            });
        }
    }


    void viewItemsList(String id) {


        Intent t =null;



            if (table.equalsIgnoreCase(xmlDataModel.booksTable)) {
                t = new Intent(this, books.class);

            } else {
                t = new Intent(this, sign_item.class);


            }

        t.putExtra("id", id);
        startActivity(t);
    }
}
