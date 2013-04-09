package no.group09.utils;
import no.group09.connection.BluetoothConnection.ConnectionState;
import no.group09.ucsoftwarestore.R;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

/**
 * Screen for adding a device connection not shown in the devicelist.
 * 
 */

public class AddDeviceScreen extends Activity {

	private Button inputSerialButton, qrButton;
	String serial = null;
	private final static String TAG = "AddDeviceScreen"; 
	private String serialString = null;
	private static final int CUSTOM_REQUEST_QR_SCANNER = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Set the xml layout
		setContentView(R.layout.add_device_screen);

		//Add functionality to the input serial button
		inputSerialButton = (Button) findViewById(R.id.input_serial_button);
		inputSerialButton.setOnClickListener(new OnClickListener() {

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

				Toast.makeText(getBaseContext(), macAddress, Toast.LENGTH_LONG).show();
				
				Intent serviceIntent = new Intent(getApplicationContext(), no.group09.utils.BtArduinoService.class);
				serviceIntent.putExtra(Devices.MAC_ADDRESS, macAddress);

				if(isMyServiceRunning()){
					BtArduinoService.getBtService().getBluetoothConnection().setConnectionState(ConnectionState.STATE_DISCONNECTED);
					BtArduinoService.getBtService().getBluetoothConnection().disconnect();
					stopService(serviceIntent);
				}
				startService(serviceIntent);
			}
		}
	}
	
	/** Checks wether a service is running or not */
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
	 * Creates a pop up box where the user can type in the serial of the
	 * bluetooth device. 
	 * 
	 * The string the user types in is now stored as a simple string, but not in 
	 * shared preferences. See the TODO in the method.
	 * 
	 * @return The created Dialog
	 */
	public Dialog onCreateDialog() {
		AlertDialog.Builder inputSerialDialog = new AlertDialog.Builder(this);

		final EditText input = new EditText(this);
		inputSerialDialog.setView(input);

		inputSerialDialog.setMessage("Please type the serial key of your bluetooth device")
		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				serialString = input.getText().toString();
				//TODO: Decide what to do with this string
				Log.d(TAG, "The input: " + serialString + " was stored in a String");
			}
		});

		return inputSerialDialog.show();
	}
}
