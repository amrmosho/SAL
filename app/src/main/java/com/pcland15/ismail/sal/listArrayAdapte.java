package com.pcland15.ismail.sal;


import android.widget.ArrayAdapter;
import com.pcland15.ismail.sal.libs.cat_list;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pcland15.ismail.sal.libs.ui;


class listArrayAdapte extends ArrayAdapter<cat_list> {
    public int resLayout= R.layout.cat_list;

    Context context;
    List<cat_list> objects;
    List<cat_list> fobjects;

    public listArrayAdapte(Context context, int resource, List<cat_list> objects) {
        super(context, resource, objects);

        this.objects = new ArrayList<cat_list>();
        this.fobjects = new ArrayList<cat_list>();

        this.context = context;
        this.objects.addAll(objects) ;
        this.fobjects.addAll(objects);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        cat_list cat = objects.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(resLayout, null);

        TextView t = (TextView) view.findViewById(R.id.list_cat_title);


        ImageView i = (ImageView) view.findViewById(R.id.list_cat_image);
        t.setText(cat.getTitle());
        //  i.setImageDrawable(ui.loadImage(config.imagePath + cat.getImage()));

        ui.loadImage(context, i, cat.getImage());

        return view;
    }


    itemsSearch filter;

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new itemsSearch();
        }
        return filter;
    }


    private class itemsSearch extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            constraint = constraint.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if (constraint != null && constraint.toString().length() > 0) {
                ArrayList<cat_list> filteredItems = new ArrayList<cat_list>();

                for (int i = 0, l =fobjects.size(); i < l; i++) {
                    cat_list country = fobjects.get(i);
                    if (country.toString().toLowerCase().contains(constraint))
                        filteredItems.add(country);
                }
                result.count = filteredItems.size();
                result.values = filteredItems;
            } else {
                synchronized (this) {
                    result.values = fobjects;
                    result.count = fobjects.size();
                }
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            objects = (ArrayList<cat_list>) results.values;
            notifyDataSetChanged();
            clear();
            for (int i = 0, l = objects.size(); i < l; i++)
                add(objects.get(i));
            notifyDataSetInvalidated();
        }
    }
}
