package eu.sstefanov.keyfinder;

import android.app.Activity;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.commonsware.cwac.camera.CameraFragment;
import com.commonsware.cwac.camera.SimpleCameraHost;

import java.util.List;
import java.util.UUID;

/**
 * Created by sstefanov on 27-Apr-15.
 */
public class PictureActivity extends Activity {

    private final static String TAG = PictureActivity.class.getSimpleName();

    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";

    private BluetoothLeService mBluetoothLeService;
    private String mDeviceAddress;
    private String mDeviceName;
    
    public final static UUID UUID_ACTION_BUTTON =
            UUID.fromString(SampleGattAttributes.ACTION_BUTTON_CHARACTERISTIC);

    public final static UUID UUID_ACTION_SERVICE =
            UUID.fromString(SampleGattAttributes.ACTION_SERVICE);

    private final String TAG_CAMERA_FRAGMENT = "camera_fragment";


    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
//                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDeviceAddress);

            // Listen for action button
            BluetoothGattCharacteristic characteristic =
                    mBluetoothLeService.getCharacteristic(UUID_ACTION_SERVICE, UUID_ACTION_BUTTON);

            if (characteristic != null) {

                mBluetoothLeService.setCharacteristicNotification(characteristic, true);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    // Handles various events fired by the Service.
    // ACTION_GATT_CONNECTED: connected to a GATT server.
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read
    //                        or notification operations.
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();


            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
//                mConnected = true;
//                updateConnectionState(R.string.connected);
                invalidateOptionsMenu();
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
//                mConnected = false;
//                updateConnectionState(R.string.disconnected);
                invalidateOptionsMenu();
//                clearUI();
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
//                displayGattServices(mBluetoothLeService.getSupportedGattServices());
                activateActionData(mBluetoothLeService.getSupportedGattServices());
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
//                displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
                takePicture();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture);

        final Intent intent = getIntent();
        mDeviceName = intent.getStringExtra(EXTRAS_DEVICE_NAME);
        mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);

//        mDeviceAddress = "18:7A:93:02:86:8C";
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);

        if (bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE)) {

            if (mBluetoothLeService != null) {
                mBluetoothLeService.connect(mDeviceAddress);
            }
        }

//        pic = (ImageView)findViewById(R.id.imageView1);
//        cameraObject = isCameraAvailiable();
//        showCamera = new ShowCamera(this, cameraObject);
//        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
//        preview.addView(showCamera);

        //Create the CameraFragment and add it to the layout
        CameraFragment f = new CameraFragment();
        getFragmentManager().beginTransaction()
                .add(R.id.camera_preview, f, TAG_CAMERA_FRAGMENT)
                .commit();

        //Set the CameraHost
        f.setHost(new SimpleCameraHost(this));

//        findViewById(R.id.button_capture).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                takePicture();
//            }
//        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mGattUpdateReceiver,  makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
            Log.d(TAG, "Connect request result=" + result);

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mGattUpdateReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }

    /**
     * Set notification for button click to true.
     * @param gattServices
     */
    public void activateActionData(List<BluetoothGattService> gattServices) {

        boolean isActivated = false;

        for (BluetoothGattService gattService : gattServices) {
            List<BluetoothGattCharacteristic> gattCharacteristics =
                    gattService.getCharacteristics();

            for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                if (UUID_ACTION_BUTTON.equals(gattCharacteristic.getUuid())) {
                    mBluetoothLeService.setCharacteristicNotification(
                            gattCharacteristic, true);
                    isActivated = true;
                    break;
                }
            }

            if (isActivated) {
                break;
            }
        }
    }

    /**
     * Handel button capture click event.
     * @param view
     */
    public void takePicture(View view) {
        takePicture();
    }
    /**
     * Checks that the CameraFragment exists and is visible to the user,
     * then takes a picture.
     */
    private void takePicture() {
        CameraFragment f = (CameraFragment) getFragmentManager().findFragmentByTag(TAG_CAMERA_FRAGMENT);
        if (f != null && f.isVisible()) {
//            f.autoFocus();
            f.takePicture();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }
}
