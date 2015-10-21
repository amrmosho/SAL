package com.pcland15.ismail.sal.libs;

        import java.io.BufferedReader;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.Iterator;


        import org.apache.http.HttpEntity;
        import org.apache.http.HttpResponse;
        import org.apache.http.StatusLine;
        import org.apache.http.client.HttpClient;
        import org.apache.http.client.methods.HttpGet;
        import org.apache.http.impl.client.DefaultHttpClient;
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import com.pcland15.ismail.sal.libs.config;
        import android.util.Log;
/**
 * Created by empcl_000 on 19/10/2015.
 */
public class dbOperations {
    public  String table="";
    public  String type="";

    public HashMap<String ,String >addData;

public dbOperations(String table, String type){

this.table=table;
    this.type=type;

}
    public String commit(){

        String r="";
        switch (this.type){
            case "get_row":
                ArrayList<String>  r22=   getFromserver("set="+type+"&table=studentes");
                break;
            case "get_data":
                break;
            case "insert":
                break;
            case "delete":
                break;
            case "update":
                break;

        }

        return r;

    }







    public     ArrayList<String> getFromserver(String oprions)  {


        String  URL= config.webServiecURL +"?"+oprions;
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);
        try {
            HttpResponse response = httpClient.execute(httpGet);
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

        String c=stringBuilder.toString();



        ArrayList<String> ddd=this.jsonStringToArray(c);

        return   ddd;
    }


    ArrayList<String> jsonStringToArray(String jsonString)  {
        ArrayList<String> stringArray = new ArrayList<String>();
        HashMap<String, String> map = new HashMap<String, String>();

        try {


            JSONObject c;



            c = new JSONObject(jsonString);




            Iterator<String> iter = c.keys();
            while(iter.hasNext())   {
                String currentKey = iter.next();
                map.put(currentKey, c.getString(currentKey));
            }





        } catch (JSONException e) {

            String aaaaaa=e.getMessage();
            Log.d("readJSONFeed", e.getLocalizedMessage());
        }
        return stringArray;
    }

}


