package no.group09.utils;

import no.group09.stk500_v1.STK500v1.ProtocolState;
import no.group09.ucsoftwarestore.R;
import no.group09.database.Save;
import no.group09.database.entity.App;
import no.group09.database.entity.BinaryFile;
import no.group09.database.entity.Developer;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.ActivityManager.RunningServiceInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
public class AppView extends Activity implements Runnable {

	ProgressDialog progressBar;
	private int progressNumber = 0;
	private Handler progressHandler = new Handler();
	private Save save;
	private Context ctxt;
	private Thread progressbarThread;
	private ProtocolState state;
	private static final String TAG = "AppView";

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

		//Initialize the thread that should contain the progressbar
		progressbarThread = new Thread();

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
		ImageView thumb_image = (ImageView) findViewById(R.id.app_profile_pic);

		//Set the information on the UI that we fetched from the database-objects
		appName.setText(app.getName());
		appDeveloper.setText(developer.getName());
		rating.setRating(app.getRating());
		appDescription.setText(app.getDescription());

		if(app.getCategory().equals("Games")){
			thumb_image.setImageResource(R.drawable.games);
		}
		else if(app.getCategory().equals("Medical")){
			thumb_image.setImageResource(R.drawable.medical);
		}

		else if(app.getCategory().equals("Tools")){
			thumb_image.setImageResource(R.drawable.tools);
		}

		else if(app.getCategory().equals("Media")){
			thumb_image.setImageResource(R.drawable.media);
		}

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
			builder.setMessage("Press install to install this app to " + getDeviceName() + ".\n"+
					"Do not turn off Bluetooth or move away from the device.")
					.setPositiveButton("Install", new DialogInterface.OnClickListener(){

						@Override
						public void onClick(DialogInterface dialog, int which){

							//TODO: add call to the service for it to start the 
							//programmer here.

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
	 * Method for showing the result of a programming attempt. Writes success
	 * message if it was successful or error message if not.
	 * 
	 * @param error Boolean for indicating if an error message or success message
	 * should be printed. True for error, false for success. 
	 */
	public void informationBox(boolean error) {
		//Creates an alertdialog builder
		AlertDialog.Builder b = new AlertDialog.Builder(this);
		
		if (error) {
			//TODO: give better error message?
			b.setMessage("An error was encountered while trying to program your " +
					"device. Please try to install again.").setPositiveButton
					("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					
				}
			});
		}
		else {
			b.setMessage("You have successfully programmed your device!").setPositiveButton
					("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					
				}
			});
		}
		b.create();
		b.show();
	}
	/**
	 * Dialog for displaying progressbar for installation purposes.
	 */
	public void installingDialog(){

		progressBar = new ProgressDialog(this);
		progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressBar.setCancelable(false);
		progressBar.setTitle("Installing app to " + getDeviceName());
		progressBar.setProgress(0);
		progressBar.setMax(100);
		progressBar.show();

		progressbarThread.run();

		//preparing progress bar dialog
		//		progressBar = new ProgressDialog(this);
		//		progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		//		progressBar.setCancelable(false);
		//		progressBar.setTitle("Installing app to " + getDeviceName());
		//		progressBar.setMessage("DO NOT DISCONNECT OR MOVE AWAY FROM THE DEVICE!");
		//		progressBar.setProgress(0);
		//		progressBar.setMax(100);
		//		progressBar.show();
		//
		//		//Reset progress bar status, just in case...
		//		progressStatus = 0;

		//		new Thread(new Runnable() {
		//
		//			@Override
		//			public void run() {
		//				while(progressStatus < 100){
		//					progressStatus += 10;
		//					
		//					//To make sure thread doesnt use up too much resources
		//					try { Thread.sleep(10);
		//					} catch (Exception e) {}
		//
		//					//Update the progress bar itself
		//					progressHandler.post(new Runnable() {
		//
		//						@Override
		//						public void run() {
		//							progressBar.setProgress(progressStatus);						
		//						}
		//					});
		//				}
		//				
		//				//The progress is done
		//				if(progressStatus >= 100){
		//					//Sleep so the user can see the 100% mark
		//					try { Thread.sleep(1500);
		//					} catch (Exception e) {}
		//					progressBar.dismiss();
		//				}
		//			}
		//		}).start();
	}

	//	public BtArduinoService getService() {
	//		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	//		for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	//			if (BtArduinoService.class.getName().equals(service.service.getClassName())) {
	//				return service;
	//			}
	//		}
	//		return false;
	//	}

	@Override
	public void run() {
		//Fetch the BtArduinoService running the programmer thread
		//TODO: check if this works.
		final BtArduinoService service = (BtArduinoService) getSystemService(Context.ACTIVITY_SERVICE);
		
		Log.d(TAG, "Progress has entered run()");

		//This should run until the programmer has stopped
		while (service.isProgrammerRunning()) {
			//Fetch the current message from the service
			String message = service.getStateMessage();
			//Get the state of the programmer
			state = service.getProtocolState();

			//The actual progress should only be shown if the programmer is reading or writing
			if (state == ProtocolState.READING || state == ProtocolState.WRITING) {

				//Show the progress
				progressNumber = service.getProgress();
				progressHandler.post(new Runnable() {

					@Override
					public void run() {
						while (progressNumber <= 100){
							progressBar.setProgress(progressNumber);
							progressNumber = service.getProgress();

							if (progressNumber == 100) {
								//Sleep so the user can see the 100% mark
								try { Thread.sleep(1500);
								} catch (Exception e) {}
							}
							//Get the current state to check if it has changed
							state = service.getProtocolState();
							/*
							 * If the state is not reading or writing, the programmer
							 * has either encountered an error or it is finished. 
							 */
							if (state != ProtocolState.READING && state != ProtocolState.WRITING) {
								break;
							}
						}
					}
				});
			}

			//If an error was encountered
			if (state == ProtocolState.ERROR_CONNECT || state == ProtocolState.ERROR_PARSE_HEX
					|| state == ProtocolState.ERROR_READ || state == ProtocolState.ERROR_WRITE) {
				
				progressBar.setMessage(message);
				//Make the thread sleep for a short interval to show the message
				try { Thread.sleep(1500);
				} catch (Exception e) {}
				
				progressBar.dismiss();
				//Show the information box
				informationBox(true);
			}
			
			if (state == ProtocolState.FINISHED) {
				progressBar.setMessage(message);
				//Make the thread sleep for a short interval to show the message
				try { Thread.sleep(1500);
				} catch (Exception e) {}
				
				progressBar.dismiss();
				informationBox(false);
			}

			else {
				progressBar.setMessage(message);
			}

		}
	}

	private String getDeviceName() {
		if(Devices.isConnected()){
			SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
			return pref.getString("connected_device_name", "null");
		}
		return "no device";
	}
}
