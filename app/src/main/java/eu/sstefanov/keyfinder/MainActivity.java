package eu.sstefanov.keyfinder;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
// import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        TextView addressView = (TextView) findViewById(R.id.deviceAddress);
        addressView.setText(BluetoothLeService.DEVICE_ADDRESS);

        TextView nameView = (TextView) findViewById(R.id.deviceName);
        nameView.setText(BluetoothLeService.DEVICE_NAME);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void findDevice(View view){
        Intent intent = new Intent(this, DeviceScanActivity.class);
        startActivity(intent);
    }

    public void takePicture(View view){
        Intent intent = new Intent(this, PictureActivity.class);
        startActivity(intent);
    }

}
