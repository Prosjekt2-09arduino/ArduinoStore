package no.group09.database.entity;

import no.group09.utils.BtArduinoService;
import no.group09.utils.Devices;

/**
 * Requirements for specific app
 */
public class Requirements {

	private int id;
	private String name, description;
	private boolean compatible;
	private String compatibleString;
	
	/** This is used when we create an object that has records from the local database */
	public Requirements(int id, String name, String description, String compatible){
		this.id = id;
		this.name = name;
		this.description = description;
		
		if(compatible.equals("true")){
			this.compatible = true;
		}
		else if(compatible.equals("false")){
			this.compatible = false;
		}
		
		this.compatibleString = compatible;
	}
	
	/**
	 * 
	 * @param uri - The uri on the connected device
	 * @return - true if its compatible with the app
	 */
	public boolean isCompatible(String uri){
		if(uri.equals(this.compatibleString))
			return true;
		
		return false;
	}
	
	/** This is used when we create the test-object to the local database */
	public Requirements(String name, String description, boolean compatible){
		this.name = name;
		this.description = description;
		this.compatible = compatible;
	}
	
	public String isCompatibleAsString(){
		if(this.compatible) return "true";
		else return "false";
	}

	public boolean isCompatible() {
//		return compatible;
		
		if(Devices.isConnected()){
			String URI = BtArduinoService.getBtService().getBluetoothConnection().getURI();
			if(isCompatible(URI)) return true;
			else return false;
		}
		return compatible;	//Dont use URI but the hardcoded true/false from database
	}

	public void setCompatible(boolean compatible) {
		this.compatible = compatible;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
