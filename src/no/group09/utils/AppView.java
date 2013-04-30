package no.group09.utils;

import no.group09.stk500_v1.STK500v1;
import no.group09.stk500_v1.STK500v1.ProtocolState;
import no.group09.ucsoftwarestore.R;
import no.group09.database.Save;
import no.group09.database.entity.App;
import no.group09.database.entity.BinaryFile;
import no.group09.database.entity.Developer;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
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
public class AppView extends Activity {

	ProgressDialog progressBar;
	private int progressNumber = 0;
	private Handler progressHandler;
	private Save save;
	private Context ctxt;
	private Thread progressbarThread;
	private ProtocolState state;
	private static final String TAG = "AppView";
	private BtArduinoService service;
	private String message = "";
	private AlertDialog installDialog, resultDialog;
	private byte[] byteArray;
	private AlertDialog.Builder responseDialog;
	private Activity activityRef;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Set the xml layout
		setContentView(R.layout.app_view);	

		ctxt = getBaseContext();
		activityRef = this;

		//Fetch the application ID from the intent
		int appID = getIntent().getExtras().getInt("app");

		//Get the database
		save = new Save(getBaseContext());

//		progressBar = new ProgressDialog(this);

		responseDialog = new AlertDialog.Builder(this);

//		byteArray = testProgrammer();


		//Fetch the application from the database
		App app = save.getAppByID(appID);
		Developer developer = save.getDeveloperByID(app.getDeveloperID());
		BinaryFile binaryfile = save.getBinaryFileByAppID(appID);
		final String blob = binaryfile.getBinaryFileAsString();
		
		byteArray = testProgrammer(binaryfile.getBinaryFileAsString());

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

	public byte[] testProgrammer(String hexData) {
				//	"3A	10	0000	00	0C9465000C948D000C948D000C948D0064" +
		hexData = "3A100000000C9461000C947E000C947E000C947E0095" +
				"3A100010000C947E000C947E000C947E000C947E0068" +
				"3A100020000C947E000C947E000C947E000C947E0058" +
				"3A100030000C947E000C947E000C947E000C947E0048" +
				"3A100040000C949D000C947E000C947E000C947E0019" +
				"3A100050000C947E000C947E000C947E000C947E0028" +
				"3A100060000C947E000C947E00000000002400270009" +
				"3A100070002A0000000000250028002B0000000000DE" +
				"3A1000800023002600290004040404040404040202DA" +
				"3A100090000202020203030303030301020408102007" +
				"3A1000A0004080010204081020010204081020000012" +
				"3A1000B0000007000201000003040600000000000029" +
				"3A1000C000000011241FBECFEFD8E0DEBFCDBF11E08E" +
				"3A1000D000A0E0B1E0EAE3F4E002C005900D92A230A6" +
				"3A1000E000B107D9F711E0A2E0B1E001C01D92AB3039" +
				"3A1000F000B107E1F70E940C020C941B020C94000063" +
				"3A100100008091000161E00E94B80168EE73E080E038" +
				"3A1001100090E00E94E5008091000160E00E94B8013B" +
				"3A1001200068EE73E080E090E00E94E5000895809121" +
				"3A10013000000161E00E94790108951F920F920FB6AD" +
				"3A100140000F9211242F933F938F939F93AF93BF935D" +
				"3A100150008091060190910701A0910801B0910901D9" +
				"3A1001600030910A010196A11DB11D232F2D5F2D375E" +
				"3A1001700020F02D570196A11DB11D20930A018093F7" +
				"3A10018000060190930701A0930801B09309018091A3" +
				"3A10019000020190910301A0910401B0910501019623" +
				"3A1001A000A11DB11D8093020190930301A09304014E" +
				"3A1001B000B0930501BF91AF919F918F913F912F9186" +
				"3A1001C0000F900FBE0F901F9018959B01AC017FB749" +
				"3A1001D000F8948091020190910301A0910401B091E3" +
				"3A1001E000050166B5A89B05C06F3F19F00196A11DDA" +
				"3A1001F000B11D7FBFBA2FA92F982F8827860F911D79" +
				"3A10020000A11DB11D62E0880F991FAA1FBB1F6A952F" +
				"3A10021000D1F7BC012DC0FFB7F894809102019091F5" +
				"3A100220000301A0910401B0910501E6B5A89B05C0AA" +
				"3A10023000EF3F19F00196A11DB11DFFBFBA2FA92FE5" +
				"3A10024000982F88278E0F911DA11DB11DE2E0880F08" +
				"3A10025000991FAA1FBB1FEA95D1F7861B970B885ED3" +
				"3A100260009340C8F2215030404040504068517C4F8C" +
				"3A10027000211531054105510571F60895789484B52D" +
				"3A10028000826084BD84B5816084BD85B5826085BD92" +
				"3A1002900085B5816085BDEEE6F0E080818160808378" +
				"3A1002A000E1E8F0E01082808182608083808181605B" +
				"3A1002B0008083E0E8F0E0808181608083E1EBF0E022" +
				"3A1002C000808184608083E0EBF0E0808181608083C6" +
				"3A1002D000EAE7F0E0808184608083808182608083AF" +
				"3A1002E0008081816080838081806880831092C100DA" +
				"3A1002F0000895CF93DF93482F50E0CA0186569F4F51" +
				"3A10030000FC0134914A575F4FFA018491882369F1C7" +
				"3A1003100090E0880F991FFC01E859FF4FA591B49117" +
				"3A10032000FC01EE58FF4FC591D491662351F42FB7CD" +
				"3A10033000F8948C91932F909589238C9388818923AD" +
				"3A100340000BC0623061F42FB7F8948C91932F909585" +
				"3A1003500089238C938881832B88832FBF06C09FB706" +
				"3A10036000F8948C91832B8C939FBFDF91CF9108954C" +
				"3A10037000482F50E0CA0182559F4FFC012491CA01C9" +
				"3A1003800086569F4FFC0194914A575F4FFA01349172" +
				"3A10039000332309F440C0222351F1233071F024307B" +
				"3A1003A00028F42130A1F0223011F514C02630B1F02C" +
				"3A1003B0002730C1F02430D9F404C0809180008F77B9" +
				"3A1003C00003C0809180008F7D8093800010C084B531" +
				"3A1003D0008F7702C084B58F7D84BD09C08091B00045" +
				"3A1003E0008F7703C08091B0008F7D8093B000E32FA2" +
				"3A1003F000F0E0EE0FFF1FEE58FF4FA591B4912FB71D" +
				"3A10040000F894662321F48C919095892302C08C91F5" +
				"3A10041000892B8C932FBF0895CF93DF930E943E01C9" +
				"3A100420000E949700C0E0D0E00E9480002097E1F396" +
				"3A0A0430000E940000F9CFF894FFCFFE" +
				"3A02043A000B00B5" +
				"3A00000001FF";

		//Convert string to byte array
		byte[] binaryFile = new byte[hexData.length() / 2];
		for(int i=0; i < hexData.length(); i+=2)
		{
			binaryFile[i/2] = Integer.decode("0x" + hexData.substring(i, i + 2)).byteValue();
		}
		return binaryFile;
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
			builder.setMessage("Press install to install this app to " + getDeviceName() + ".\n\n"+
					"Do not turn off Bluetooth or move away from the device.")
					.setPositiveButton("Install", new DialogInterface.OnClickListener(){

						@Override
						public void onClick(DialogInterface dialog, int which){

							service = BtArduinoService.getBtService();

							if (service != null) {
								Log.d(TAG, "Fetched service! Progress: " + service.getProgress());

								installDialog.dismiss();
								progressBar = new ProgressDialog(activityRef);
								progressHandler = new Handler();
								progressbarThread = new Thread(new ProgressBarUpdate());
								progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
								progressBar.setCancelable(false);
								progressBar.setTitle("Installing app to " + getDeviceName());
								progressBar.setMessage("Working");
								progressBar.setProgress(0);
								progressBar.setMax(100);

								setProgressBarVisibility(true);

								progressBar.show();

								Log.d(TAG, "Ready to start progressBar");
								installingDialog();

								//TODO: add call to the service for it to start the 
								//programmer here.


							}
							else {
								Log.d(TAG, "Service was null!");
							}
						}

					}).setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
						@Override
						public void onClick(DialogInterface dialog, int which){

						}
					}).show();
		}
		installDialog = builder.create();
		//		installDialog.show();
	}

	/**
	 * Method for showing the result of a programming attempt. Writes success
	 * message if it was successful or error message if not.
	 * 
	 * @param error Boolean for indicating if an error message or success message
	 * should be printed. True for error, false for success. 
	 */

	/**
	 * Dialog for displaying progressbar for installation purposes.
	 */
	public void installingDialog(){

		//		progressHandler = new Handler();
		//		progressbarThread = new Thread(new ProgressBarUpdate());
		//		progressBar = new ProgressDialog(this);
		//		progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		//		progressBar.setCancelable(false);
		//		progressBar.setTitle("Installing app to " + getDeviceName());
		//		progressBar.setMessage("First message.");
		//		progressBar.setProgress(0);
		//		progressBar.setMax(100);
		//		
		//		setProgressBarVisibility(true);
		//		
		//		progressBar.show();
		//		
		//		Log.d(TAG, "Ready to start progressBar");
		progressbarThread.start();

	}



	private String getDeviceName() {
		if(Devices.isConnected()){
			SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
			return pref.getString("connected_device_name", "null");
		}
		return "no device";
	}

	class ShowInformationBox implements Runnable {

		boolean error = false;

		public ShowInformationBox (boolean error) {
			this.error = error;
		}

		@Override
		public void run() {
			//Creates an alertdialog builder
			//			AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

			if (error) {
				//TODO: give better error message?
				responseDialog.setMessage("An error was encountered while trying to program your " +
						"device. Please try to install again.").setPositiveButton
						("OK", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								//								resultDialog.dismiss();
							}
						});
			}
			else {
				responseDialog.setMessage("You have successfully programmed your device!").setPositiveButton
				("OK", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						//						resultDialog.dismiss();
					}
				});
			}
			responseDialog.create();
			responseDialog.show();
		}

	}
	public Dialog createDialog(boolean error) {
		//		AlertDialog.Builder responseDialog = new AlertDialog.Builder(this);

		if (error) {
			responseDialog.setMessage("Error encountered")
			.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					//Nothing needs to be done, this closes the box
				}
			}).setCancelable(false);
		}

		return responseDialog.show();
	}

	class ProgressBarUpdate implements Runnable {

		@Override
		public void run() {
			Log.d(TAG, "Sending data to service");
			service.sendData(byteArray);
			Log.d(TAG, "Progress has entered run()");

			while (!service.isProgrammerRunning()){
				//				Log.d(TAG, "Programmer not running. Waiting");
			}

			//This should run until the programmer has stopped
			while (true) {
				//				Log.d(TAG, "Programmer is running");
				//Fetch the current message from the service
				message = service.getStateMessage();
				//Get the state of the programmer
				state = service.getProtocolState();

				//The actual progress should only be shown if the programmer is reading or writing
				if (state == ProtocolState.READING || state == ProtocolState.WRITING) {

					//Show the progress
					progressNumber = service.getProgress();

					if (progressNumber <= 100){
						progressNumber = service.getProgress();

						//Get the current state to check if it has changed
						progressHandler.post(new Runnable() {

							@Override
							public void run() {
								progressBar.setProgress(progressNumber);
								progressBar.setMessage(message);
							}
						});

						state = service.getProtocolState();
						/*
						 * If the state is not reading or writing, the programmer
						 * has either encountered an error or it is finished. 
						 */
						if (state != ProtocolState.READING && state != ProtocolState.WRITING) {
							//									break;
						}
					}
				}

				//If an error was encountered
				else if (state == ProtocolState.ERROR_CONNECT || state == ProtocolState.ERROR_PARSE_HEX
						|| state == ProtocolState.ERROR_READ || state == ProtocolState.ERROR_WRITE) {

					Log.d(TAG, "Error received.");
					progressHandler.post(new Runnable() {

						@Override
						public void run() {
							progressBar.setMessage(message);
						}
					});

					//Make the thread sleep for a short interval to show the message
					try { Thread.sleep(1500);
					} catch (Exception e) {}

					progressBar.dismiss();
					//Show the information box
					runOnUiThread(new ShowInformationBox(true));
					//					createDialog(true);
					break;
				}

				else if (state == ProtocolState.FINISHED) {

					progressHandler.post(new Runnable() {

						@Override
						public void run() {
							progressBar.setMessage(message);
						}
					});

					progressBar.dismiss();
					//Make the thread sleep for a short interval to show the message
					//					try { Thread.sleep(1500);
					//					} catch (Exception e) {}

					//					informationBox(false);
					break;
				}

				else {
					progressHandler.post(new Runnable() {
						
						@Override
						public void run() {
							progressBar.setMessage(message);
						}
					});
				}

			}
		}

	}
}
