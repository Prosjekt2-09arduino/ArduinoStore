package no.group09.utils;

import no.group09.arduinoair.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.content.DialogInterface;

public class AppView extends Activity {

	ProgressDialog progressBar;
	private int progressStatus = 0;
	private Handler progressHandler = new Handler();

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
		//		if(!getDeviceName().equals(null)
		builder.setMessage("Cannot install app, no device connected").setPositiveButton("Ok",new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		}).setNegativeButton("Install anyway",new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which){
				//				just to test if installingDialog works and if it responds well
				installingDialog();
			}
		});
		builder.create();
		//		else
		//		builder.setMessage("Press install to install this app to " + getDeviceName().setPositiveButton("Install", new DialogInterface.OnClickListener(){
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

		//			preparing progress bar dialog
		progressBar = new ProgressDialog(this);
		progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressBar.setCancelable(false);
		progressBar.setTitle("Installing app to " + getDeviceName());
		progressBar.setMessage("DO NOT DISCONNECT OR MOVE AWAY FROM THE DEVICE!");
		progressBar.setProgress(0);
		progressBar.setMax(100);
		progressBar.show();


		//			reset progress bar status, just in case...
		progressStatus = 0;

		new Thread(new Runnable() {

			@Override
			public void run() {
				while(progressStatus < 100){
					//					TODO: call method to update progressStatus

					progressStatus++;
					//				to make sure thread doesnt use up too much resources
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						// TODO: handle exception
					}

					//					update the progress bar itself
					progressHandler.post(new Runnable() {

						@Override
						public void run() {
							progressBar.setProgress(progressStatus);						
						}

					});
				}
				//				the progress is done
				if(progressStatus >= 100){

					//					sleep so the user can see the 100% mark
					try {
						Thread.sleep(1500);
					} catch (Exception e) {
						// TODO: handle exception
					}
					progressBar.dismiss();
				}
			}

		}).start();

		//			TODO: create progressbar, or cancel after feedback from device
	}
	private String getDeviceName(){
		//			TODO: implement this method of getting the connected device name

		return null;
	}
}

