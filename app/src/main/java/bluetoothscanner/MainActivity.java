package bluetoothscanner;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.okarpov.customviews.R;

public class MainActivity extends AppCompatActivity {

    private BluetoothLeScanner mScanner;
    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScanner = new BluetoothLeScanner(this);

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
                }
            }
        };

        final IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy    private static final UUID MY_UUID_SECURE =

        mScanner.getBluetoothAdapter().cancelDiscovery();
        mScanner.getBluetoothAdapter().startDiscovery();

        new CountDownTimer(10000, 10000)
        {
            @Override
            public void onFinish() {

                mScanner.getBluetoothAdapter().cancelDiscovery();
                mScanner.getBluetoothAdapter().startDiscovery();

                start();
            }

            @Override
            public void onTick(long l) {

            }
        }.start();

        mScanner.scanLeDevice(-1, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mReceiver);
    }
}
