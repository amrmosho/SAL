package com.pcland15.ismail.sal.libs;

import android.graphics.drawable.Drawable;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by ismail on 10/22/2015.
 */
public class ui {
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}
