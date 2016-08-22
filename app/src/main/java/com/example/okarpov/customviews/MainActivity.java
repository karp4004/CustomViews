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
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    void testList()
    {
        setContentView(R.layout.activity_main);

        ViewGroup journeyFrame = (ViewGroup)findViewById(R.id.journeyFrame);

        LinearLayout listView = (LinearLayout)findViewById(R.id.journeyList);

        CustomScrollView scrollView = (CustomScrollView)findViewById(R.id.scrollView);
        scrollView.setMargins(0.f, 200, 0.95f, 1.0f);

        scrollView.setInterface(new CustomScrollView.Interface(){
            @Override
            public void onOverscroll(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {

                Log.i("onOverscroll", "scrollX:" + scrollX + " scrollY:" + scrollY + " clampedX:" + clampedX + " clampedY:" + clampedY);
            }

            @Override
            public void onSizeChanged(int w, int h, int oldw, int oldh) {
                //View content_frame = findViewById(R.id.content_frame);
                //journeyFrame.setLayoutParams(new LinearLayout.LayoutParams(content_frame.getWidth(), content_frame.getHeight()));
            }

            @Override
            public void onTouchEvent(MotionEvent ev, float dy) {
            }

            @Override
            public void onScrollEnd() {

                Log.i("onScrollEnd", "onScrollEnd");

                //animateCar();
            }
        });

        scrollView.setAnimated(journeyFrame);

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for(int i=0;i<20;i++) {
            View v = inflater.inflate(R.layout.customlist_item, null);
            listView.addView(v);
        }
    }

    void testGroup()
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_statistic_diagram);
        testList();
    }
}
