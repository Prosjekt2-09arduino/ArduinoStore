package no.group09.utils;

import java.io.InputStream;
import java.io.OutputStream;

import no.group09.connection.BluetoothConnection;
import no.group09.connection.BluetoothConnection.ConnectionState;
import no.group09.connection.ConnectionListener;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import no.group09.stk500_v1.Logger;
import no.group09.stk500_v1.STK500v1;
import no.group09.stk500_v1.STK500v1.ProtocolState;
import no.group09.utils.LogForProtocol;

/**
 * Service used to hold the Bluetooth connection
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
	private STK500v1 programmer;
	private byte[] hexFile;
	private Logger logger = new LogForProtocol();
	private Thread programmerHandlerThread;
	/** Used to check if the programmer is running */
	private volatile boolean checkState = false;
	/** Variable used to check the progress of the programming */
	private int progress = 0;
	/** Field used to contain the state of the programmer */
	private ProtocolState state;
	/** String used to print the message displayed to the user */
	private String stateMessage = "";
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		//		sendData();
	}

	/**
	 * Prepare the programmer with the provided file to program.
	 * @param hexFile As a byte array in Intel hex format.
	 */
	private void prepareProgrammer(byte[] hexFile) {
		if (programmer == null) {
			if (connection != null) {
				InputStream input = connection.getInputStream();
				OutputStream output = connection.getOutputStream();
				if (input == null || output == null) {
					logger.logcat("Sockets weren't ready", "w");
					return;
				}
				if (!connection.isConnected()) {
					logger.logcat("BtArduinoService.prepareProgrammer: Sockets ready, " +
							"but no connection prepared", "w");
					return;
				}

				this.hexFile = hexFile;
				programmer = new STK500v1(output, input, logger, hexFile);
				
				//The programmer is now running
				checkState = true;
				
			}
		} else {
			//TODO: Reset programmer
		}
	}

	private synchronized void checkProtocolState() {
		while (checkState) {
			
			setProtocolState(programmer.getProtocolState());
			ProtocolState state = getProtocolState();
			Log.d("AppView", "State fetched: " + state);
			
			switch(state) {
			case CONNECTING:
				setStateMessage("Trying to connect...");
				break;
			case ERROR_CONNECT:
				setStateMessage("An error was encountered while connecting");
				programmer.stopReadWrapper();
				checkState = false;
				break;
			case ERROR_PARSE_HEX:
				setStateMessage("Downloaded program was corrupted");
				checkState = false;
				break;
			case ERROR_READ:
				setStateMessage("Error while verifying program");
				checkState = false;
				break;
			case ERROR_WRITE:
				setStateMessage("Error while programming device");
				checkState = false;
				break;
			case FINISHED:
				setStateMessage("Finished programming");
				checkState = false;
				break;
			case INITIALIZING:
				setStateMessage("Initializing programmer");
				break;
			case READING:
				setStateMessage("Verifying program...");
				setProgress(programmer.getProgress());
				break;
			case WRITING:
				setProgress(programmer.getProgress());
				setStateMessage("Programming");
				break;
			case READY:
				/*
				 * Add functionality to check if the programmer is in this state too
				 * long. If this is the case something most likely is wrong.
				 * Could be if it e.g. is in ready state after three checks.
				 *
				 */
				setStateMessage("Programmer ready");
				break;
			default:
				Log.wtf("AppView", "Unknown state: " + state);
				checkState = false;
				break;
			}
			
			try {
				this.wait(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setStateMessage(String message) {
		this.stateMessage = message;
	}
	
	public synchronized String getStateMessage() {
		return stateMessage;
	}
	
	/**
	 * Sets the current state of the programmer. Should be called every time the
	 * state changes
	 * 
	 * @param state The new state of the programmer
	 */
	private void setProtocolState(ProtocolState state) {
		this.state = state;
	}
	
	/**
	 * Returns the current state of the programmer.
	 * 
	 * @return The current state of the programmer.
	 */
	public synchronized ProtocolState getProtocolState() {
		return this.state;
	}
	
	/**
	 * Checks if the programmer is running.
	 * 
	 * @return True if the programmer is running, false if not.
	 */
	public synchronized boolean isProgrammerRunning() {
		return checkState;
	}
	
	/**
	 * Sets the progress of the programming of the Arduino. Should be called
	 * every time there is an update with the progress.
	 * 
	 * @param progress The new progress. Must be an integer between 0 and 100.
	 */
	private void setProgress(int progress) {
		this.progress = progress;
	}
	
	/**
	 * Returns the progress of the programming of the Arduino
	 * 
	 * @return The total progress as an integer between 0 and 100
	 */
	public int getProgress() {
		return this.progress;
	}



	/**
	 * Check if the programmer is ready to be programmed.
	 * @return true if ready
	 */
	private boolean isProgrammerReady() {
		if (programmer != null) {
			//TODO Add sensible checks
			return true;
		}
		return false;
	}

	/**
	 * Program using the supplied hex file. Prepares the programmer if required.
	 * @param hexFile as byte array in Intel hex format.
	 */
	public void sendData(byte[] hexFile){
		if (!isProgrammerReady()) {
			programmerHandlerThread = new Thread(new ProgrammerHandler(hexFile));
			//Starting thread
			programmerHandlerThread.start();
			
		} else if (hexFile == this.hexFile) {
			//TODO: Ask programmer to program now
		} else {
			//programmer ready but a different file was prepared with it
			logger.logcat("BtService.sendData: Programmer was prepared with another" +
					" hex file. Sending aborted.", "i");
		}
	}
	
	class ProgrammerTask implements Runnable {

		@Override
		public void run() {
			//Start the programming. This does not return until the programmer is finished
			boolean result = programmer.programUsingOptiboot(true, 128);
			if (result) {
				Log.d(TAG, "programUsinOptiboot returned true");
			}
			else {
				//TODO: Handle the event of programming was unsuccessful.
				Log.d(TAG, "programUsinOptiboot returned false");
			}
		}
		
	}
	
	class ProgrammerHandler implements Runnable {
		byte[] hexFile;
		public ProgrammerHandler(byte[] hexFile) {
			this.hexFile = hexFile;
		}
		@Override
		public void run() {
			prepareProgrammer(hexFile);
			//Start blocking programming operation in another thread
			new Thread(new ProgrammerTask()).start();
			//Start checking the state of the programmer
			checkProtocolState();
		}
	}

	/**
	 * Get a connection with the Arduino. This is a Bluetooth connection with an added
	 * handshake communicating with the Arduino library.
	 * @return true if successful
	 */
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
			}

			Log.d(TAG, "Connection state: " + connection.getConnectionState());
		}

		setBtService(this);
		boolean connection = connect();

		Log.d(TAG, "connect() returned: " + connection); 

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

