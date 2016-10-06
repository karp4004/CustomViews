package com.example.okarpov.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
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

    float maxSpeed = 240.f;
    float itemVal2 = 5.f;
    float itemVal3 = itemVal2*2/ 20.f;
    float itemVal4 = itemVal2*5/ 20.f;
    float mDegrees = 270;
    int speedItem = 0;
    int mValue = 0;
    float mSpeedToDeg = 0.f;
    int mCount = 54;

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

        float weight = mDegrees/mCount;

        for(float i=0;i<=mDegrees;i+=weight) {
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
                item.setRotation(i - (90 - (360 - mDegrees)/2));

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

                if(idx == 0 || idx==mCount || (idx - 2)%itemVal2==0)
                {
                    FrameLayout.LayoutParams par = new
                            FrameLayout.LayoutParams((int)getResources().getDimension(R.dimen.speed7),
                            (int)getResources().getDimension(R.dimen.speed5));
                    par.gravity = Gravity.CENTER_VERTICAL;

                    item_head.setLayoutParams(par);

                    item_head.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_selected2));

                    TextView txtVal = (TextView)v.findViewById(R.id.txtVal);
                    if(txtVal != null && idx != 0 && idx!=mCount)
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

//        Ratio1 = (float)(count-4)/(maxSpeed - minSpeed);
//        Ratio2 = 2/minSpeed;
    }

    public void setValue(int v)
    {
        if( v < 20)
        {
            mSpeedToDeg = v*itemVal3;
        }
        else if(v > 220)
        {
            mSpeedToDeg = 20*itemVal3 + (200)*itemVal4 + (v-220)*itemVal3;
        }
        else
        {
            mSpeedToDeg = 20*itemVal3 + (v-20)*itemVal4;
        }

        float speddtodeg = mSpeedToDeg - (90 - (360 - mDegrees)/2);

        View item = getChildAt(speedItem);
        if(item != null)
        {
            if(item.getRotation() > speddtodeg)
            {
                for(int i=speedItem;i>=0;i--)
                {
                    View ch = getChildAt(i);
                    if(ch != null) {
                        if(ch.getRotation() <= speddtodeg)
                        {
                            speedItem = i;
                            break;
                        }
                    }
                }
            }
            else if(item.getRotation() < speddtodeg)
            {
                for(int i=speedItem;i<getChildCount();i++)
                {
                    View ch = getChildAt(i);
                    if(ch != null) {
                        if(ch.getRotation() >= speddtodeg)
                        {
                            speedItem = i;
                            break;
                        }
                    }
                }
            }

            if(speedItem < getChildCount() - 1)
            {
                if(v > 0)
                {
                    speedItem++;
                }
            }
        }

        Log.i("setValue", "v:" + v + " speedItem:" + speedItem);// + "r:" + r);

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

//            mPaint.setColor(colorBg);
//            mPaint.setStrokeWidth(mStrokeWidthBg);
//            canvas.drawArc(mArcRect, 0, 360, false, mPaint);

        float offset = 360 - mDegrees;

        mPaint.setShader(null);
        mPaint.setStrokeWidth((int)(mStrokeWidthArc/2));

        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(mArcRect, 90 + offset/2, mDegrees, false, mPaint);

        float degrees = mSpeedToDeg;
        double x = mArcRect.centerX() + Math.cos(Math.toRadians(degrees + (90 + offset/2)))*mArcRect.width()/2.f;
        double y = mArcRect.centerY() + Math.sin(Math.toRadians(degrees + (90 + offset/2)))*mArcRect.height()/2.f;
        double vx = x - mArcRect.centerX();
        double vy = y - mArcRect.centerX();

        int div = 3;
        mPaint.setShader(new LinearGradient((int)x, (int)y, (int)x + (int)vx/div, (int)y + (int)vy/div,
                Color.RED, Color.TRANSPARENT, Shader.TileMode.MIRROR));
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        float d = degrees - 25;
        if(d > 0) {
            float maxover = degrees + 25 - mDegrees;
            maxover = maxover>0?maxover:0;
            canvas.drawArc(mArcRect, degrees - 25 + (90 + offset/2), 50 - maxover, false, mPaint);
        }
        else
        {
            canvas.drawArc(mArcRect, (90 + offset/2), 50 + d, false, mPaint);
        }

        mPaint.setShader(null);

        Log.i("onDrawSegment", "x:" + x + " y:" + y + " degrees:" + degrees);

        mPaint.setStyle(Paint.Style.FILL);

        mPaint.setShader(new RadialGradient((int)x, (int)y + 6,
                mStrokeWidthArc * 1.3f, Color.argb(255,150,150,150), Color.TRANSPARENT, Shader.TileMode.CLAMP));
        canvas.drawCircle((int)x, (int)y + 6, mStrokeWidthArc * 1.3f, mPaint);

        mPaint.setShader(new RadialGradient((int)x, (int)y-10,
                mStrokeWidthArc*8, Color.argb(255,230,0,0), Color.BLACK, Shader.TileMode.CLAMP));
        canvas.drawCircle((int)x, (int)y, mStrokeWidthArc, mPaint);
    }

    protected void setArcRect() {
        int height = (int)((float)getHeight()/1.6f);
        int width = (int)((float)getWidth()/1.6f);
        int radius = height/2 - (int)(mStrokeWidthArc/2.f);//mStrokeWidthBg - mStrokeWidthBg/6;
        mArcRect.set(width/2- radius + getPaddingLeft(),
                height/2 - radius + getPaddingTop(),
                width/2 + radius - getPaddingRight(),
                height/2 + radius - getPaddingBottom());

        mArcRect.offset((getWidth() - width)/2, (getHeight() - height)/2);

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
