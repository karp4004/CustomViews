package com.example.okarpov.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * Created by okarpov on 7/25/2016.
 */
public class CustomList extends ListView {

    int marginMin;
    int marginMax;
    int marginSideCoeff;
    int mCount = 0;
    int mVerticalOffset = 0;
    float mDY = 0;

    public CustomList(Context context)
    {
        super(context);
    }

    public CustomList(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public CustomList(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
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
    public boolean onTouchEvent(MotionEvent ev) {

        int dvertical = computeVerticalScrollOffset() - mVerticalOffset;
        mVerticalOffset = computeVerticalScrollOffset();

        Log.i("onTouchEvent", "dvertical:" + dvertical);

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
            params.leftMargin = marginMax/marginSideCoeff - params.topMargin/marginSideCoeff;
            params.rightMargin = marginMax/marginSideCoeff - params.topMargin/marginSideCoeff;
            setLayoutParams(params);

            mCount = c;
        }
    }
}
