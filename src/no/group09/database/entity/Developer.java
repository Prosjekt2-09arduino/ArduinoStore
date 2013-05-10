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
	 * @param name
	 * @param website
	 */
	public Developer(String name, String website){
		this.name = name;
		this.website = website;
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

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
}
