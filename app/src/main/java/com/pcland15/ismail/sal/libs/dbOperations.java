package com.pcland15.ismail.sal.libs;

import android.graphics.Bitmap;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
//import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by empcl_000 on 19/10/2015.
 */
public class dbOperations {
    public String table = "";
    public String type = "";
    public String where = "";

    public static HashMap<String, String> userData;
    public HashMap<String, String> addData;
    public HashMap<String, String> addImageData;

    public dbOperations(String table, String type) {
        this.table = table;
        this.type = type;
        this.addData = new HashMap<>();

        this.addImageData = new HashMap<>();

    }

    public HashMap<String, HashMap<String, String>> commit() {
        return getFromserver();
    }


    MultipartEntity geturldata() {

        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
/*
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("table", this.table));
        nameValuePairs.add(new BasicNameValuePair("set", this.type));
        nameValuePairs.add(new BasicNameValuePair("where", this.where));

        */

        try {

            entity.addPart("set", new StringBody(this.type));
            entity.addPart("where", new StringBody(this.where));
            entity.addPart("table", new StringBody(this.table));


            if (this.addData != null) {
                for (String k : this.addData.keySet()) {
                    entity.addPart(k, new StringBody(this.addData.get(k)));

                }

            }


            if (this.addImageData != null) {
                for (String k : this.addImageData.keySet()) {

                    File f = new File(this.addImageData.get(k));
                    entity.addPart(k, new FileBody(f));

                }

            }

        } catch (UnsupportedEncodingException e) {


            e.printStackTrace();
        } catch (Exception e) {

            String a = e.getMessage();

            a = a;
        }
        return entity;
    }


    public HashMap<String, HashMap<String, String>> getFromserver() {


        // String URL = config.webServiecURL;
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(config.webServiecURL);
        try {
            httppost.setEntity(geturldata());

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
        } catch (ClientProtocolException e) {
            Log.d("ClientProtocolException", e.getMessage());
        } catch (Exception e) {
            Log.d("readJSONFeed", e.getLocalizedMessage());
        }
        String c = stringBuilder.toString();

//[{"id":"1","title":"\u0627\u0644\u062d\u0631\u0648\u0641 \u0627\u0644\u0627\u0628\u062c\u062f\u064a\u0647","image":"5628cf739d5ff.jpg","des":"sdfsdfsdf\r\nsd\r\nfsd\r\nfsd\r\nfs\r\ndf","enabled":"0"}
// ,{"id":"2","title":"\u0648\u0633\u0627\u0626\u0644 \u0627\u0644\u0645\u0648\u0635\u0644\u0627\u062a","image":"5628cf84e5d66.jpg","des":"\u0648\u0633\u0627\u0626\u0644 \u0627\u0644\u0645\u0648\u0635\u0644\u0627\u062a \u0648\u0633\u0627\u0626\u0644 \u0627\u0644\u0645\u0648\u0635\u0644\u0627\u062a \u0648\u0633\u0627\u0626\u0644 \u0627\u0644\u0645\u0648\u0635\u0644\u0627\u062a \u0648\u0633\u0627\u0626\u0644 \u0627\u0644\u0645\u0648\u0635\u0644\u0627\u062a","enabled":"0"},{"id":"3","title":"\u0641\u0649 \u0627\u0644\u0645\u062f\u0631\u0633\u0647","image":"5628cf521309c.jpg","des":"\u0641\u0649 \u0627\u0644\u0645\u062f\u0631\u0633\u0647 \u0641\u0649 \u0627\u0644\u0645\u062f\u0631\u0633\u0647 \u0641\u0649 \u0627\u0644\u0645\u062f\u0631\u0633\u0647","enabled":"0"}]
        return this.jsonStringToArray(c);
    }


    HashMap<String, HashMap<String, String>> jsonStringToArray(String jsonString) {
        //  ArrayList<String> stringArray = new ArrayList<String>();
        HashMap<String, String> map = new HashMap<String, String>();
        HashMap<String, HashMap<String, String>> r = new HashMap<String, HashMap<String, String>>();
        try {
            JSONObject c = new JSONObject(jsonString);


            r.put("log", jsoneTomap(c));


            if (c.has("row")) {
                r.put("0", jsoneTomap(c.getJSONObject("row")));
            }
            if (c.has("data")) {
                JSONArray data = c.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    r.put(i + "", jsoneTomap(data.getJSONObject(i)));
                }
            }

        } catch (JSONException e) {

            String aaaaaa = e.getMessage();
            Log.d("readJSONFeed", e.getLocalizedMessage());
        }
        return r;
    }


    HashMap<String, String> jsoneTomap(JSONObject thisdata) {
        HashMap<String, String> mapdata = new HashMap<String, String>();

        Iterator<String> ir = thisdata.keys();

        try {
            while (ir.hasNext()) {
                String currentKey = ir.next();
                mapdata.put(currentKey, thisdata.getString(currentKey));
            }

        } catch (JSONException e) {

            String aaaaaa = e.getMessage();
            Log.d("readJSONFeed", e.getLocalizedMessage());
        }


        return mapdata;

    }

    HashMap<String, String> jsonStringToArrayM(String jsonString) {
        ArrayList<String> stringArray = new ArrayList<String>();
        HashMap<String, String> map = new HashMap<String, String>();

        try {

            JSONObject c = new JSONObject(jsonString);


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


