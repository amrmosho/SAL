package com.pcland15.ismail.sal;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.pcland15.ismail.sal.libs.config;
import com.pcland15.ismail.sal.libs.dbOperations;
import com.pcland15.ismail.sal.libs.xmlDataModel;

import java.util.HashMap;

public class books extends AppCompatActivity {
    WebView w = null;
    TextView t = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }


        w = (WebView) findViewById(R.id.webview);
        t = (TextView) findViewById(R.id.bookTitle);



        myID = getIntent().getStringExtra("id");



        w.getSettings().setJavaScriptEnabled(true);
        w.getSettings().setPluginState(WebSettings.PluginState.ON);


        w.setWebViewClient(new Callback());


        openFile();

    }

    String myID = "";

    void openFile() {

        dbOperations db = new dbOperations(xmlDataModel.booksTable, "get_data");


        db.where = "id=" + this.myID;




        HashMap<String, HashMap<String, String>> allData = db.commit();

        HashMap<String, String> data = allData.get("0");


        t.setText(data.get("name"));



        String pdfURL = config.imagePath + data.get("file");



        w.loadUrl("http://docs.google.com/gview?embedded=true&url=" + pdfURL);


    }

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(
                WebView view, String url) {
            return (false);
        }
    }

    public void goBack(View view) {

        this.finish();
    }
}
