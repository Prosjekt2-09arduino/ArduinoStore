package no.group09.utils;

import no.group09.arduinoair.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
//import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.DialogFragment;

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



public class AddDeviceScreen extends Activity {

	private Button inputSerialButton, qrButton;
	String serial = null;
	private final static String TAG = "AddDeviceScreen"; 
	private String serialString = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		//Set the xml layout
		setContentView(R.layout.add_device_screen);


		//Add functionality to the QR button
		
		

		//Add functionality to the input serial button
		inputSerialButton = (Button) findViewById(R.id.input_serial_button);
		inputSerialButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onCreateDialog();
			}
		});
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
