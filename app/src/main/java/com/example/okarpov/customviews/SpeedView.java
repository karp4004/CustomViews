package com.example.okarpov.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;

/**
 * Created by karp4004 on 03.10.2016.
 */
public class SpeedView extends FrameLayout {

    public SpeedView(Context context)
    {
        super(context);

        init(context);
    }

    public SpeedView(Context context,
                     AttributeSet attrs)
    {
        super(context, attrs);

        init(context);
    }

    public SpeedView(Context context,
                     AttributeSet attrs,
                     int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    void init(Context context)
    {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        float range = 330;
        float weight = range/100;
        DisplayMetrics metrics = getResources().getDisplayMetrics();

        for(float i=30;i<range;i+=weight) {
            View v = inflater.inflate(getResources().getLayout(R.layout.speed_item), null);
            addView(v);

            FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(600, ViewGroup.LayoutParams.WRAP_CONTENT);
            p.gravity = Gravity.CENTER;

            v.setLayoutParams(p);

            View item = v.findViewById(R.id.item);
            if(item != null)
            {
                item.setRotation(i - 90);

//                            RotateAnimation animation = new RotateAnimation(0, i-90,//(float) Math.toRadians(i),
//                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//            animation.setInterpolator(new LinearInterpolator());
//            animation.setDuration(300);
//            animation.setFillAfter(true);
//
//                item.startAnimation(animation);
            }

//            RotateAnimation animation = new RotateAnimation(0, (float) Math.toRadians(i),
//                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//            animation.setInterpolator(new LinearInterpolator());
//            animation.setDuration(300);
//            animation.setFillAfter(true);
//
//            v.startAnimation(animation);

            //v.setRotation(i);
        }
    }
}
