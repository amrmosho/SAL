package com.pcland15.ismail.sal.libs;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pcland15.ismail.sal.libs.config;

import android.util.Log;

/**
 * Created by empcl_000 on 19/10/2015.
 */
public class dbOperations {
    public String table = "";
    public String type = "";
    public String where = "";

    public HashMap<String, String> addData;

    public dbOperations(String table, String type) {
        this.table = table;
        this.type = type;
        this.addData=new HashMap<>();
    }

    public   HashMap<String, String> commit() {
        return getFromserver();
    }


    List<NameValuePair> geturldata() {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("table", this.table));
        nameValuePairs.add(new BasicNameValuePair("set", this.type));
        nameValuePairs.add(new BasicNameValuePair("where", this.where));
      if (this.addData !=null) {
           for (String k : this.addData.keySet()) {
                nameValuePairs.add(new BasicNameValuePair(k, this.addData.get(k)));
            }

    }
        return nameValuePairs;
    }


    public    HashMap<String, String> getFromserver() {
        String URL = config.webServiecURL ;
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(config.webServiecURL);
        try {
            httppost.setEntity(new UrlEncodedFormEntity(geturldata()));
            HttpResponse response = httpClient.execute(httppost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
            } else {
                Log.d("JSON", "Failed to download file");
            }
        } catch (Exception e) {
            Log.d("readJSONFeed", e.getLocalizedMessage());
        }
        String c = stringBuilder.toString();


        return this.jsonStringToArray(c);
    }


    HashMap<String, String>jsonStringToArray(String jsonString) {
        ArrayList<String> stringArray = new ArrayList<String>();
        HashMap<String, String> map = new HashMap<String, String>();

        try {


            JSONObject c;


            c = new JSONObject(jsonString);


            Iterator<String> iter = c.keys();
            while (iter.hasNext()) {
                String currentKey = iter.next();
                map.put(currentKey, c.getString(currentKey));
            }


        } catch (JSONException e) {

            String aaaaaa = e.getMessage();
            Log.d("readJSONFeed", e.getLocalizedMessage());
        }
        return map;
    }

}


