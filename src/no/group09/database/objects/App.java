package no.group09.database.objects;

public class App {

	private int ID;
	private String name = "";
	private String description = "";
	private int developerID;
	private byte[] icon;

	/** This is used when we fetch the objects from the database */ 
	App(int ID, String name, String description, int developerID, byte[] icon){	//TODO: use this instead
		this.ID = ID;
		this.name = name;
		this.description = description;
		this.developerID = developerID;
		this.icon = icon;
	}

	/** This is used when we create new samples (not from db) */
	public App(String name, String description, int developerID){
		this.name = name;
		this.description = description;
		this.developerID = developerID;
	}

	/**
	 * @deprecated this is temporary, fix and use with byte[]
	 * @param name
	 * @param description
	 * @param developerID
	 */
	public App(int ID, String name, String description, int developerID){
		this.ID = ID;
		this.name = name;
		this.description = description;
		this.developerID = developerID;
	}

	public int getID(){
		return ID;
	}

	public void setID(int id){
		this.ID = id;
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

	public int getDeveloperID() {
		return developerID;
	}

	public void setDeveloperID(int developerID) {
		this.developerID = developerID;
	}

	public byte[] getIcon() {
		return icon;
	}

	public void setIcon(byte[] icon) {
		this.icon = icon;
	}
}
