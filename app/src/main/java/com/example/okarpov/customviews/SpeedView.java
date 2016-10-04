package com.example.okarpov.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by karp4004 on 03.10.2016.
 */
public class SpeedView extends FrameLayout {

    int mValue = 0;

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

            FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(
                    (int)getResources().getDimension(R.dimen.size3),
                    ViewGroup.LayoutParams.WRAP_CONTENT);

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

            int idx = indexOfChild(v);

            View item_head = v.findViewById(R.id.item_head);
            if (item_head != null) {

                if(idx > 6 && (idx-2)%5==0)
                {
                    FrameLayout.LayoutParams par = new
                            FrameLayout.LayoutParams((int)getResources().getDimension(R.dimen.size7),
                            (int)getResources().getDimension(R.dimen.size5));
                    par.gravity = Gravity.CENTER_VERTICAL;

                    item_head.setLayoutParams(par);

                    TextView txtVal = (TextView)v.findViewById(R.id.txtVal);
                    if(txtVal != null)
                    {
                        txtVal.setText("" + idx);
                        txtVal.setRotation(-item.getRotation());
                    }
                }
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

    public void setValue(int v)
    {
        Log.i("setValue", "v:" + v);

        if(mValue == v)
            return;

        if(mValue > v)
        {
            for(int i=mValue;i>=v;i--)
            {
                View ch = getChildAt(i);
                if(ch != null) {
                    View item_head = ch.findViewById(R.id.item_head);
                    if (item_head != null) {
                        item_head.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_selected));
                    }
                }
            }
        }
        else {
            for (int i = mValue; i < v; i++) {
                View ch = getChildAt(i);
                if(ch != null) {
                    View item_head = ch.findViewById(R.id.item_head);
                    if (item_head != null) {
                        item_head.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_selected2));
                    }
                }
            }
        }

        mValue = v;
    }
}
