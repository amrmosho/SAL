package com.pcland15.ismail.sal;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pcland15.ismail.sal.libs.cat_list;
import com.pcland15.ismail.sal.libs.ui;

import java.util.ArrayList;
import java.util.List;


class quizArrayAdapte extends ArrayAdapter<cat_list> {

    Context context;
    List<cat_list> objects;

    public quizArrayAdapte(Context context, int resource, List<cat_list> objects) {
        super(context, resource, objects);
        this.context = context;

        this.objects = new ArrayList<cat_list>();
        this.objects.addAll(objects) ;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        cat_list cat = objects.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.quiz_item, null);
        TextView t = (TextView) view.findViewById(R.id.quiz_ans_title);
        t.setText(cat.getTitle());


        return view;
    }



}
