package com.pcland15.ismail.sal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pcland15.ismail.sal.libs.dbOperations;
import com.pcland15.ismail.sal.libs.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

public class user_cat extends AppCompatActivity {
    String status = "";

    String title = "";
    String table = "";
    TextView titletxt = null;
    TextView cat_navbar_title = null;
    ImageView viewImage;
    dbOperations o = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cat);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }

        status = getIntent().getStringExtra("status");
        titletxt = (TextView) findViewById(R.id.cat_title);
        viewImage = (ImageView) findViewById(R.id.imageView5);


        cat_navbar_title = (TextView) findViewById(R.id.cat_navbar_title);

        steData();

        cat_navbar_title.setText(title);


        o = new dbOperations(table, "insert");
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




    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index
                = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    ui u = new ui(this);

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        o.addImageData.put("image", u.getImage(resultCode, viewImage, data));

    }

    public void select_image(View view) {
        u.getImage(this);
    }


    public void goList(View view) {
        Intent t = new Intent(this, act_list.class);

        t.putExtra("status",this.status);
        startActivity(t);
    }

    public void send_data(View view) {
        o.addData.put("title", titletxt.getText().toString());

        HashMap<String, HashMap<String, String>> a = o.commit();
        if (a.get("log").get("opstauts").equalsIgnoreCase("true")) {
            Toast t = Toast.makeText(this, "Done", Toast.LENGTH_LONG);
            t.show();
            titletxt.setText("");
        } else {
            Toast t = Toast.makeText(this, "Error", Toast.LENGTH_LONG);
            t.show();
        }

    }

}
