/*
 * Copyright 2012 Anders Eie, Henrik Goldsack, Johan Jansen, Asbj�rn 
 * Lucassen, Emanuele Di Santo, Jonas Svarvaa, Bj�rnar H�kenstad Wold
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package no.group09.connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import no.group09.connection.BluetoothConnection.ConnectionState;
import android.util.Log;

/**
 * A package private class used by BluetoothConnection. Only one instance of this class should exist per
 * BluetoothConnection after connect() has been executed. The thread will try to connect to the
 * remote device through Bluetooth and set the BluetoothConnection state to STATE_CONNECTED and exit when
 * the connection is established successfully. A disconnect() on the BluetoothConnection will kill any
 * running ConnectionThread.
 */
class ConnectionThread extends Thread {	
	private final BluetoothConnection connection;
	private static final String TAG = "CONNECTIONTHREAD";
	private volatile boolean connectionSuccessful;

	/**
	 * Create new thread to connect with the remote device
	 * @param connection Which BluetoothConnection created this thread
	 * @throws IllegalArgumentException If the specified BluetoothConnection is already connected.
	 */
	public ConnectionThread(BluetoothConnection connection) throws IllegalArgumentException {
		this.connection = connection;
		connectionSuccessful = false;

		if( connection.getConnectionState() != ConnectionState.STATE_DISCONNECTED ) {
			throw new IllegalArgumentException("The specified BluetoothConnection is already connected!");
		}

		//Start connecting
		connection.setConnectionState(ConnectionState.STATE_CONNECTING);

		setDaemon(true);
		setName("Connection Thread: " + connection.device.getName() + " (" + connection.device.getAddress() + ")");
	}

	/**
	 * This thread monitors and polls for new data received from the remote device
	 */
	private class PollingThread extends Thread {
		@Override
		public void run() {			

			//Keep listening bytes from the stream
			while( connection.getConnectionState() == ConnectionState.STATE_CONNECTING ){
				try {
					int readByte = connection.input.read();
					if( readByte != -1 ) {
						connection.byteReceived( (byte)readByte );
					}
					else {
						try { 
							Thread.sleep(10); 
						} catch (InterruptedException ex) {}
					}
				} catch (IOException e) {
					Log.d(TAG, "Could not read byte from socket: " + e.getMessage());
					connection.disconnect();
				}			
			}

			Log.d(TAG, "Stopped polling for new data.");
		}
	}

	@Override
	public void run() {	
		long timeout;
		boolean discoveryMode = false;

		//Stop discovery when connecting
		while( connection.bluetooth.isDiscovering() ) {

			//wait until discovery has finished before connecting
			if(!discoveryMode) {
				Log.d(TAG, "BluetoothDevice is in discovery mode. Waiting for discovery to finish before connecting");
				discoveryMode = true;
			}

			//Wait 250 ms
			try { 
				wait(250); 
			}catch (Exception e) {
				//				Log.d(TAG, "error waiting for respond");
			}
		}

		//Open socket in new thread because socket.connect() is blocking
		Thread socketThread = new Thread(){
			@Override
			public void run() {
				try {
					connection.socket.connect();
					connectionSuccessful = true;
				} catch (IOException ex) {
					Log.d(TAG, "Unable to open socket: " + ex);
					connection.disconnect();
				}
			}
		};

		socketThread.start();	

		//30 extra seconds to respond if we are not paired already
		if(!connection.isPaired()) timeout = System.currentTimeMillis() + 30000;
		else					   timeout = System.currentTimeMillis() + 4000;

		//Wait until connection is successful or TIMEOUT milliseconds has passed
		while(!connectionSuccessful) {
			if(System.currentTimeMillis() > timeout) {
				Log.d(TAG, "Bluetooth socket connection timeout (remote device used too long time to respond)");
				connection.disconnect();
				return;
			}			
		}
		
		Log.d(TAG, "Bluetooth connection: wait for hardware reset processing before " +
				"requesting handshake...");
		//wait to allow hardware reset to take place before requesting handshake
		long now = System.currentTimeMillis();
		//TODO: Should probably be between 1 and 3 seconds
		while (System.currentTimeMillis() - now <= 3000) {
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {}
		}

		//Start the super protocol thread loop
		Log.d(TAG, "Basic connection established! Requesting Metadata to finish handshake procedure.");
		new Thread(connection).start();
		new PollingThread().start();

		//Check if we are connected properly by sending a metadata request
		try {
			connection.handshakeConnection();
			Log.d(TAG, "Successfully connected last handshake");
		} catch (TimeoutException e) {
			Log.d(TAG, "Failed to setup connection: Could not retrieve ConnectionMetadata (" + e + ")");
			connection.disconnect();
			return;
		}

		//We are now connected!
		connection.setConnectionState(ConnectionState.STATE_CONNECTED);	
	}

}