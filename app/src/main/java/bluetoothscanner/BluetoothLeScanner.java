package bluetoothscanner;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;

public class BluetoothLeScanner {

    public interface Interface {
        void onNewDevice(BluetoothDevice device);
    }

    private final Handler mHandler;
    private final BluetoothAdapter.LeScanCallback mLeScanCallback;
    private BroadcastReceiver mReceiver;

    private boolean mScanning;
    public final static int REQUEST_ENABLE_BT = 2001;
    private final Activity mActivity;
    private final BluetoothAdapter mBluetoothAdapter;

    private CountDownTimer mDiscovery;
    private CountDownTimer mBlueTooth;

    boolean mStopped = false;

    public BluetoothLeScanner(final Activity activity, final Interface i) {
        mHandler = new Handler();

        mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
            @Override
            public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
                Log.i("devices", "found le device:" + device.getName() + " " + device.getAddress());

                if(i != null)
                {
                    i.onNewDevice(device);
                }
            }
        };

        // Create a BroadcastReceiver for ACTION_FOUND
        mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {

                String action = intent.getAction();

                Log.i("devices", "onReceive:" + action);

                // When discovery finds a device
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    // Get the BluetoothDevice object from the Intent
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                    Log.i("devices", "found device:" + device.getName() + " " + device.getAddress());

                    if(i != null)
                    {
                        i.onNewDevice(device);
                    }
                }
            }
        };

        mActivity = activity;
        final BluetoothManager btManager = (BluetoothManager) mActivity.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = btManager.getAdapter();

        mDiscovery = new CountDownTimer(20000, 20000) {
            @Override
            public void onFinish() {

                if(!mStopped) {
                    if (!isBluetoothOn()) {
                        askUserToEnableBluetoothIfNeeded();
                    } else {
                        getBluetoothAdapter().cancelDiscovery();
                        getBluetoothAdapter().startDiscovery();
                    }

                    start();
                }
            }

            @Override
            public void onTick(long l) {

            }
        };

        mBlueTooth = new CountDownTimer(1000, 1000) {
            @Override
            public void onFinish() {

                if(!mStopped) {
                    if (!isBluetoothOn()) {
                        askUserToEnableBluetoothIfNeeded();
                    } else {
                        start();
                    }
                }
            }

            @Override
            public void onTick(long l) {
            }
        };
    }

    public void startScan()
    {
        mStopped = false;

        if (!isBluetoothOn()) {
            askUserToEnableBluetoothIfNeeded();
        }
        else
        {
            scanLeDevice();
        }
    }

    public void stopScan()
    {
        mStopped = true;

        getBluetoothAdapter().cancelDiscovery();
        getBluetoothAdapter().stopLeScan(mLeScanCallback);

        mDiscovery.cancel();
        mBlueTooth.cancel();

        mActivity.unregisterReceiver(mReceiver);
    }

    public void scanLeDevice() {

        final IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        mActivity.registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy    private static final UUID MY_UUID_SECURE =

        getBluetoothAdapter().cancelDiscovery();
        getBluetoothAdapter().stopLeScan(mLeScanCallback);

        getBluetoothAdapter().startDiscovery();
        getBluetoothAdapter().startLeScan(mLeScanCallback);
        mDiscovery.start();
        mBlueTooth.start();
    }

    public void askUserToEnableBluetoothIfNeeded() {
        if (isBluetoothLeSupported() && (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled())) {
            final Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            mActivity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return mBluetoothAdapter;
    }

    public boolean isBluetoothLeSupported() {
        return mActivity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
    }

    public boolean isBluetoothOn() {
        return mBluetoothAdapter != null && mBluetoothAdapter.isEnabled();
    }
}
