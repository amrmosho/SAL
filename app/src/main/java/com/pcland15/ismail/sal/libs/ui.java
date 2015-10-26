package com.pcland15.ismail.sal.libs;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by ismail on 10/22/2015.
 */
public class ui {
    public static void loadImage(Context context, ImageView i, String name) {


        try {
String sname=name;
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
}
