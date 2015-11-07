package com.pcland15.ismail.sal.libs;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
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

        final CharSequence[] options = {context.getString(R.string.ChoosefromGallery), context.getString(R.string.cancel)};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.AddPhoto));
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals(context.getString(R.string.ChoosefromGallery))) {



                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activity.startActivityForResult(intent, 2);



                } else if (options[item].equals(context.getString(R.string.cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    public List<simpleList> fillSpinner(Spinner newuser_sp_cat, String table) {


        final List<simpleList> mydata = new ArrayList<>();

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

    public String getImage(int resultCode, ImageView viewImage, Intent data) {
        Bitmap yourSelectedImage = null;
        String path = "";
        if (resultCode == activity.RESULT_OK) {


            Uri selectedImage = data.getData();




            path = getRealPathFromURI(activity, selectedImage);
            InputStream imageStream = null;

            //  path= getRealPathFromURI(activity,data.getData());
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


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};



            String wholeID = DocumentsContract.getDocumentId(contentUri);

            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];

            String[] column = { MediaStore.Images.Media.DATA };

            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

             cursor =context.getContentResolver().
                    query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            column, sel, new String[]{ id }, null);






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

