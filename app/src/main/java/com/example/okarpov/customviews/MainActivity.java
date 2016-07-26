package com.example.okarpov.customviews;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomAdapter a = new CustomAdapter(this);
        final CustomList listView = (CustomList)findViewById(R.id.cList);
        listView.setAdapter(a);

        a.addItem("asdf");
        a.addItem("fdfdfd");
        a.addItem("asdfd");
        a.addItem("dsasaa");
        a.addItem("aaaaaa");
        a.addItem("aaaaaa");
        a.addItem("aaaaaa");
        a.addItem("aaaaaa");
        a.addItem("aaaaaa");
        a.addItem("aaaaaa");
        a.addItem("aaaaaa");
        a.addItem("aaaaaa");
        a.addItem("aaaaaa");
        a.addItem("aaaaaa");
        a.addItem("aaaaaa");
        a.addItem("aaaaaa");
        a.addItem("aaaaaa");

        a.notifyDataSetChanged();

        listView.setMargins(300, 600, 4);

//        CountDownTimer routeFinishTimer = new CountDownTimer(100, 100) {
//
//            public void onTick(long millisUntilFinished) {
//            }
//
//            public void onFinish() {
//                float x = listView.getX();
//                listView.animate().x(x + 1);
//
//                start();
//            }
//        }.start();
    }
}
