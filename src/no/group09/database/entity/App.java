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


	/**
	 * This is used when we create new samples (not from db)
	 * @param name - name of app
	 * @param rating - rating to app
	 * @param developerID - id to developer
	 * @param category - category to app
	 * @param description - description of app
	 * @param requirementID - requirement id
	 */
	public App(String name, int rating, int developerID, String category, String description, int requirementID){
		this.name = name;
		this.rating = rating;
		this.developerID = developerID;
		this.category = category;
		this.description = description;
		this.requirementID = requirementID;
	}

	/**
	 * This is used when we create a App that is fetched from the database (hence have a ID)
	 * @param ID - id to app from db
	 * @param name - name of app
	 * @param rating - rating to app
	 * @param developerID - id to developer
	 * @param category - category to app
	 * @param description - description of app
	 * @param requirementID - requirement id
	 */
	public App(int ID, String name, int rating, int developerID, String category, String description, int requirementID){
		this.ID = ID;
		this.name = name;
		this.rating = rating;
		this.developerID = developerID;
		this.category = category;
		this.description = description;
		this.requirementID = requirementID;
	}
	
	/**
	 * Get the requiremnt id
	 * @return
	 */
	public int getRequirementID() {
		return requirementID;
	}

	/**
	 * Set the requirement id
	 * @param requirementID
	 */
	public void setRequirementID(int requirementID) {
		this.requirementID = requirementID;
	}

	/**
	 * Get the description of the application
	 * @return
	 */
	public String getDescription(){
		return this.description;
	}
	
	/**
	 * Set the desc of the app
	 * @param description
	 */
	public void setDescription(String description){
		this.description = description;
	}

	/**
	 * Get the category belonging to this app
	 * @return
	 */
	public String getCategory() {
		return category;
	}
	
	/**
	 * Set the category to this app
	 * @param category
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Get the id of this app
	 * @return
	 */
	public int getID(){
		return ID;
	}

	/**
	 * Set the id to this app
	 * @param id
	 */
	public void setID(int id){
		this.ID = id;
	}

	/**
	 * Get the name of this app
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of this app
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the rating of this app
	 * @return
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * Set the rating of this app
	 * @param rating
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}

	/**
	 * Get the devleoper id that owns this app
	 * @return
	 */
	public int getDeveloperID() {
		return developerID;
	}

	/**
	 * Set the developer id to this app
	 * @param developerID
	 */
	public void setDeveloperID(int developerID) {
		this.developerID = developerID;
	}

	/**
	 * Get the icon as byte array
	 * @return
	 */
	public byte[] getIcon() {
		return icon;
	}

	/**
	 * Set the icon as byte array
	 * @param icon
	 */
	public void setIcon(byte[] icon) {
		this.icon = icon;
	}
}
