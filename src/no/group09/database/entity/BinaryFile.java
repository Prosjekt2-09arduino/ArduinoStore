package no.group09.database.entity;

import java.io.UnsupportedEncodingException;

public class BinaryFile {

	private byte[] file;
	private int appID;
	
	public BinaryFile(int appID, byte[] BLOB){
		this.appID = appID;
		this.file = BLOB;
	}
	
	public byte[] getBinaryFile(){
		return this.file;
	}
	
	public int getAppID(){
		return this.appID;
	}
	
	public void setAppID(int appID){
		this.appID = appID;
	}
	
	public void insertBinaryFile(byte[] BLOB){
		this.file = BLOB;
	}
	
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
