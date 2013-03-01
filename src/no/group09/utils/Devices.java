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

import no.group09.arduinoair.ConnectionHolder;
import no.group09.arduinoair.MainActivity;
import no.group09.arduinoair.R;
import no.group09.connection.BluetoothConnection;
import no.group09.connection.ConnectionListener;
import no.group09.connection.ConnectionMetadata;
import no.group09.connection.ConnectionMetadata.DefaultServices;
import no.group09.fragments.BluetoothDeviceAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View.OnClickListener;

/**
 * This class searches for the BT devices in range and put them in a list
 */
public class Devices extends Activity  {

	private SharedPreferences sharedPref;
	private ProgressDialog progressDialog;
	private static final String TAG = "DEVICES";
	private static final boolean LIST_NON_ARDUINO_DEVICES = true;
	private static final int REQUEST_ENABLE_BT = 1;
	private ListView tv;
	private BluetoothDeviceAdapter listAdapter;
	private BluetoothAdapter btAdapter; 
	private ArrayList<HashMap<String, String>> category_list;
	private IntentFilter filter;
	private Button refresh;
	private Button addDeviceButton, browseShowButton;
	private ProgressBar progressBar;
	private ArrayList<HashMap<String, String>> device_list;
	private boolean alreadyChecked = false;
	private ArrayList<BluetoothDevice> btDeviceList = new ArrayList<BluetoothDevice>();
//	private BluetoothConnection con;
	private MyBroadcastReceiver actionFoundReceiver;
	public static final String MAC_ADDRESS = "MAC_ADDRESS";
	static Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Set the xml layout
		setContentView(R.layout.devices);
		
		//Fetching the shared preferences object used to write the preference file
		sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

		//Progressdialog used to indicate that the program is connecting to a BT device
		progressDialog = new ProgressDialog(this);

		//progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		progressBar = (ProgressBar) findViewById(R.id.progbar);

		registerBroadcastReceiver();

		// Getting the Bluetooth adapter
		btAdapter = BluetoothAdapter.getDefaultAdapter();

		category_list = new ArrayList<HashMap<String, String>>();

		// Getting adapter by passing xml data ArrayList
		listAdapter = new BluetoothDeviceAdapter(getBaseContext(), category_list); 

		//List of devices
		device_list = new ArrayList<HashMap<String, String>>();

		//Get the xml to how the list is structured
		tv = (ListView) findViewById(R.id.bluetooth_devices_list);

		//Get the adapter for the device list
		listAdapter = new BluetoothDeviceAdapter(getBaseContext(), device_list);        

		//Set the adapter
		tv.setAdapter(listAdapter);

		//Click event for single list row
		tv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				String macAddress = listAdapter.getMacAddress(position);
				
				//Stygg hack, men det funker. Fix hvis du orker... (Good luck.)
				setContext(view.getContext());
				
				Intent serviceIntent = new Intent(getApplicationContext(), no.group09.utils.BtArduinoService.class);
				serviceIntent.putExtra(MAC_ADDRESS, macAddress);
				Bundle bundle = new Bundle();
//				serviceIntent.putextra
				
				//FIXME: If the service allready is running, it does not start over.
				//This might be a problem if it needs to change the connected device.
				getApplicationContext().startService(serviceIntent);

				
//				progressDialog.setMessage("Connecting...");
//				progressDialog.setCancelable(false);
//				progressDialog.show();
//				


//				Log.d(TAG, "Check if the device is connected: " + con.isConnected());

				String lastConnectedDevice = "Device name: " + listAdapter.getName(position)
						+ "\nMAC Address: " + listAdapter.getMacAddress(position);

				Editor edit = sharedPref.edit();

				//Saves the full information about the last connected device to sharedPreferences
				edit.putString("connected_device_dialog", lastConnectedDevice);

				//Save the name of the BT device as a separate setting. Used
				//to show the name of last connected device in title bar.
				edit.putString("connected_device_name", listAdapter.getName(position));

				edit.commit();
				Log.d(TAG, "The information about the last connected device was written to shared preferences");

			}
		});	

		//What does this do? TODO: Answer.
		tv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
				try{
//					con.print("HAHAHA", false);
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
				listAdapter.notifyDataSetChanged();

				//Scan for new BT devices
				checkBTState();
			}
		});

		refresh.setVisibility(View.GONE);

		//Check the BT state
		checkBTState();


		//Add the button that opens the 'Add device' screen
		addDeviceButton = (Button) findViewById(R.id.add_device_button);
		addDeviceButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(), AddDeviceScreen.class));
			}
		});


		//Add the button that takes you directly to the shop.
		//TODO: Check if this is done right since the shop is our main actvity
		//FIXME: This is not done rigth. Make it go back instead of making a new Activity
		browseShowButton = (Button) findViewById(R.id.browse_shop_button);
		browseShowButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(), MainActivity.class));
			}
		});
	}
	
	//Disse to er en del av en stygg hack, men det funker. Fix senere.
	private void setContext(Context context) {
		this.context = context;
	}
	
	public static Context getContext() {
		return context;
	}


	/**
	 * Method used to register the broadcast receiver for communicating with
	 * bluetooth API
	 */
	private void registerBroadcastReceiver() {
		//Register the BroadcastReceiver
		filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		filter.addAction(BluetoothDevice.ACTION_UUID);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

		actionFoundReceiver = new MyBroadcastReceiver();
		//Registers the BT receiver with the requested filters
		//Don't forget to unregister during onDestroy
		registerReceiver(actionFoundReceiver, filter);
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
	public void onBackPressed() {
		super.onBackPressed();
//		unregisterReceiver(actionFoundReceiver);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(actionFoundReceiver);
		if (btAdapter != null) {
			btAdapter.cancelDiscovery();
		}
		//		unregisterReceiver(ActionFoundReceiver);

		//Shut down the BT connection
		//		if(con != null) con.disconnect();
	}

	public void onResume() {
		super.onResume();

		//Check if there is an active connection with a BT device
//		if (con != null) Log.d(TAG, "Check if the device is connected " + con.isConnected());

		//Clear the list of BT devices
		device_list.clear();

		//Notify the adapter that the list is now empty
		listAdapter.notifyDataSetChanged();

		//Scan for new BT devices
		//		checkBTState();

		try {

//			String mac = "";	//FIXME: get mac from preferences

//			con = new BluetoothConnection(mac, (Activity)getBaseContext(), getConnectionListener());
//			con.connect();		


		} catch (Exception e) {
			Log.d(TAG, "Could not find the last connected device");
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

	/**
	 * For debugging (?).
	 * @return 
	 */
//	private ConnectionListener getConnectionListener() {
//		return new ConnectionListener() {
//			public void onConnect(BluetoothConnection bluetoothConnection) {
//				Log.d(TAG, "Connected to: " + con.toString());
//
//				//Add a button for every service found
//				ConnectionMetadata meta = con.getConnectionData();
//				for(String service : meta.getServicesSupported()) {
//					Integer pins[] = meta.getServicePins(service);
//
//					//Pin controlled button
//					if(pins.length > 0) {
//						if(service.equals(DefaultServices.SERVICE_LED_LAMP.name()))  for(int pin : pins) Log.d(TAG, "LED pin: " + pin);
//						if(service.equals(DefaultServices.SERVICE_VIBRATION.name()))  for(int pin : pins) Log.d(TAG, "VIBRATION pin " + pin);
//						if(service.equals(DefaultServices.SERVICE_SPEAKER.name())) Log.d(TAG, "SPEAKER");
//					}
//
//					//LCD print screen
//					else if(service.equals(DefaultServices.SERVICE_LCD_SCREEN.name())) Log.d(TAG, "LCD");
//				}
//
//			}
//
//			@Override
//			public void onConnecting(BluetoothConnection bluetoothConnection) {
//				Log.d(TAG, "Connecting to BT");
//			}
//
//			@Override
//			public void onDisconnect(BluetoothConnection bluetoothConnection) {
//				Log.d(TAG, "Disconnected from BT");
//			}
//		};
//
//	};

	//TODO: Not in use. Remove? 
//	private class LCDButton extends Button implements View.OnClickListener {
//		int timesClicked;
//
//		public LCDButton(Context context) {
//			super(context);
//			setOnClickListener(this);
//			setText("Print \"Hello World\"");
//		}
//
//		public void onClick(View v) {
//			try {
//				con.print("Hello World! (" + timesClicked++ + ")", false);
//			} catch (TimeoutException e) {}
//		}
//	}


	/**
	 * Broadcast receiver class. Used to receive Android Bluetooth API communication
	 * 
	 * @author JeppeE
	 *
	 */
	private class MyBroadcastReceiver extends BroadcastReceiver {

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
				if((device.getBluetoothClass().toString()).equals("708")){


					//Adding found device
					HashMap<String, String> map = new HashMap<String, String>();
					map = new HashMap<String, String>();
					map.put("name", device.getName());
					map.put("mac", device.getAddress());
					map.put("pager", device.getBluetoothClass().toString());
					device_list.add(map);

					btDeviceList.add(device);	//FIXME: debugging

					listAdapter.notifyDataSetChanged();
				}
			}
			//If discovery finished
			if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {

				//Hide the progress bar
				progressBar.setVisibility(View.GONE);
				refresh.setVisibility(View.VISIBLE);
				Log.d(TAG, "\nDiscovery Finished");
			}
		}

	}
}