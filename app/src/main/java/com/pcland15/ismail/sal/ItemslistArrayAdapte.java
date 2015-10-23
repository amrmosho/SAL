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
import com.pcland15.ismail.sal.libs.ui;

import java.util.List;


class ItemslistArrayAdapte extends ArrayAdapter<cat_list> {
    public int resLayout = R.layout.items_list;

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
        t.setText(cat.getTitle());
        ImageView i = (ImageView) view.findViewById(R.id.list_items_image);

        //

        //

        ui.loadImage(context, i, cat.getImage());

       /* try {

            String name = cat.getImage();
            int pos = name.lastIndexOf(".");
            if (pos > 0) {
                name = name.substring(0, pos);
            }
            int myi = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
            i.setImageResource(myi);

        } catch (Exception e) {

            try {
                i.setImageDrawable();

            } catch (Exception x) {

            }
        }*/

        return view;
    }
}
