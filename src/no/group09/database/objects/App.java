package no.group09.database.objects;

public class App {

	private int ID;
	private String name = "";
	private int rating;
	private int developerID;
	private byte[] icon;
	private String category = "";


	/** This is used when we fetch the objects from the database */ 
	App(int ID, String name, int rating, int developerID, byte[] icon){	//TODO: use this instead
		this.ID = ID;
		this.name = name;
		this.rating = rating;
		this.developerID = developerID;
		this.icon = icon;
	}

	/** This is used when we create new samples (not from db) */
	public App(String name, int rating, int developerID, String category){
		this.name = name;
		this.rating = rating;
		this.developerID = developerID;
		this.category = category;
	}

	/**
	 * @deprecated this is temporary, fix and use with byte[]
	 * @param name
	 * @param description
	 * @param developerID
	 */
	public App(int ID, String name, int rating, int developerID, String category){
		this.ID = ID;
		this.name = name;
		this.rating = rating;
		this.developerID = developerID;
		this.category = category;
	}

	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
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

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
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
