package com.pcland15.ismail.sal;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pcland15.ismail.sal.libs.cat_list;
import com.pcland15.ismail.sal.libs.config;
import com.pcland15.ismail.sal.libs.ui;

import java.util.List;


class ItemslistArrayAdapte extends ArrayAdapter<cat_list> {
    public int resLayout= R.layout.items_list;

    Context context;
    List<cat_list> objects;

    public ItemslistArrayAdapte(Context context, int resource, List<cat_list> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        cat_list cat = objects.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(resLayout, null);

        TextView t = (TextView) view.findViewById(R.id.list_items_title);
        ImageView i = (ImageView) view.findViewById(R.id.list_items_image);
        t.setText(cat.getTitle());
        i.setImageDrawable(ui.LoadImageFromWebOperations(config.imagePath + cat.getImage()));
        return view;
    }
}
