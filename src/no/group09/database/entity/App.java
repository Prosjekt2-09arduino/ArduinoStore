package no.group09.database.entity;
/**
 * Main app information in the database
 */
public class App {

	private int ID;
	private String name = "";
	private int rating;
	private int developerID;
	private byte[] icon;
	private String category = "";
	private String description = "";
	private int requirementID;


	/** This is used when we create new samples (not from db) */
	public App(String name, int rating, int developerID, String category, String description, int requirementID){
		this.name = name;
		this.rating = rating;
		this.developerID = developerID;
		this.category = category;
		this.description = description;
		this.requirementID = requirementID;
	}

	/** This is used when we create a App that is fetched from the database (hence have a ID) */
	public App(int ID, String name, int rating, int developerID, String category, String description, int requirementID){
		this.ID = ID;
		this.name = name;
		this.rating = rating;
		this.developerID = developerID;
		this.category = category;
		this.description = description;
		this.requirementID = requirementID;
	}
	
	public int getRequirementID() {
		return requirementID;
	}

	public void setRequirementID(int requirementID) {
		this.requirementID = requirementID;
	}

	public String getDescription(){
		return this.description;
	}
	
	public void setDescription(String description){
		this.description = description;
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
