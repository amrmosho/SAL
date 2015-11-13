package com.pcland15.ismail.sal;

import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pcland15.ismail.sal.libs.cat_list;
import com.pcland15.ismail.sal.libs.dbOperations;
import com.pcland15.ismail.sal.libs.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class quiz_itme extends AppCompatActivity {
    TextView title;
    ImageView img;
    ImageView imgStatus;
    TextView rightAnsNumber;
    TextView wrongAnsNumber;
    RelativeLayout dataLayout;
    RelativeLayout Resayout;

    ImageView imgResStatus;
    TextView quizAnsP;
    String ansid = "";
    ListView list;
    String catID = "";

    String position = "0";




    HashMap<String, HashMap<String, String>> allData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_itme);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        title = (TextView) findViewById(R.id.quiz_q_title);
        img = (ImageView) findViewById(R.id.quiz_q_image);
        list = (ListView) findViewById(R.id.quiz_list);













        imgStatus = (ImageView) findViewById(R.id.quiz_status_image);
        wrongAnsNumber = (TextView) findViewById(R.id.wrong_ans_number);
        rightAnsNumber = (TextView) findViewById(R.id.right_ans_number);
        dataLayout = (RelativeLayout) findViewById(R.id.quiz_item_data);
        Resayout = (RelativeLayout) findViewById(R.id.quiz_item_res);
        catID = getIntent().getStringExtra("id");

        imgResStatus = (ImageView) findViewById(R.id.quiz__res_status);
        quizAnsP = (TextView) findViewById(R.id.quiz_ans_p);







        dbOperations db = new dbOperations("quiz_questions", "get_data");
        db.where = "cat_id=" + this.catID;
        allData = db.commit();




        getData();

    }

    void getData() {


        if (allData.containsKey(this.position)) {


            dataLayout.setVisibility(View.VISIBLE);
            Resayout.setVisibility(View.GONE);
            imgStatus.setVisibility(View.GONE);

            HashMap<String, String> data;
            data = allData.get(this.position);




            title.setText(data.get("question"));
            ui.loadImage(this, img, data.get("image"));


            ansid = data.get("answer");






            dbOperations db = new dbOperations("answers", "get_data");
            db.where = "q_id=" + data.get("id");


            final HashMap<String, HashMap<String, String>> ansData = db.commit();


            if (ansData.size() > 0) {
                final List<cat_list> mydata = new ArrayList<>();

                for (String k : ansData.keySet()) {
                    if (!k.equalsIgnoreCase("log")) {
                        cat_list c = new cat_list();
                        c.setID(ansData.get(k).get("id"));
                        c.setTitle(ansData.get(k).get("description"));
                        mydata.add(c);
                    }

                }

                quizArrayAdapte
                        adapter;
                adapter = new quizArrayAdapte(this, 0, mydata);


                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        goNext(mydata.get(position).getID());
                    }
                });

            }
        } else {


            float all = this.rightAns + this.wrongAns;

            float allp = (this.rightAns/all) * 100;




            if (allp >= 50) {
                imgResStatus.setImageResource(R.drawable.thumb_up);
            } else {
                imgResStatus.setImageResource(R.drawable.thumb_down);
            }



            quizAnsP.setText(allp + "%");

            rightAnsNumber.setText("" + this.rightAns);
            wrongAnsNumber.setText("" + this.wrongAns);


            dataLayout.setVisibility(View.GONE);
            Resayout.setVisibility(View.VISIBLE);
        }
    }


    int rightAns = 0;
    int wrongAns = 0;


    public void goNext(String id) {



        if (id.equalsIgnoreCase(this.ansid)) {
            imgStatus.setImageResource(R.drawable.right);
            rightAns++;

        } else {
            imgStatus.setImageResource(R.drawable.wrong);

            wrongAns++;

        }





        this.position = "" + (Integer.parseInt(this.position) + 1);




        imgStatus.setVisibility(View.VISIBLE);





        Handler myHandler = new Handler();
        myHandler.postDelayed(mMyRunnable, 1000);


    }

    private Runnable mMyRunnable = new Runnable() {
        @Override
        public void run() {
            getData();


        }
    };


    public void goBack(View view) {

        this.finish();
    }
}
