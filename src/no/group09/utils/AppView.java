package no.group09.utils;

import no.group09.arduinoair.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.content.DialogInterface;

public class AppView extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		//Set the xml layout
		setContentView(R.layout.app_view);	


		setContentView(R.layout.app_view);
	}
	//	method for handling click of the review button
	public void reviewClicked(View view){
		//		
		//		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		//		
		//		builder.setTitle("Review the app").setItem()
		//
	}
	//	method for handling the click of the install button
	public void installClicked(View view){

		//		creates an alertdialog builder
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		//	if no device connected, create popup with that message
		//		if( no device connected)
		builder.setMessage("Cannot install app, no device connected").setPositiveButton("Ok",new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		builder.create();
		//		else
		//		builder.setMessage("Press install to install this app to " + DEVICENAME).setPositiveButton("Install", new DialogInterface.OnClickListener(){
		//			@Override
		//			public void onClick(DialogInterface dialog, int which){
		//
		//			}
		//		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
		//			@Override
		//			public void onClick(DialogInterface dialog, int which){
		//
		//			}
		//		});
		//		builder.create();
		
		builder.show();

	}
		public void installingDialog(){
			InstallingDialog popup = new InstallingDialog();
			
			
//			creates an alertdialog builder
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			TODO: create progressbar, or cancel after feedback from device
//			builder.setTitle("Installing to " + DEVICENAME).setMessage("\nDO NOT MOVE AWAY FROM THE DEVICE!!");
		}
}

