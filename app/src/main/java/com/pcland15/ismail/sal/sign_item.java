package com.pcland15.ismail.sal;

import android.content.Context;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.VideoView;

import com.pcland15.ismail.sal.libs.config;
import com.pcland15.ismail.sal.libs.dbOperations;
import com.pcland15.ismail.sal.libs.ui;
import com.pcland15.ismail.sal.libs.xmlDataModel;

import java.util.HashMap;

public class sign_item extends AppCompatActivity {
    String myID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_item);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }


        //  TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
        myID = getIntent().getStringExtra("id");


        TabHost tabHost = (TabHost) findViewById(R.id.mytabhost);


        tabHost.setup();

        TabHost.TabSpec tabimage = tabHost.newTabSpec("Image");
        tabimage.setContent(R.id.tab_image);
        tabimage.setIndicator(getString(R.string.image));

        TabHost.TabSpec tabvido = tabHost.newTabSpec("Video");
        tabvido.setContent(R.id.tab_vido);
        tabvido.setIndicator(getString(R.string.video));



        tabHost.addTab(tabimage);
        tabHost.addTab(tabvido);

        getData();

    }


    void getData() {

        dbOperations db = new dbOperations(xmlDataModel.signTable, "get_data");


        db.where = "id=" + this.myID;


        HashMap<String, HashMap<String, String>> allData = db.commit();


        HashMap<String, String> data = allData.get("0");


        ImageView i = (ImageView) findViewById(R.id.sign_item_image);


        ui.loadImage(this, i, data.get("image"));


        TextView t = (TextView) findViewById(R.id.sign_item_title);
        t.setText(data.get("name"));


        // String vidAddress = config.vidoePath + data.get("video"); //"https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";
        // Uri vidUri = Uri.parse(vidAddress);
        // VideoView v = (VideoView) findViewById(R.id.sign_item_vido);
        // v.setVideoURI(vidUri);
        // MediaController mediaController = new MediaController(this);
        // mediaController.setAnchorView(v);
        //v.setMediaController(mediaController);


        ui.loadVideoloadImage(this, (VideoView) findViewById(R.id.sign_item_vido), data.get("video"));

        TextView d = (TextView) findViewById(R.id.sign_item_des);
        d.setText(data.get("desc"));


    }

    public void goBack(View view) {

        this.finish();
    }
}
