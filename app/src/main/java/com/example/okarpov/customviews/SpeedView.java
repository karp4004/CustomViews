package com.example.okarpov.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
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
    float Speed = 0;
    float maxSpeed = 220.f;
    float minSpeed = 20.f;
    float Ratio1 = 1.0f;
    float Ratio2 = 1.0f;

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
        this.setWillNotDraw(false);

        mStrokeWidthArc = context.getResources().getDimension(R.dimen.speed5);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //5.1428571428571428571428571428571 - one
        //41.142857142857142857142857142857 - one side
        int count = 54;
        float end = 277.71428571428571428571428571429f;
        float weight = end/count;

        for(float i=0;i<=end;i+=weight) {
            View v = inflater.inflate(getResources().getLayout(R.layout.speed_item), null);
            addView(v);

            FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(
                    (int)getResources().getDimension(R.dimen.speed3),
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            p.gravity = Gravity.CENTER;

            v.setLayoutParams(p);

            View item = v.findViewById(R.id.item);
            if(item != null)
            {
                item.setRotation(i - (90 - (360 - end)/2));

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

                if(idx == 0 || idx==count || (idx - 2)%5==0)
                {
                    FrameLayout.LayoutParams par = new
                            FrameLayout.LayoutParams((int)getResources().getDimension(R.dimen.speed7),
                            (int)getResources().getDimension(R.dimen.speed5));
                    par.gravity = Gravity.CENTER_VERTICAL;

                    item_head.setLayoutParams(par);

                    item_head.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_selected2));

                    TextView txtVal = (TextView)v.findViewById(R.id.txtVal);
                    if(txtVal != null && idx != 0 && idx!=count)
                    {
                        txtVal.setText("" + (idx+3)*4);
                        txtVal.setRotation(-item.getRotation());
                    }
                }
                else
                {
                    item_head.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_selected));
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

        Ratio1 = (float)(count-4)/(maxSpeed - minSpeed);
        Ratio2 = 2/minSpeed;
    }

    public void setValue(int v)
    {
        float r = v<20?Ratio2:Ratio1;

        int speedItem = Math.round(((float)v)*r);
        speedItem = v<20?speedItem:speedItem - 2;

        Log.i("setValue", "v:" + v + " speedItem:" + speedItem + "r:" + r);

        if(mValue > speedItem)
        {
            for(int i=mValue;i>=speedItem;i--)
            {
                View ch = getChildAt(i);
                if(ch != null) {
                    View item_head = ch.findViewById(R.id.item_head);
                    if (item_head != null) {
                        int idx = indexOfChild(ch);
                        if(idx == 0 || idx==getChildCount()-1 || (idx - 2)%5==0) {
                            item_head.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_selected2));
                        }
                        else
                        {
                            item_head.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_selected));
                        }
                    }
                }
            }
        }
        else {
            for (int i = mValue; i < speedItem; i++) {
                View ch = getChildAt(i);
                if(ch != null) {
                    View item_head = ch.findViewById(R.id.item_head);
                    if (item_head != null) {
                        item_head.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_selected3));
                    }
                }
            }
        }

        mValue = speedItem;

        invalidate();
    }

    protected final Paint mPaint = new Paint();
    protected final RectF mArcRect = new RectF();
    protected boolean rectSet = false;
    protected float mStrokeWidthArc;

    protected void onDrawSegment(Canvas canvas)
    {
        if (!rectSet) {
            setArcRect();
        }

        mPaint.setShader(null);

        if (40 >= 0) {
//            mPaint.setColor(colorBg);
//            mPaint.setStrokeWidth(mStrokeWidthBg);
//            canvas.drawArc(mArcRect, 0, 360, false, mPaint);

            int offset = 37;


            mPaint.setStrokeWidth((int)(mStrokeWidthArc/2));

            int degrees = (int)maxSpeed * (360 - offset*2) / (int)maxSpeed;
            mPaint.setColor(Color.BLACK);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawArc(mArcRect, 90 + offset, degrees, false, mPaint);

            degrees = (int)40 * (360 - offset*2) / (int)maxSpeed;
            mPaint.setColor(Color.RED);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawArc(mArcRect, 90 + offset, degrees, false, mPaint);

            double x = mArcRect.centerX() + Math.cos(Math.toRadians(degrees + (90 + offset)))*mArcRect.width()/2.f;
            double y = mArcRect.centerY() + Math.sin(Math.toRadians(degrees + (90 + offset)))*mArcRect.height()/2.f;

            Log.i("onDrawSegment", "x:" + x + " y:" + y);

            mPaint.setColor(Color.BLACK);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle((int)x, (int)y, mStrokeWidthArc/2, mPaint);

            mPaint.setShader(new RadialGradient((int)x, (int)y,
                    mStrokeWidthArc, Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP));
            canvas.drawCircle((int)x, (int)y, mStrokeWidthArc, mPaint);

//            double endX = Math.cos(Math.toRadians(degrees + (90 + offset))) * radius + mArcRect.centerX();
//
//            double endY = Math.sin(Math.toRadians(degrees + (90 + offset))) * radius + mArcRect.centerY();
//
//            canvas.drawBitmap(mBmp, (int)endX, (int)endY, mPaint);

            //canvas.drawCircle((int)endX, (int)endY, 30, mPaint);

        }
    }

    protected void setArcRect() {
        int height = getHeight()/2;
        int width = getWidth()/2;
        int radius = height/2 - (int)(mStrokeWidthArc/2.f);//mStrokeWidthBg - mStrokeWidthBg/6;
        mArcRect.set(width/2- radius + getPaddingLeft(),
                height/2 - radius + getPaddingTop(),
                width/2 + radius - getPaddingRight(),
                height/2 + radius - getPaddingBottom());

        mArcRect.offset(width/2, height/2);

        rectSet = true;
    }

    public float getMaxSpeed()
    {
        return maxSpeed;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Log.i("onDrawSegment", "onDraw");

        super.onDraw(canvas);

        onDrawSegment(canvas);
    }
}
