package com.example.okarpov.customviews;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.CountDownTimer;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by okarpov on 7/25/2016.
 */
public class CustomScrollView extends ScrollView implements
        GestureDetector.OnGestureListener {

    public interface Interface
    {
        void onOverscroll(int scrollX, int scrollY, boolean clampedX, boolean clampedY);
        void onSizeChanged(int w, int h, int oldw, int oldh);
        void onTouchEvent(MotionEvent ev, float dy);
        void onScrollEnd();
    }



    private GestureDetectorCompat mDetector;
    Interface mInterface;
    Context mContext;

    float mDY = 0.f;
    float mCurrentY = 0.f;
    float mCurrentScaleX = 1.f;
    float marginMax;
    float marginMin = 0;
    float scaleMin = 0.9f;
    float scaleMax = 1.0f;
    float scaleSpeed = 0.0002f;
    int animateDuration = 100   ;
    int animateDurationCont = 100;
    View mAnimated = null;
    CountDownTimer mScrollWatch;

    public void setAnimated(View v)
    {
        mAnimated = v;
    }

    public void setInterface(Interface i ){
        mInterface = i;
    }

    public CustomScrollView(Context context)
    {
        super(context);
        init(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context)
    {
        mDetector = new GestureDetectorCompat(context,this);
        mContext = context;

        setVerticalScrollBarEnabled(false);

//        setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//
//                Log.i("onItemClick", "position:" + position);
//
//                mPosition = position;
//
//                for(int i=0;i<getChildCount();i++)
//                {
//                    View v = getChildAt(i);
//                    View o = v.findViewById(R.id.viewObscure);
//                    if(i == mPosition)
//                    {
//                        o.setVisibility(INVISIBLE);
//                    }
//                    else
//                    {
//                        o.setVisibility(VISIBLE);
//                    }
//                }
//            }
//        });

        mScrollWatch = new CountDownTimer(300, 300) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                if(mInterface != null)
                    mInterface.onScrollEnd();
            }
        };
    }

    void animateContinious(float coeff)
    {
        if(mAnimated != null) {
            float target = mCurrentY - mDY * coeff;
            if (target < marginMin) {
                target = marginMin;
            } else if (target > marginMax) {
                target = marginMax;
            }

            float startOffset = mCurrentY;
            float endOffset = target;

            float targetsc = mCurrentScaleX - mDY * scaleSpeed;
            if (targetsc < scaleMin) {
                targetsc = scaleMin;
            } else if (targetsc > scaleMax) {
                targetsc = scaleMax;
            }

            float startX = mCurrentScaleX;
            float endX = targetsc;

            ObjectAnimator mover = ObjectAnimator.ofFloat(mAnimated,
                    "y", startOffset, endOffset);
            mover.setDuration(animateDurationCont);

            ObjectAnimator mover2 = ObjectAnimator.ofFloat(mAnimated,
                    "scaleX", startX, endX);
            mover.setDuration(animateDurationCont);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.play(mover).with(mover2);//.with(fadeIn).after(fadeOut);
            animatorSet.start();

            mCurrentY = target;
            mCurrentScaleX = targetsc;
        }
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);

        if(mDY < 0)
        {
            animateContinious(1.0f);
        }

        if(mCurrentY == 0 || mCurrentY == marginMax) {
            if (mInterface != null)
                mInterface.onOverscroll(scrollX, scrollY, clampedX, clampedY);
        }
    }

    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           final float velocityX, final float velocityY) {

        if(mAnimated != null) {
            if (Math.abs(velocityY) >= 1000) {
                if (velocityY < 0 || getScrollY() == 0) {
                    float target = mCurrentY + velocityY / 20;
                    if (target < marginMin) {
                        target = marginMin;
                    } else if (target > marginMax) {
                        target = marginMax;
                    }

                    float startOffset = mCurrentY;
                    float endOffset = target;//velocityY < 0 ? marginMin : marginMax;

                    float targetsc = mCurrentScaleX + velocityY / 150000;
                    if (targetsc < scaleMin) {
                        targetsc = scaleMin;
                    } else if (targetsc > scaleMax) {
                        targetsc = scaleMax;
                    }

                    float startX = mCurrentScaleX;
                    float endX = targetsc;//velocityY < 0 ? scaleMin : scaleMax;

                    ObjectAnimator mover = ObjectAnimator.ofFloat(mAnimated,
                            "y", startOffset, endOffset);
                    mover.setDuration(animateDuration);

                    ObjectAnimator mover2 = ObjectAnimator.ofFloat(mAnimated,
                            "scaleX", startX, endX);
                    mover.setDuration(animateDuration);

                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.play(mover).with(mover2);//.with(fadeIn).after(fadeOut);
                    animatorSet.start();

                    mCurrentY = target;
                    mCurrentScaleX = targetsc;
                }
            }
        }

        return true;
    }

    @Override
    protected void onScrollChanged(int l, final int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        Log.i("onScrollChanged", "t:" + t + " oldt:" + oldt);

        mScrollWatch.start();
    }

    @Override
    public void onLongPress(MotionEvent event) {
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        return true;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        Log.i("onTouchEvent", "ev:" + ev);

        Log.i("Car", "scroll:" + getScrollY());

        this.mDetector.onTouchEvent(ev);

        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            if (ev.getHistorySize() > 0) {
                float hy = ev.getHistoricalY(0);
                float y = ev.getY();
                mDY = hy - y;

                if(mDY > 0)
                {
                    animateContinious(1.0f);
                }
            }
        }

        if(mInterface != null)
            mInterface.onTouchEvent(ev, mDY);

        if(mCurrentY >= marginMax || mCurrentY <= 0 || mDY < 0) {
            return super.onTouchEvent(ev);
        }

        return true;
    }

    public void setMargins(float min, float max, float minsc, float maxsc)
    {
        marginMin = min;
        marginMax = max;
        scaleMin = minsc;
        scaleMax = maxsc;
        mCurrentY = marginMax;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if(mInterface != null)
            mInterface.onSizeChanged(w, h, oldw, oldh);

    }

    public void add()
    {
        inflate(mContext, R.layout.customlist_item, this);
    }

    public void clear()
    {
        removeAllViews();
    }
}
