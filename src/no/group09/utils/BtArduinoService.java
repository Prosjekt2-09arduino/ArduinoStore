package no.group09.utils;

import no.group09.connection.BluetoothConnection;
import no.group09.connection.ConnectionListener;
import no.group09.connection.ConnectionMetadata;
import no.group09.connection.ConnectionMetadata.DefaultServices;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Service used to hold the bluetooth connection
 * 
 * @author JeppeE
 *
 */
public class BtArduinoService extends Service {

	private String TAG = "BtArduinoService";
	private BluetoothConnection connection;
	private ConnectionListener connectionListener;
	private String macAddress;

	//	public BtArduinoService() {
	//		onCreate();
	//		Log.d(TAG, "Service constructor");
	//	}
	//	
	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	private void connect() {
		/*
		ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
		List< ActivityManager.RunningTaskInfo > taskInfo = am.getRunningTasks(1);
		*/
		
		//Stygg hack, men det funker. Fix hvis du orker... (Good luck.)
//		Activity a = (Activity) Devices.getContext();
		
		
		Log.d(TAG, "Service started");
		Log.d(TAG, "MAC address: " + macAddress);
		if (macAddress != null) {
			Log.d(TAG, "The MAC address of the chosen device is: " + macAddress);
			try {
				connection = new BluetoothConnection(macAddress, (Activity)getBaseContext() , getConnectionListener());
			} catch (Exception e) {
				Log.d(TAG, "Could not connect to device.");
				e.printStackTrace();
			}

			connection.connect();
		}
		
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		this.macAddress = intent.getStringExtra(Devices.MAC_ADDRESS);
		Log.d(TAG, "Extra: " + macAddress);
		
		connect();
		
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		connection.disconnect();
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "Skjer det noe her?");
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * For debugging (?).
	 * @return 
	 */
	private ConnectionListener getConnectionListener() {
		if (connectionListener == null) {
			createConnectionListener();
		}
		return this.connectionListener;
	}

	private void createConnectionListener() {
		this.connectionListener = new ConnectionListener() {
			@Override
			public void onConnect(BluetoothConnection bluetoothConnection) {
				Log.d(TAG, "Connected to: " + connection.toString());

				//Add a button for every service found
				ConnectionMetadata meta = connection.getConnectionData();
				for(String service : meta.getServicesSupported()) {
					Integer pins[] = meta.getServicePins(service);

					//Pin controlled button
					if(pins.length > 0) {
						if(service.equals(DefaultServices.SERVICE_LED_LAMP.name()))  for(int pin : pins) Log.d(TAG, "LED pin: " + pin);
						if(service.equals(DefaultServices.SERVICE_VIBRATION.name()))  for(int pin : pins) Log.d(TAG, "VIBRATION pin " + pin);
						if(service.equals(DefaultServices.SERVICE_SPEAKER.name())) Log.d(TAG, "SPEAKER");
					}

					//LCD print screen
					else if(service.equals(DefaultServices.SERVICE_LCD_SCREEN.name())) Log.d(TAG, "LCD");
				}

			}

			@Override
			public void onConnecting(BluetoothConnection bluetoothConnection) {
				Log.d(TAG, "Connecting to BT");
			}

			@Override
			public void onDisconnect(BluetoothConnection bluetoothConnection) {
				Log.d(TAG, "Disconnected from BT");
			}
		};
	}
}

