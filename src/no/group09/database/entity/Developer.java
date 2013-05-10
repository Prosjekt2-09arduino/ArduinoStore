package no.group09.database.entity;
/**
 * Contains information about the developer of an app
 */
public class Developer {

	private int id;
	private String name, website;
	
	/**
	 * Constructor to developer
	 * @param id - developerid from database
	 * @param name - name of developer
	 * @param website - website of developer
	 */
	public Developer(int id, String name, String website){
		this.id = id;
		this.name = name;
		this.website = website;
	}
	
	/**
	 * Constructor to developer when its not fetched from the database
	 * @param name - name of the developer
	 * @param website - website of the developer
	 */
	public Developer(String name, String website){
		this.name = name;
		this.website = website;
	}

	/**
	 * Get the id of the developer from the database
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set the developer id coresponding to the datbase developerid
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get the developer name
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the developer name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the developer website
	 * @return
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * Set the developer website
	 * @param website
	 */
	public void setWebsite(String website) {
		this.website = website;
	}
}
