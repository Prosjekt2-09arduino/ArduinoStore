package no.group09.utils;

import no.group09.database.Save;
import no.group09.database.objects.App;
import no.group09.database.objects.Developer;
import no.group09.ucsoftwarestore.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.content.DialogInterface;

public class AppView extends Activity {

	ProgressDialog progressBar;
	private int progressStatus = 0;
	private Handler progressHandler = new Handler();
	private Save save;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		//Set the xml layout
		setContentView(R.layout.app_view);	

		//Fetch the application ID from the intent
		int appID = getIntent().getExtras().getInt("app");
		
		//Get the database
		save = new Save(getBaseContext());
		
		//Open the database
		save.open();
		
		//Fetch the application from the database
		App app = save.getApp(appID);
		Developer developer = save.getDeveloper(app.getDeveloperID());
		
		TextView appName = (TextView) findViewById(R.id.app_view_app_name);
		TextView appDeveloper = (TextView) findViewById(R.id.app_view_developer);
		RatingBar rating = (RatingBar) findViewById(R.id.ratingBarIndicator);
		
		appName.setText(app.getName());
		
		appDeveloper.setText(developer.getName());	//TODO: Get the developer from the database on this ID
		
		rating.setRating((float)app.getRating());
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
