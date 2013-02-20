package no.group09.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import no.group09.arduinoair.R;
import no.group09.fragments.BluetoothDeviceAdapter;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Devices extends Activity{

	private String TAG = "DEVICES";

	private ListView tv;
	private BluetoothDeviceAdapter adapter;
	private static final int REQUEST_ENABLE_BT = 1;
	private BluetoothAdapter btAdapter; 
	private ArrayList<BluetoothDevice> btDeviceList = new ArrayList<BluetoothDevice>();
	ArrayList<HashMap<String, String>> category_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.devices);

		//Register the BroadcastReceiver
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		filter.addAction(BluetoothDevice.ACTION_UUID);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		registerReceiver(ActionFoundReceiver, filter); // Don't forget to unregister during onDestroy

		// Getting the Bluetooth adapter
		btAdapter = BluetoothAdapter.getDefaultAdapter();

		category_list = new ArrayList<HashMap<String, String>>();


		tv = (ListView) findViewById(R.id.bluetooth_devices_list);


		// Getting adapter by passing xml data ArrayList
		adapter = new BluetoothDeviceAdapter(getBaseContext(), category_list);        
		tv.setAdapter(adapter);

		// Click event for single list row/
		tv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(view.getContext(), "You hit the button", Toast.LENGTH_SHORT).show();
			}
		});	
		
		CheckBTState();
	}

	/* This routine is called when an activity completes.*/
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_ENABLE_BT) {
			CheckBTState();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (btAdapter != null) {
			btAdapter.cancelDiscovery();
		}
		unregisterReceiver(ActionFoundReceiver);
	}

	private void CheckBTState() {
		// Check for Bluetooth support and then check to make sure it is turned on
		// If it isn't request to turn it on
		// List paired devices
		// Emulator doesn't support Bluetooth and will return null
		if(btAdapter==null) { 
			Log.d(TAG, "\nBluetooth NOT supported. Aborting.");
			return;
		} else {
			if (btAdapter.isEnabled()) {
				Log.d(TAG, "\nBluetooth is enabled...");

				// Starting the device discovery
				btAdapter.startDiscovery();
			} else {
				Intent enableBtIntent = new Intent(btAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
			}
		}
	}
	private final BroadcastReceiver ActionFoundReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if(BluetoothDevice.ACTION_FOUND.equals(action)) {
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				Log.d(TAG, "\n  Device: " + device.getName() + ", " + device);
				btDeviceList.add(device);
				
				//Adding found device
				HashMap<String, String> map = new HashMap<String, String>();
				map = new HashMap<String, String>();
				map.put("element", device.getName() + ", " + device);
				category_list.add(map);
				
				adapter.notifyDataSetChanged();
//			} else {
//				if(BluetoothDevice.ACTION_UUID.equals(action)) {
//					BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//					Parcelable[] uuidExtra = intent.getParcelableArrayExtra(BluetoothDevice.EXTRA_UUID);
//					for (int i=0; i<uuidExtra.length; i++) {
//						Log.d(TAG, "\n  Device: " + device.getName() + ", " + device + ", Service: " + uuidExtra[i].toString());
//					}
//				} else {
//					if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
//						Log.d(TAG, "\nDiscovery Started...");
//					} else {
//						if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
//							Log.d(TAG, "\nDiscovery Finished");
//							Iterator<BluetoothDevice> itr = btDeviceList.iterator();
//							while (itr.hasNext()) {
//								// Get Services for paired devices
//								BluetoothDevice device = itr.next();
//								Log.d(TAG, "\nGetting Services for " + device.getName() + ", " + device);
//							
//								HashMap<String, String> map = new HashMap<String, String>();
//								map = new HashMap<String, String>();
//								map.put("element", device.getName() + ", " + device);
//								category_list.add(map);
//								
//								adapter.notifyDataSetChanged();
////								if(!device.fetchUuidsWithSdp()) {
////									Log.d(TAG, "\nSDP Failed for " + device.getName());
////								}
//
//							}
//							
//						}
//					}
//				}
			}
		}
	};
}
