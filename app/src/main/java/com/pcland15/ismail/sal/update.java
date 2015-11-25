package com.pcland15.ismail.sal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.pcland15.ismail.sal.libs.ui;

public class update extends AppCompatActivity {
    TextView about_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_update);


        TextView about_title = (TextView) findViewById(R.id.about_title);
        about_data = (TextView) findViewById(R.id.about_data);
        about_title.setText(this.getString(R.string.update));

    }

  /*  @Override
    protected void onStart() {
        super.onStart();
        update();
    }*/

    @Override
    protected void onResume() {
        super.onResume();

        update();
    }

    public void goBack(View view) {
        this.finish();
    }

    void update() {

        ui.updateData(this, about_data);
    }


}
