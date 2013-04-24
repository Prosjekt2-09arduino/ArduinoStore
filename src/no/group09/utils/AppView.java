package no.group09.utils;

import no.group09.ucsoftwarestore.R;
import no.group09.database.Save;
import no.group09.database.entity.App;
import no.group09.database.entity.BinaryFile;
import no.group09.database.entity.Developer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * The informational view of an app in the shop.
 */
public class AppView extends Activity {

	ProgressDialog progressBar;
	private int progressStatus = 0;
	private Handler progressHandler = new Handler();
	private Save save;
	private Context ctxt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Set the xml layout
		setContentView(R.layout.app_view);	

		ctxt = getBaseContext();
		
		//Fetch the application ID from the intent
		int appID = getIntent().getExtras().getInt("app");

		//Get the database
		save = new Save(getBaseContext());

		//Fetch the application from the database
		App app = save.getAppByID(appID);
		Developer developer = save.getDeveloperByID(app.getDeveloperID());
		BinaryFile binaryfile = save.getBinaryFileByAppID(appID);
		final String blob = binaryfile.getBinaryFileAsString();
		
		//Get the objects from xml
		TextView appName = (TextView) findViewById(R.id.app_view_app_name);
		TextView appDeveloper = (TextView) findViewById(R.id.app_view_developer);
		RatingBar rating = (RatingBar) findViewById(R.id.ratingBarIndicator);
		TextView appDescription = (TextView) findViewById(R.id.app_view_description);

		//Set the information on the UI that we fetched from the database-objects
		appName.setText(app.getName());
		appDeveloper.setText(developer.getName());
		rating.setRating(app.getRating());
		appDescription.setText(app.getDescription());

		Button installButton = (Button) findViewById(R.id.button1);
		installButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				installClicked(v);
			}
		});
	}

	/**	method for handling the click of the install button */
	public void installClicked(View view){

		//Creates an alertdialog builder
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		if(!Devices.isConnected()){
			//If no device connected, create popup with that message
			builder.setMessage("Cannot install app, no device connected").setPositiveButton("Cancel",new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
				
			}).setNegativeButton("Choose a device",new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which){
					startActivity(new Intent(ctxt, Devices.class));
				}
			});
		}
		else{
			builder.setMessage("Press install to install this app to " + getDeviceName())
			.setPositiveButton("Install", new DialogInterface.OnClickListener(){
				
				@Override
				public void onClick(DialogInterface dialog, int which){
					installingDialog();
				}
				
			}).setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which){
					
				}
			});
		}

		builder.create();
		builder.show();

	}
	/**
	 * Dialog for displaying progressbar for installation purposes.
	 */
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

		//Reset progress bar status, just in case...
		progressStatus = 0;

		new Thread(new Runnable() {

			@Override
			public void run() {
				while(progressStatus < 100){
					progressStatus += 10;
					
					//To make sure thread doesnt use up too much resources
					try { Thread.sleep(10);
					} catch (Exception e) {}

					//Update the progress bar itself
					progressHandler.post(new Runnable() {

						@Override
						public void run() {
							progressBar.setProgress(progressStatus);						
						}
					});
				}
				
				//The progress is done
				if(progressStatus >= 100){
					//Sleep so the user can see the 100% mark
					try { Thread.sleep(1500);
					} catch (Exception e) {}
					progressBar.dismiss();
				}
			}
		}).start();
	}

	private String getDeviceName() {
		if(Devices.isConnected()){
			SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
			return pref.getString("connected_device_name", "null");
		}
		return "no device";
	}
}
