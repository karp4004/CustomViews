package com.example.okarpov.customviews;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

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

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View customHeader = findViewById(R.id.customHeader);

        listView.addHeaderView(customHeader);

        listView.setMargins(300, 600, 4);
        listView.setInterface(new CustomList.Interface(){
            @Override
            public void onOverscroll() {
                Log.i("CustomList.Interface", "onOverscroll");
            }
        });

//        final CustomList2 listView2 = (CustomList2)findViewById(R.id.clist2);
//        listView2.setMargins(300, 600, 4);
//        listView2.addView(inflater.inflate(R.layout.customlist_item, null));
//        listView2.addView(inflater.inflate(R.layout.customlist_item, null));
//        listView2.addView(inflater.inflate(R.layout.customlist_item, null));
//        listView2.addView(inflater.inflate(R.layout.customlist_item, null));
//        listView2.addView(inflater.inflate(R.layout.customlist_item, null));
//        listView2.addView(inflater.inflate(R.layout.customlist_item, null));
//        listView2.addView(inflater.inflate(R.layout.customlist_item, null));
//        listView2.addView(inflater.inflate(R.layout.customlist_item, null));
//        listView2.addView(inflater.inflate(R.layout.customlist_item, null));
//        listView2.addView(inflater.inflate(R.layout.customlist_item, null));
//        listView2.addView(inflater.inflate(R.layout.customlist_item, null));
//        listView2.addView(inflater.inflate(R.layout.customlist_item, null));
//        listView2.addView(inflater.inflate(R.layout.customlist_item, null));
//        listView2.addView(inflater.inflate(R.layout.customlist_item, null));
//        listView2.addView(inflater.inflate(R.layout.customlist_item, null));
//        listView2.addView(inflater.inflate(R.layout.customlist_item, null));
//        listView2.addView(inflater.inflate(R.layout.customlist_item, null));
//        listView2.addView(inflater.inflate(R.layout.customlist_item, null));
//        listView2.addView(inflater.inflate(R.layout.customlist_item, null));
//        listView2.addView(inflater.inflate(R.layout.customlist_item, null));
//        listView.addHeaderView(customHeader);

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
