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

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import no.group09.connection.BluetoothConnection;
import no.group09.connection.BluetoothConnection.ConnectionState;
import no.group09.ucsoftwarestore.R;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Screen for adding a device connection manually.
 */
public class AddDeviceScreen extends Activity {

	/** Buttons for different methods of adding devices */
	private Button inputMacAddressButton, qrButton;
	/** The MAC address contained from adding */
	String macAddress = null;
	private final static String TAG = "AddDeviceScreen"; 
	private String macAddressString = null;
	private static final int CUSTOM_REQUEST_QR_SCANNER = 0;
	private ProgressDialog progressDialog;
	private SharedPreferences sharedPref;
	private String macAdress;
	/** 
	 * Message text that is to be printed to the user when the attempt to 
	 * connect was unsuccessful.
	 */
	private final static String NEGATIVE_MESSAGE = "The connection was not " +
			"successfull.\nPlease try again.";
	protected static final String NOT_VALID_MAC = "The MAC address entered is not " +
			"valid.\n\nPlease enter a valid MAC address.";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Set the xml layout
		setContentView(R.layout.add_device_screen);

		progressDialog = new ProgressDialog(this);

		//Fetching the shared preferences object used to write the preference file
		sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

		//Add functionality to the input serial button
		inputMacAddressButton = (Button) findViewById(R.id.input_serial_button);
		inputMacAddressButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onCreateDialog();
			}
		});

		//Add functionality to the QR button
		qrButton = (Button) findViewById(R.id.qr_button);
		qrButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent("com.google.zxing.client.android.SCAN");
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
				intent.putExtra("SCAN_MODE", "QR_CODE_MODE");

				String title = (String) getResources().getText(R.string.chooser_title);

				Intent chooser = Intent.createChooser(intent, title);

				startActivityForResult(chooser, CUSTOM_REQUEST_QR_SCANNER);
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == CUSTOM_REQUEST_QR_SCANNER) {

			//Successful scan
			if (resultCode == RESULT_OK) {
				String macAddress = intent.getStringExtra("SCAN_RESULT");
				//Convert the MAC address to upper case
				toUpperCase(macAddress);
				if (isValidMacAddress(macAddress)){

					Toast.makeText(getBaseContext(), "Trying to connect to: " + 
							macAddress, Toast.LENGTH_LONG).show();

					Intent serviceIntent = new Intent(getApplicationContext(), 
							no.group09.utils.BtArduinoService.class);

					serviceIntent.putExtra(Devices.MAC_ADDRESS, macAddress);

					/* 
					 * Checks if there is another valid bluetooth connection running
					 * If so, the previous connection is terminated and disconnected
					 */
					if(isMyServiceRunning()) {
						BtArduinoService.getBtService().getBluetoothConnection()
						.setConnectionState(ConnectionState.STATE_DISCONNECTED);
						BtArduinoService.getBtService().getBluetoothConnection().disconnect();
						stopService(serviceIntent);
					}

					Log.d(TAG, "Starting new service for connection with device");
					startService(serviceIntent);
					setMacAdress(macAddress);

					ProgressDialogTask task = new ProgressDialogTask();
					task.execute();
				}
				else {
					createDialog(NOT_VALID_MAC);
				}
			}
		}
	}

	/**
	 * Checks whether a service is running or not
	 * 
	 * @return true if the service is running, false if not
	 */
	private boolean isMyServiceRunning() {
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if (BtArduinoService.class.getName().equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Creates a pop up box where the user can type in the MAC address of the
	 * bluetooth device. 
	 * 
	 * @return The created Dialog
	 */
	public Dialog onCreateDialog() {
		AlertDialog.Builder inputSerialDialog = new AlertDialog.Builder(this);

		final EditText input = new EditText(this);
		inputSerialDialog.setView(input);

		inputSerialDialog.setMessage("Please type the complete MAC address of " +
				"your bluetooth device.")
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						macAddressString = input.getText().toString();
						toUpperCase(macAddressString);
						if(isValidMacAddress(macAddressString)){

							Intent serviceIntent = new Intent(getBaseContext(), 
									no.group09.utils.BtArduinoService.class);

							serviceIntent.putExtra(Devices.MAC_ADDRESS, macAddressString);

							/* 
							 * Checks if there is another valid bluetooth connection running
							 * If so, the previous connection is terminated and disconnected
							 */
							if(isMyServiceRunning()){
								BtArduinoService.getBtService().getBluetoothConnection()
								.setConnectionState(ConnectionState.STATE_DISCONNECTED);
								BtArduinoService.getBtService().getBluetoothConnection().disconnect();
								stopService(serviceIntent);
							}

							Log.d(TAG, "Starting new service for connection with device");
							startService(serviceIntent);
							setMacAdress(macAddressString);

							ProgressDialogTask task = new ProgressDialogTask();
							task.execute();
						}

						else{
							createDialog(NOT_VALID_MAC);
						}
					}
				});

		return inputSerialDialog.show();
	}

	/**
	 * Converts string to uppercase
	 * 
	 * @param address the string to be converted
	 */
	public void toUpperCase(String address) {
		String uppercase = "";
		for (int i = 0; i < address.length(); i++) {
			int c = address.charAt(i);

			if (c >= 97 && c <= 122) {
				c = c - 32;
				uppercase += (char) c;
			}
			else uppercase += (char) c;
		}
		macAddressString = uppercase;
	}

	/**
	 * Returns the MAC address of the device that is connected to through this
	 * activity.
	 * 
	 * @return String containin the MAC address.
	 */
	public String getMacAdress() {
		return macAdress;
	}

	/**
	 * Sets the MAC address of the device that is beeing connected to through this
	 * activity.
	 * 
	 * @param macAdress String containing the MAC address of the device.
	 */
	public void setMacAdress(String macAdress) {
		this.macAdress = macAdress;
	}

	/**
	 * Check if the given MAC address is valid
	 * 
	 * @param mac String with the MAC address that is to be validated
	 * @return True if the address is valid, false if not.
	 */
	public boolean isValidMacAddress(String mac) {
		Pattern isValid = Pattern.compile("([0-9A-F]{2}[:-]){5}([0-9A-F]{2})");
		Matcher m = isValid.matcher(mac);

		return m.matches();
	}

	/**
	 * Add the connected device name to the title. If no device is found in the
	 * preferences file only "Add device" will be shown.
	 */
	public void setActivityTitle(){
		String appName = sharedPref.getString("connected_device_name", "null");

		if(Devices.isConnected() && !appName.equals("null")){
			setTitle("Add device" + " - " + appName);
		}
		else{
			setTitle("Add device");
		}
	}

	/**
	 * Creates a dialog with the result of the connection attempt with a device.
	 * If the attempt was successful, the user is returned to the previous activity.
	 * If not, the dialog box is dismissed and the user can retry the connection.
	 * 
	 * @param message The message that is to be printed to the user. Must be
	 * NEGATIVE_MESSAGE for negative response.
	 * 
	 * @return The dialog to be shown
	 */
	public Dialog createDialog(String message) {
		AlertDialog.Builder responseDialog = new AlertDialog.Builder(this);

		//The MAC address entered by the user was not valid
		if (message.equals(NOT_VALID_MAC)) {
			responseDialog.setMessage(message)
			.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//The connection was unsuccessful, and the user will need
					//to try again - just dismiss the dialog box.
				}
			});
		}
		//Check if the response from the connection attempt was negative
		else if (message.equals(NEGATIVE_MESSAGE)) {
			responseDialog.setMessage(message)
			.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//The connection was unsuccessful, and the user will need
					//to try again - just dismiss the dialog box.
				}
			});
		}
		//The response from connection attempt was positive
		else {
			responseDialog.setMessage(message)
			.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//Return to device list when connection is successful
					finish();
				}
			});
		}
		return responseDialog.show();
	}

	/**
	 * AsyncTask handling the progress bar shown to the user upon connecting
	 * to a device. 
	 */
	private class ProgressDialogTask extends AsyncTask<Void, Void, Boolean> {

		private long timeout;

		@Override
		protected void onPreExecute() {
			//30 second timeout
			timeout = System.currentTimeMillis() + 30000;
			progressDialog.setMessage("Connecting, please wait... This might take " +
					"up to 30 seconds.");
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			while(true) {
				if (Devices.isConnected()){
					return true;
				}
				//Return false if the timeout is encountered
				if (System.currentTimeMillis() > timeout) {
					return false;
				}
				else if (Devices.connectionHasFailed()) return false;

				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {}
			}
		}

		@Override 
		protected void onPostExecute(Boolean success) {
			BluetoothConnection connection = BtArduinoService.getBtService().getBluetoothConnection();
			progressDialog.dismiss();
			String message, title;

			if (success && connection.isConnected()) {
				String deviceName = connection.toString();

				//Check if the connected device has a name
				if(deviceName != null) {
					String lastConnectedDevice = "Device name: " + deviceName
							+ "\nMAC Address: " + getMacAdress();
					Editor edit = sharedPref.edit();
					edit.putString("connected_device_dialog", lastConnectedDevice);
					edit.putString("connected_device_name", deviceName);
					edit.putString("connected_device_mac", getMacAdress());
					edit.commit();
					//Set the title bar
					title = "Add device - " + deviceName;
					//Set the message for the user
					message = "Successfully connected to device: " + connection.toString();
				}
				//If it does not have a name, use the MAC address
				else {
					String lastConnectedDevice = "Device name: " + getMacAdress()
							+ "\nMAC Address: " + getMacAdress();
					Editor edit = sharedPref.edit();
					edit.putString("connected_device_dialog", lastConnectedDevice);
					edit.putString("connected_device_name", getMacAdress());
					edit.putString("connected_device_mac", getMacAdress());
					edit.commit();
					//Set the title bar
					title = "Add device - " + deviceName;
					//Set the message for the user
					message = "Successfully connected to device: " + getMacAdress();
				}
			}
			//Not successful connection
			else {
				message = NEGATIVE_MESSAGE;
				title = "Add device";
			}
			createDialog(message);
			setTitle(title);
		}
	}
}