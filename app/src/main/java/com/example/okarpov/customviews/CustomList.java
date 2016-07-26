package com.example.okarpov.customviews;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * Created by okarpov on 7/25/2016.
 */
public class CustomList extends ListView implements
        GestureDetector.OnGestureListener {

    int marginMin;
    int marginMax;
    int marginSideCoeff;
    int mCount = 0;
    int mVerticalOffset = 0;
    float mDY = 0;
    private GestureDetectorCompat mDetector;

    public CustomList(Context context)
    {
        super(context);
        init(context);
    }

    public CustomList(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public CustomList(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context)
    {
        mDetector = new GestureDetectorCompat(context,this);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();

        Log.i("onTouchEvent", "mDY:" + mDY + " params.topMargin:" + params.topMargin + " getVerticalScrollbarPosition():"
                + getVerticalScrollbarPosition() + " computeVerticalScrollOffset:" + computeVerticalScrollOffset()
                + "computeVerticalScrollRange():" + computeHorizontalScrollRange()
                + "computeVerticalScrollExtent():" + computeVerticalScrollExtent());

        if (mDY < 0) {
            if (params.topMargin < marginMax) {
                params.topMargin -= mDY;
            }
        }

        Log.i("onTouchEvent", "params.topMargin:" + params.topMargin);

        if (params.topMargin > marginMax) {
            params.topMargin = marginMax;
        }

        params.leftMargin = marginMax / marginSideCoeff - params.topMargin / marginSideCoeff;
        params.rightMargin = marginMax / marginSideCoeff - params.topMargin / marginSideCoeff;

        Log.i("onTouchEvent", "params.topMargin:" + params.topMargin + "params.leftMargin:" + params.leftMargin + "params.rightMargin:" + params.rightMargin);

        setLayoutParams(params);

    }

    @Override
    public boolean onDown(MotionEvent event) {
        Log.d("CustomList","onDown: " + event.toString());
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           final float velocityX, final float velocityY) {
        Log.d("CustomList", "onFling: " + velocityX + " " + velocityY);

        final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();

        Animation a = new Animation() {

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {

                Log.i("CustomList", "interpolatedTime:" + interpolatedTime);

                if(velocityY < 0) {
                    params.topMargin += velocityY / 100;

                    if (params.topMargin < marginMin) {
                        params.topMargin = marginMin;
                    }

                    params.leftMargin -= velocityY / 100 / marginSideCoeff;
                    params.rightMargin -= velocityY / 100 / marginSideCoeff;

                    if (params.leftMargin > (marginMax - marginMin) / marginSideCoeff) {
                        params.leftMargin = (marginMax - marginMin) / marginSideCoeff;
                    }

                    if (params.rightMargin > (marginMax - marginMin) / marginSideCoeff) {
                        params.rightMargin = (marginMax - marginMin) / marginSideCoeff;
                    }
                }
                else {

                    params.topMargin += velocityY / 100;

                    if (params.topMargin > marginMax) {
                        params.topMargin = marginMax;
                    }

                    params.leftMargin -= velocityY / 100 / marginSideCoeff;
                    params.rightMargin -= velocityY / 100 / marginSideCoeff;

                    if (params.leftMargin < 0) {
                        params.leftMargin = 0;
                    }

                    if (params.rightMargin < 0) {
                        params.rightMargin = 0;
                    }
                }

                setLayoutParams(params);
            }
        };
        a.setDuration(500); // in ms
        startAnimation(a);

        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        Log.d("CustomList", "onLongPress: " + event.toString());
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        Log.d("CustomList", "onScroll: " + e1.toString()+e2.toString());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
        Log.d("CustomList", "onShowPress: " + event.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        Log.d("CustomList", "onSingleTapUp: " + event.toString());
        return true;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        this.mDetector.onTouchEvent(ev);

        boolean can_scroll = true;

        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            if (ev.getHistorySize() > 0) {
                float hy = ev.getHistoricalY(0);
                float y = ev.getY();
                mDY = hy - y;

                if (mDY > 0) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();

                    if (params.topMargin > marginMin) {
                        params.topMargin -= mDY;
                        can_scroll = false;
                    }

                    if (params.topMargin < marginMin) {
                        params.topMargin = marginMin;
                    }

                    params.leftMargin = marginMax / marginSideCoeff - params.topMargin / marginSideCoeff;
                    params.rightMargin = marginMax / marginSideCoeff - params.topMargin / marginSideCoeff;
                    setLayoutParams(params);
                }
            }
        }

        if (can_scroll)
        {
            return super.onTouchEvent(ev);
        }

        return true;
    }

    public void setMargins(int min, int max, int side)
    {
        marginMin = min;
        marginMax = max;
        marginSideCoeff = side;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int c = getCount();
        if(c != mCount)
        {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
            params.topMargin = marginMax;
            params.leftMargin = marginMax / marginSideCoeff - params.topMargin/marginSideCoeff;
            params.rightMargin = marginMax / marginSideCoeff - params.topMargin/marginSideCoeff;
            setLayoutParams(params);

            mCount = c;
        }
    }
}
