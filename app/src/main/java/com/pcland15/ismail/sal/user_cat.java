package com.pcland15.ismail.sal;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.pcland15.ismail.sal.libs.dbOperations;

import java.util.HashMap;

public class user_cat extends AppCompatActivity {
    String status = "";

    String title = "";
    String table = "";
    TextView titletxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cat);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }

        status = getIntent().getStringExtra("sttaus");
        titletxt = (TextView) findViewById(R.id.cat_title);

     /*

        des = (TextView) findViewById(R.id.new_uesr_password);
        image = (TextView) findViewById(R.id.new_uesr_name);*/


        steData();


        updateTitle();
    }


    void steData() {
        title = this.getString(R.string.newcat_title);
        if (status.equalsIgnoreCase("user")) {

            title += " " + this.getString(R.string.add_user);


            table = "studentes_groups";


        } else if (status.equalsIgnoreCase("sign")) {

            title += " " + this.getString(R.string.sign);
            table = "sign_categries";


        } else if (status.equalsIgnoreCase("quiz")) {

            title += " " + this.getString(R.string.panel_quiz);
            table = "quiz_categries";


        }


    }


    void updateTitle() {

        dbOperations o = new dbOperations(table, "insert");
        o.addData.put("title", titletxt.getText().toString());


        // o.addData.put("image", email.getText().toString());
        //  o.addData.put("des", email.getText().toString());


        HashMap<String, HashMap<String, String>> a = o.commit();
        if (a.get("log").get("opstauts").equalsIgnoreCase("true")) {
            Toast t = Toast.makeText(this, "Done", Toast.LENGTH_LONG);
            t.show();
            //  email.setText("");
            titletxt.setText("");
            //  password.setText("");
        } else {
            Toast t = Toast.makeText(this, "Error", Toast.LENGTH_LONG);
            t.show();
        }

        /*

        */

    }


    void updateData() {


    }


}
