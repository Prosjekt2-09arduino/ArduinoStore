package no.group09.utils;

import java.util.List;

import no.group09.connection.BluetoothConnection;
import no.group09.connection.ConnectionListener;
import no.group09.connection.ConnectionMetadata;
import no.group09.connection.ConnectionMetadata.DefaultServices;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
	private static BtArduinoService btService;

	@Override
	public void onCreate() {
		super.onCreate();
	}

	private void connect() {

		Log.d(TAG, "Service started");
		if (macAddress != null) {
			Log.d(TAG, "The MAC address of the chosen device is: " + macAddress);
			try {
				connection = new BluetoothConnection(macAddress, getConnectionListener());
				connection.connect();
			} catch (Exception e) {
				Log.d(TAG, "Could not connect to device.");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Gets the active connection
	 * @return The active BluetoothConnection. Null if there is no active connection
	 */
	public BluetoothConnection getBluetoothConnection() {
		return connection;
	}

	/**
	 * Gets this service
	 * @return The active service
	 */
	public static BtArduinoService getBtService() {
		return btService;
	}

	private static void setBtService(BtArduinoService btService) {
		BtArduinoService.btService = btService;
	}

	@Override
	/**
	 * Method automatically called by the system when a service is started.
	 */
	public int onStartCommand(Intent intent, int flags, int startId) {

		Log.d(TAG, "onStartCommand called");
		this.macAddress = intent.getStringExtra(Devices.MAC_ADDRESS);
		
		//Disconnect from the previous device before creating a new connection
		if (getBluetoothConnection() != null) {
			Log.d(TAG, "The connection was not null");
			connection.disconnect();
			Log.d(TAG, "Connection state: " + connection.getConnectionState());
		}

		setBtService(this);
		connect();

		//START_NOT_STICKY makes sure the service dies when the app is killed
		return START_NOT_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "Service onDestroy() called");
		connection.disconnect();
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "Skjer det noe her?");
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns the current connection listener.
	 * @return The current connection listener. Creates a new one if it is null
	 */
	private ConnectionListener getConnectionListener() {
		if (connectionListener == null) {
			createConnectionListener();
		}
		return this.connectionListener;
	}

	/**
	 * Creates a new connection listener.
	 */
	private void createConnectionListener() {
		this.connectionListener = new ConnectionListener() {
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
