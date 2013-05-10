package no.group09.database.entity;

import java.io.UnsupportedEncodingException;

/**
 * Object that contains a binary file (hex) that is runnable on arduinos
 * @author tordly
 *
 */
public class BinaryFile {

	private byte[] file;
	private int appID;
	
	/**
	 * Contsructor to binary fil
	 * @param appID - id to app
	 * @param BLOB - the arduino app as byte-array
	 */
	public BinaryFile(int appID, byte[] BLOB){
		this.appID = appID;
		this.file = BLOB;
	}
	
	/**
	 * Get the hex as byte array
	 * @return
	 */
	public byte[] getBinaryFile(){
		return this.file;
	}
	
	/**
	 * Get the application id
	 * @return
	 */
	public int getAppID(){
		return this.appID;
	}
	
	/**
	 * Set the application id
	 * @param appID
	 */
	public void setAppID(int appID){
		this.appID = appID;
	}
	
	/**
	 * Insert the binary fil as byte array
	 * @param BLOB
	 */
	public void insertBinaryFile(byte[] BLOB){
		this.file = BLOB;
	}
	
	/**
	 * Get the binary file as a string in UTF-8 format
	 * @return
	 */
	public String getBinaryFileAsString(){
		String text = "null";
		try {
			text = new String(file, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return text;
	}
}
