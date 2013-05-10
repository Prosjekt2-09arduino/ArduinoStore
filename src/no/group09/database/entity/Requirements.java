package no.group09.database.entity;

/**
 * Requirements for specific app
 */
public class Requirements {

	private int id;
	private String name, description;
	private boolean compatible;
	
	/**
	 * This is used when we create an object that has records from the local database
	 * @param id - requirement id
	 * @param name - name of the requirement
	 * @param description - desc of the req
	 * @param compatible - TRUE or FALSE
	 */
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
	}
	
	/**
	 * This is used when we create the test-object to the local database
	 * @param name
	 * @param description
	 * @param compatible - true or false
	 */
	public Requirements(String name, String description, boolean compatible){
		this.name = name;
		this.description = description;
		this.compatible = compatible;
	}
	
	/**
	 * Get the compatible boolean as a string
	 * @return - "true" or "false"
	 */
	public String isCompatibleAsString(){
		if(this.compatible) return "true";
		else return "false";
	}

	/**
	 * Check if the requirement is compatible
	 * @return - true or false
	 */
	public boolean isCompatible() {
		return compatible;
	}

	/**
	 * Set the compatibility as boolean
	 * @param compatible - true or false
	 */
	public void setCompatible(boolean compatible) {
		this.compatible = compatible;
	}

	/**
	 * Get the requirement id
	 * @return - database id to requirement
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set the database id
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get the requirement name
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the requirement name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the description
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the description
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
