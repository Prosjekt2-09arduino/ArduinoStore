package no.group09.ucsoftwarestore;

import java.io.Serializable;
import no.group09.connection.BluetoothConnection;

public class ConnectionHolder implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BluetoothConnection connection;

	public ConnectionHolder() {
	}
	
	public ConnectionHolder (BluetoothConnection connection) {
		this.connection = connection;
	}
	
	public BluetoothConnection getConnection() {
		return this.connection;
	}
	
	public void setConnection(BluetoothConnection connection) {
		this.connection = connection;
	}
	
	
}
