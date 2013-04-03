package no.group09.utils;

import no.group09.connection.BluetoothConnection;
import no.group09.connection.BluetoothConnection.ConnectionState;
import no.group09.connection.ConnectionListener;
import no.group09.protocol.STK500;
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
	private BluetoothConnection connection = null;
	private ConnectionListener connectionListener;
	private String macAddress;
	private static BtArduinoService btService;

	@Override
	public void onCreate() {
		super.onCreate();
//		sendData();
	}

	public void sendData(){
		//FIXME This is just to create the UML remove and replace with real STK500 later
		STK500 stk = new STK500();
		stk.transfer("");
	}

	private boolean connect() {

		Log.d(TAG, "Service started");
		if (macAddress != null) {
			Log.d(TAG, "The MAC address of the chosen device is: " + macAddress);
			try {
				connection = new BluetoothConnection(macAddress, getConnectionListener());
				connection.connect();
				return true;
			} catch (Exception e) {
				Log.d(TAG, "Could not connect to device.");
				e.printStackTrace();
				return false;
			}
		}
		return false;
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

			if(connection.getConnectionState() == ConnectionState.STATE_CONNECTED){
				connection.disconnect();
			}

			if(connection.getConnectionState() != ConnectionState.STATE_DISCONNECTED){
				Log.d(TAG, "State was not disconnected (onStartCommand())\nSetting state to Disconnected.");
				connection.disconnect();
			}

			Log.d(TAG, "Connection state: " + connection.getConnectionState());
		}

		setBtService(this);
		if(!connect()){
			Log.d(TAG, "trying connection for second time");
			try{
				Thread.sleep(10);
				connect();
			}catch(Exception e){
				
			}
		}

		//START_NOT_STICKY makes sure the service dies when the app is killed
		return START_NOT_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		connection.disconnect();
	}

	@Override
	public IBinder onBind(Intent intent) {
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
			@Override
			public void onConnect(BluetoothConnection bluetoothConnection) {
				Log.d(TAG, "Connected to: " + connection.toString());
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

