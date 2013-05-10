package no.group09.utils;

import java.io.InputStream;
import java.io.OutputStream;
import no.group09.connection.BluetoothConnection;
import no.group09.connection.BluetoothConnection.ConnectionState;
import no.group09.connection.ConnectionListener;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import no.group09.stk500_v1.Logger;
import no.group09.stk500_v1.STK500v1;
import no.group09.stk500_v1.STK500v1.ProtocolState;
import no.group09.utils.LogForProtocol;
import no.group09.utils.AppView.ProgressbarHandler;

/**
 * Service used to hold the Bluetooth connection and handle the programming of
 * a device. Used to continuously check the state of the programmer.
 */
public class BtArduinoService extends Service {

	private String TAG = "BtArduinoService";
	private Logger logger = new LogForProtocol();
	private BluetoothConnection connection = null;
	private ConnectionListener connectionListener;
	private String macAddress;
	private static BtArduinoService btService;
	private STK500v1 programmer;
	private byte[] hexFile;
	private Thread programmerHandlerThread;
	/** Used to check if the programmer is running */
	private volatile boolean checkState = false;
	/** Variable used to check the progress of the programming */
	private int newProgress = 0;
	/** Instance of the handler that handles the progress bar while programming */
	private ProgressbarHandler handler;
	/**
	 * Number of bytes that is to be programmed each round of the programming.
	 * Recommended is 128, but can also be set to any even number below 257.
	 */
	private static final int BYTES_PROGRAMMED_EACH_ROUND = 128;

	@Override
	public void onCreate() {
		super.onCreate();
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

				newProgress = 0;

				this.hexFile = hexFile;
				programmer = new STK500v1(output, input, logger, hexFile);

				//The programmer is now running. Start checking its state
				checkState = true;
			}
		} else {
			//Reset the programmer
			programmer = null;
			prepareProgrammer(hexFile);
		}
	}

	/**
	 * Method continuously checking the state of the programmer and updating
	 * the message that is to be displayed in the progress bar. 
	 */
	private synchronized void checkProtocolState() {

		ProtocolState oldState = null;
		boolean updateProgressBar = false;
		int oldProgress = 0;

		//While the programmer is running
		while (checkState) {

			//Get the updated state
			ProtocolState newState = programmer.getProtocolState();
			//Get the updated progress
			newProgress = programmer.getProgress();

			//If the state or progress has changed since last iteration the 
			//progress bar needs to be updated.
			if (oldState != newState || oldProgress != newProgress) updateProgressBar = true;

			switch(newState) {
			case CONNECTING:
				if (updateProgressBar) {
					stateUpdateProgressBar("Trying to connect...", false);
					oldState = newState;
				}
				break;
			case ERROR_CONNECT:
				if (updateProgressBar) {
					stateUpdateProgressBar("An error was encountered while connecting", true);
					oldState = newState;
				}
				checkState = false;
				break;
			case ERROR_PARSE_HEX:
				if (updateProgressBar) {
					stateUpdateProgressBar("Downloaded program was corrupted", true);
					oldState = newState;
				}
				checkState = false;
				break;
			case ERROR_READ:
				if (updateProgressBar) {
					stateUpdateProgressBar("Error while verifying program", true);
					oldState = newState;
				}
				checkState = false;
				break;
			case ERROR_WRITE:
				if (updateProgressBar) {
					stateUpdateProgressBar("Error while programming device", true);
					oldState = newState;
				}
				checkState = false;
				break;
			case FINISHED:
				if (updateProgressBar) {
					stateUpdateProgressBar("Finished programming", true);
					oldState = newState;
				}
				checkState = false;
				break;
			case INITIALIZING:
				if (updateProgressBar) {
					stateUpdateProgressBar("Initializing programmer", false);
					oldState = newState;
				}
				break;
			case READING:
				if (updateProgressBar) {
					stateUpdateProgressBarProgress("Verifying program...", newProgress);
					oldState = newState;
					oldProgress = newProgress;
				}
				break;
			case WRITING:
				if (updateProgressBar) {
					stateUpdateProgressBarProgress("Programming...", newProgress);
					oldState = newState;
					oldProgress = newProgress;
				}
				break;
			case READY:
				if (updateProgressBar) {
					stateUpdateProgressBar("Programmer ready", false);
					oldState = newState;
				}
				break;
			default:
				Log.wtf("AppView", "Unknown state: " + newState);
				checkState = false;
				break;
			}
			try {
				this.wait(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Method called when the progress bar of the installation needs to be 
	 * updated with new a progress number.
	 * 
	 * @param message The message to be displayed in the installation progress window
	 * @param progress The new progress number
	 */
	private void stateUpdateProgressBarProgress(String message, int progress) {
		Message msg = Message.obtain(handler, 0, 1, progress, message);
		msg.sendToTarget();
	}
	
	/**
	 * Method called when the message in the installation progress window needs
	 * to be updated.
	 * 
	 * @param message - The new message to be displayed in the installation 
	 * progress window.
	 * @param hide boolean indicating if the installation progress window should
	 * be hidden after this message is set or not. True if the window should
	 * be dismissed, false if only the message should be updated and the windwow
	 * should persist.
	 */
	private void stateUpdateProgressBar(String message, boolean hide) {
		int hideWindow;
		if (hide) hideWindow = 0;
		else hideWindow = 1;
		Message msg = Message.obtain(handler, 0, hideWindow, newProgress, message);
		msg.sendToTarget();
	}

	/**
	 * Check if the programmer is ready to be programmed.
	 * 
	 * @return true if ready, false if not.
	 */
	private boolean isProgrammerReady() {
		if (programmer != null) {
			ProtocolState state = programmer.getProtocolState();
			if (state == ProtocolState.READY) {
				return true;
			} else if (state == ProtocolState.ERROR_CONNECT || state == ProtocolState.ERROR_PARSE_HEX
					|| state == ProtocolState.ERROR_READ || state == ProtocolState.ERROR_WRITE) {
				//Delete the programmer 
				programmer = null;
				return false;
			}
		}
		return false;
	}

	/**
	 * Program the connected device using the supplied hex file. Prepares the
	 * programmer for programming if required.
	 * 
	 * @param hexFile the file that is to be programmed. Must be a byte array
	 * in Intel hex format.
	 */
	public void sendData(byte[] hexFile, ProgressbarHandler handler){
		if (!isProgrammerReady()) {
			this.handler = handler;
			programmerHandlerThread = new Thread(new ProgrammerHandler(hexFile));
			//Starting thread
			programmerHandlerThread.start();
		} else {
			//programmer ready but a different file was prepared with it
			logger.logcat("BtService.sendData: Programmer was prepared with another" +
					" hex file. Sending aborted.", "i");
		}
	}

	/**
	 * Runnable for doing the actual programming in a separate thread.
	 */
	class ProgrammerTask implements Runnable {

		@Override
		public void run() {
			//Start the programming. This does not return until the programmer is finished
			boolean result = programmer.programUsingOptiboot(false, BYTES_PROGRAMMED_EACH_ROUND);
			if (result) {
				Log.d(TAG, "programUsinOptiboot returned true");
			}
			else {
				//Programming was unsuccessful. Error message will be displayed to the user
				Log.d(TAG, "programUsinOptiboot returned false");
			}
		}
	}

	/**
	 * Separate thread for initiating and polling of status updates from the 
	 * programming
	 */
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
	 * 
	 * @return true if successfully connected
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
	 * 
	 * @return The active BluetoothConnection. Null if there is no active connection
	 */
	public BluetoothConnection getBluetoothConnection() {
		return connection;
	}

	/**
	 * Gets this service
	 * 
	 * @return The active service. Null if no service is active.
	 */
	public static BtArduinoService getBtService() {
		return btService;
	}

	/**
	 * Sets this service to the active service.
	 * @param btService the active serivce.
	 */
	private static void setBtService(BtArduinoService btService) {
		BtArduinoService.btService = btService;
	}

	/**
	 * Method automatically called by the system when a service is started.
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		Log.d(TAG, "onStartCommand called");
		this.macAddress = intent.getStringExtra(Devices.MAC_ADDRESS);

		//Disconnect from the previous device before creating a new connection
		if (getBluetoothConnection() != null) {

			if(connection.getConnectionState() == ConnectionState.STATE_CONNECTED){
				connection.disconnect();
			}

			if(connection.getConnectionState() != ConnectionState.STATE_DISCONNECTED){
				Log.d(TAG, "State was not disconnected (onStartCommand())");
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
		//Disconnect from the connected device when the service is killed.
		connection.disconnect();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	/**
	 * Returns the current connection listener.
	 * 
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

