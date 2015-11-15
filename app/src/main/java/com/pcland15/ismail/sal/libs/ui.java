package com.pcland15.ismail.sal.libs;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v4.content.CursorLoader;

import com.pcland15.ismail.sal.R;

import android.database.Cursor;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ismail on 10/22/2015.
 */
public class ui {

    Activity activity;

    public ui(Activity activity) {
        this.activity = activity;
    }

    public static void loadImage(Context context, ImageView i, String name) {


        try {


            String sname = name;


            int pos = name.lastIndexOf(".");

            if (pos > 0) {
                sname = name.substring(0, pos);
            }

            int myi = context.getResources().getIdentifier(sname, "drawable", context.getPackageName());

            i.setImageResource(myi);

        } catch (Exception e) {


            try {


                InputStream is = (InputStream) new URL(config.imagePath + name).getContent();

                Drawable d = Drawable.createFromStream(is, "src name");


                i.setImageDrawable(d);

                i.setPadding(1, 1, 1, 1);


                i.setBackgroundColor(0xFF00FF00);


            } catch (Exception x) {
            }
        }


    }

    public static void applyFont(Context context, TextView tx) {
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/AL-FARES.TTF");
        tx.setTypeface(custom_font);
    }


    public void getImage(final Context context) {


        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


        activity.startActivityForResult(intent, 2);


    }


    public List<simpleList> fillSpinner(Spinner newuser_sp_cat, String table ,String title) {


        final List<simpleList> mydata = new ArrayList<>();

        simpleList f = new simpleList();
        f.setText(title);
        f.setValue("-1");
        mydata.add(f);

        dbOperations db = new dbOperations(table, "get_data");
        HashMap<String, HashMap<String, String>> data = db.commit();
        for (String k : data.keySet()) {
            if (!k.equalsIgnoreCase("log")) {
                simpleList l = new simpleList();
                l.setText(data.get(k).get("title"));
                l.setValue(data.get(k).get("id"));

                mydata.add(l);
            }

        }
        spinnerAdapter dataAdapter = new spinnerAdapter(activity, 0, mydata);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newuser_sp_cat.setAdapter(dataAdapter);

        return mydata;

    }


    public List<simpleList> fillSpinner(Spinner newuser_sp_cat, String table,String title, String textView) {


        final List<simpleList> mydata = new ArrayList<>();

        dbOperations db = new dbOperations(table, "get_data");
        HashMap<String, HashMap<String, String>> data = db.commit();

        simpleList f = new simpleList();
        f.setText(title);
        f.setValue("-1");
        mydata.add(f);

        for (String k : data.keySet()) {
            if (!k.equalsIgnoreCase("log")) {
                simpleList l = new simpleList();
                l.setText(data.get(k).get(textView));
                l.setValue(data.get(k).get("id"));

                mydata.add(l);
            }

        }
        spinnerAdapter dataAdapter = new spinnerAdapter(activity, 0, mydata);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newuser_sp_cat.setAdapter(dataAdapter);

        return mydata;

    }

    public String getImage(int resultCode, ImageView viewImage, Intent data) {
        Bitmap yourSelectedImage = null;
        String path = "";
        if (resultCode == activity.RESULT_OK) {


            Uri selectedImage = data.getData();


            path = getPathFromURI(activity, selectedImage);



            InputStream imageStream = null;

            try {
                imageStream = activity.getContentResolver().openInputStream(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            yourSelectedImage = BitmapFactory.decodeStream(imageStream);


            viewImage.setImageBitmap(yourSelectedImage);
        }

        return path;
    }








    public String getImage(int resultCode, TextView viewImage, Intent data) {
        Bitmap yourSelectedImage = null;
        String path = "";
        if (resultCode == activity.RESULT_OK) {


            Uri selectedImage = data.getData();


            path = getPathFromURI(activity, selectedImage);
            InputStream imageStream = null;

            //  path= getRealPathFromURI(activity,data.getData());
            try {
                imageStream = activity.getContentResolver().openInputStream(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            yourSelectedImage = BitmapFactory.decodeStream(imageStream);
            viewImage.setText(path);
        }

        return path;
    }



   /*

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};


            String wholeID = DocumentsContract.getDocumentId(contentUri);

            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];

            String[] column = {MediaStore.Images.Media.DATA};

            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            cursor = context.getContentResolver().
                    query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            column, sel, new String[]{id}, null);


            //   cursor = context.getContentResolver().query(contentUri, proj, null, null, null);


            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
*/

    public static String getPathFromURI(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }


    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }



    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


/*
    Bitmap drawable_from_url(String url) throws java.net.MalformedURLException, java.io.IOException {
        Bitmap x;
        HttpURLConnection connection = (HttpURLConnection)new URL(url) .openConnection();
        connection.setRequestProperty("User-agent","Mozilla/4.0");
        connection.connect();
        InputStream input = connection.getInputStream();
        x = BitmapFactory.decodeStream(input);
        return x;
    }*/
}

