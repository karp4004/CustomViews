package com.example.okarpov.customviews;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.concurrent.Delayed;

import bluetoothscanner.BluetoothLeScanner;

public class MainActivity extends AppCompatActivity {

    BluetoothLeScanner mScanner;

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
        setContentView(R.layout.group_statistic_diagram);
    }

    void testSpeed()
    {
        setContentView(R.layout.activity_main);

        final SpeedView sv = (SpeedView)findViewById(R.id.speedView);
        SeekBar sb = (SeekBar)findViewById(R.id.seekBar);
        if(sb != null)
        {
            sb.setMax((int)sv.getMaxSpeed());
            sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {

                    Handler h = new Handler();
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            sv.setValue(progress);
                        }
                    });
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }

        final TextView tv = (TextView)findViewById(R.id.textView2);
        sv.setInterface(new SpeedView.Iterface(){
            @Override
            public void onSpeedChanged(int speed) {
                tv.setText("" + speed);
            }
        });
    }

    void testBlueTooth()
    {
        setContentView(R.layout.activity_main);

        mScanner = new BluetoothLeScanner(this, new BluetoothLeScanner.Interface(){
            @Override
            public void onNewDevice(BluetoothDevice device) {
                if(device != null && device.getName() != null && device.getName().equals("eZWay-Android"))
                {
                    mScanner.stopScan();

                    Log.i("onNewDevice", "" + device.getName());
                }
            }
        });

        mScanner.startScan();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_test);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("devices", "requestCode:" + requestCode + " resultCode:" + resultCode + " data:" + data);

        mScanner.scanLeDevice();
    }

    public void btn1(View view)
    {
        testSpeed();
    }

    public void btn2(View view)
    {
        testBlueTooth();
    }

    public void btn3(View view)
    {
        testGroup();
    }

    public void btn4(View view)
    {
        testList();
    }
}
