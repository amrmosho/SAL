package com.pcland15.ismail.sal.libs;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pcland15.ismail.sal.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ismail on 10/31/2015.
 */
public class spinnerAdapter extends ArrayAdapter<simpleList> {

    List<simpleList> objects;


    private Context context;
    private List<simpleList> myObjs;

    public spinnerAdapter(Context context, int textViewResourceId,
                          List<simpleList> myObjs) {
        super(context, textViewResourceId, myObjs);
        this.context = context;
        this.myObjs = myObjs;
    }

    public int getCount() {
        return myObjs.size();
    }

    public simpleList getItem(int position) {
        return myObjs.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setText(myObjs.get(position).getText());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(context);
        label.setText(myObjs.get(position).getText());
        return label;
    }
}



