package no.group09.utils;

/*
 * Licensed to UbiCollab.org under one or more contributor
 * license agreements.  See the NOTICE file distributed 
 * with this work for additional information regarding
 * copyright ownership. UbiCollab.org licenses this file
 * to you under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeoutException;

import no.group09.arduinoair.R;
import no.group09.connection.BluetoothConnection;
import no.group09.connection.ConnectionListener;
import no.group09.connection.ConnectionMetadata;
import no.group09.connection.ConnectionMetadata.DefaultServices;
import no.group09.fragments.BluetoothDeviceAdapter;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View.OnClickListener;

/**
 * This class searches for the BT devices in range and put them in a list
 */
public class Devices extends Activity{

	SharedPreferences sharedPref = null;
	
	private static final String TAG = "DEVICES";
	private static final boolean LIST_NON_ARDUINO_DEVICES = true;
	private static final int REQUEST_ENABLE_BT = 1;
	private ListView tv;
	private BluetoothDeviceAdapter adapter;
	private BluetoothAdapter btAdapter; 
	private ArrayList<HashMap<String, String>> category_list;
	private IntentFilter filter;
	private Button refresh;
	private ProgressBar progressBar;
	private ArrayList<HashMap<String, String>> device_list;
	private boolean alreadyChecked = false;
	private ArrayList<BluetoothDevice> btDeviceList = new ArrayList<BluetoothDevice>();

	private BluetoothConnection con;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		//Set the xml layout
		setContentView(R.layout.devices);	

		//Fetching the shared preferences object used to write the preference file
		sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		//progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		progressBar = (ProgressBar) findViewById(R.id.progbar);

		//Register the BroadcastReceiver
		filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		filter.addAction(BluetoothDevice.ACTION_UUID);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

		//Registers the BT receiver with the requested filters
		//Don't forget to unregister during onDestroy
		registerReceiver(ActionFoundReceiver, filter);

		// Getting the Bluetooth adapter
		btAdapter = BluetoothAdapter.getDefaultAdapter();

		category_list = new ArrayList<HashMap<String, String>>();

		// Getting adapter by passing xml data ArrayList
		adapter = new BluetoothDeviceAdapter(getBaseContext(), category_list);        

		//List of devices
		device_list = new ArrayList<HashMap<String, String>>();

		//Get the xml to how the list is structured
		tv = (ListView) findViewById(R.id.bluetooth_devices_list);

		//Get the adapter for the device list
		adapter = new BluetoothDeviceAdapter(getBaseContext(), device_list);        

		//Set the adapter
		tv.setAdapter(adapter);

		//Click event for single list row
		tv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//				Toast.makeText(view.getContext(), "You clicked on: " + adapter.getName(position), Toast.LENGTH_SHORT).show();

				// Handle successful scan
				try {
					con = new BluetoothConnection(adapter.getMacAddress(position), 
							(Activity)view.getContext(), getConnectionListener());
					con.connect();
					
					String lastConnectedDevice = "Device name: " + adapter.getName(position)
							+ "\nMAC Address: " + adapter.getMacAddress(position);
					
					Editor edit = sharedPref.edit();
					
					//Saves the information about the last connected device to sharedPreferences
					edit.putString("connected_device_dialog", lastConnectedDevice);
					
					edit.commit();
					Log.d(TAG, "The information about the last connected device was written to shared preferences");
					Toast.makeText(view.getContext(), "SUCCESS", Toast.LENGTH_SHORT).show();

				} catch (Exception e) {
					Toast.makeText(view.getContext(), "ERROR", Toast.LENGTH_SHORT).show();
					Log.d(TAG, e.getMessage());
				}
			}
		});	

		//What does this do?
		tv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
				try{
					con.print("HAHAHA", false);
				}
				catch(Exception e){
					Log.d(TAG, "Could not send message");
				}
				return false;
			}
		});

		//Add refresh button to UI
		refresh = (Button) findViewById(R.id.refresh);
		refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				//Clear the list of BT devices
				device_list.clear();

				//Notify the adapter that the list is now empty
				adapter.notifyDataSetChanged();

				//Scan for new BT devices
				checkBTState();
			}
		});

		refresh.setVisibility(View.GONE);

		//Check the BT state
		checkBTState();
	}

	/* This routine is called when an activity completes.*/
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_ENABLE_BT) {
			checkBTState();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (btAdapter != null) {
			btAdapter.cancelDiscovery();
		}
		unregisterReceiver(ActionFoundReceiver);

		//Shut down the BT connection
		if(con != null) con.disconnect();
	}

	public void onResume() {
		super.onResume();

		//Clear the list of BT devices
		device_list.clear();

		//Notify the adapter that the list is now empty
		adapter.notifyDataSetChanged();

		//Scan for new BT devices
//		checkBTState();
		
        try {
        	
        	String mac = "";	//FIXME: get mac from preferences
        	
        	con = new BluetoothConnection(mac, (Activity)getBaseContext(), getConnectionListener());
			con.connect();		
			
			Toast.makeText(getBaseContext(), "SUCCESS", Toast.LENGTH_SHORT).show();
			
		} catch (Exception e) {
			Toast.makeText(getBaseContext(), "ERROR", Toast.LENGTH_SHORT).show();
			Log.d(TAG, e.getMessage());
        }
	}

	/**
	 * Checks the current BT state
	 */
	private void checkBTState() {

		//Check if the Android support bluetooth
		if(btAdapter==null) return;
		else {

			//Check if the BT is turned on
			if (btAdapter.isEnabled()) {

				//Show the progress bar
				progressBar.setVisibility(View.VISIBLE);
				refresh.setVisibility(View.GONE);

				// Starting the device discovery
				btAdapter.startDiscovery();
			}

			//If the BT is not turned on, request the user to turn it on
			else if (!btAdapter.isEnabled() && !alreadyChecked){
				Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

				//The user have already turned it on or off: don't request again
				alreadyChecked = true;
			}
		}
	}

	private final BroadcastReceiver ActionFoundReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			//If discovery started
			if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
				Log.d(TAG, "\nDiscovery Started...");
			}

			//If a device is found
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

				//If the bluetooth class is named 708 that we made as a 'standard' for recognizing arduinos
				if((device.getBluetoothClass().toString()).equals("708") || LIST_NON_ARDUINO_DEVICES){


					//Adding found device
					HashMap<String, String> map = new HashMap<String, String>();
					map = new HashMap<String, String>();
					map.put("name", device.getName());
					map.put("mac", device.getAddress());
					map.put("pager", device.getBluetoothClass().toString());
					device_list.add(map);

					btDeviceList.add(device);	//FIXME: debugging

					adapter.notifyDataSetChanged();
				}

				//				Log.d(TAG, device.getName());
				//				Log.d(TAG, device.getBluetoothClass().getClass().getCanonicalName());
				//				Log.d(TAG, device.getBluetoothClass().getClass().getName());
				//				Log.d(TAG, device.getBluetoothClass().getClass().getSimpleName());
				//				Log.d(TAG, "major: " + String.valueOf(device.getBluetoothClass().getMajorDeviceClass()));
				//				Log.d(TAG, "device: " + String.valueOf(device.getBluetoothClass().getDeviceClass()));
				//				Log.d(TAG, device.getBluetoothClass().toString());

			}

			//If it received a UUID parcel
//			if(BluetoothDevice.ACTION_UUID.equals(action)) {	TODO: uncomment these lines
//				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//				Parcelable[] uuidExtra = intent.getParcelableArrayExtra(BluetoothDevice.EXTRA_UUID);
//
//				for (int i=0; i<uuidExtra.length; i++) {
//					Log.d(TAG, "\n  Device: " + device.getName() + ", " + device + ", Service: " + uuidExtra[i].toString());
//				}
//			}

			//If discovery finished
			if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {

				//Hide the progress bar
				progressBar.setVisibility(View.GONE);
				refresh.setVisibility(View.VISIBLE);

//				Iterator<BluetoothDevice> itr = btDeviceList.iterator(); TODO: incomment these lines
//
//				while (itr.hasNext()) {
//
//					// Get Services for paired devices
//					BluetoothDevice device = itr.next();
//					Log.d(TAG, "\nGetting Services for " + device.getName() + ", " + device);
//
//				}

				Log.d(TAG, "\nDiscovery Finished");
			}
		}
	};

	private ConnectionListener getConnectionListener() {
		return new ConnectionListener() {
			public void onConnect(BluetoothConnection bluetoothConnection) {
				Log.d(TAG, "Connected to: " + con.toString());

				//Add a button for every service found
				ConnectionMetadata meta = con.getConnectionData();
				for(String service : meta.getServicesSupported()) {
					Integer pins[] = meta.getServicePins(service);

					//Pin controlled button
					if(pins.length > 0) {
						if(service.equals(DefaultServices.SERVICE_LED_LAMP.name()))  for(int pin : pins) Log.d(TAG, "LED pin: " + pin);
						if(service.equals(DefaultServices.SERVICE_VIBRATION.name()))  for(int pin : pins) Log.d(TAG, "VIBRATION pin " + pin);
						if(service.equals(DefaultServices.SERVICE_SPEAKER.name())) Log.d(TAG, "SPEAKER");
					}

					//LCD print screen
					else if(service.equals(DefaultServices.SERVICE_LCD_SCREEN.name())) Log.d(TAG, "LCD");
				}

			}

			@Override
			public void onConnecting(BluetoothConnection bluetoothConnection) {
				Log.d(TAG, "Connecting to BT");
			}

			@Override
			public void onDisconnect(BluetoothConnection bluetoothConnection) {
				Log.d(TAG, "Disconnected from BT");
			}
		};

	};

	private class LCDButton extends Button implements View.OnClickListener {
		int timesClicked;

		public LCDButton(Context context) {
			super(context);
			setOnClickListener(this);
			setText("Print \"Hello World\"");
		}

		public void onClick(View v) {
			try {
				con.print("Hello World! (" + timesClicked++ + ")", false);
			} catch (TimeoutException e) {}
		}

	}
}