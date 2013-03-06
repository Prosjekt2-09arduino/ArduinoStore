package no.group09.database;

public class Constants {

	/** Select an app from the database */
    protected final static String SELECT_APP = "select * from app where appid=?";
    protected final static String SELECT_DEVELOPER = "select * from developer where developerid=?";
    protected final static String SELECT_REQUIREMENTS = "select * from requirements where requirementid=?";
    protected final static String SELECT_PICTURES = "select * from pictures where pictureid=?";
    
    /** Insert an app to the database */
    protected final static String INSERT_APP = "insert or replace into app (name, rating, developerid, category, description) values (?, ?, ?, ?, ?)";
    protected final static String INSERT_DEVELOPER = "insert or replace into developer (name, website) values (?, ?)";
    protected final static String INSERT_REQUIREMENTS = "insert or replace into requirements (name, description) values (?, ?)";
    protected final static String INSERT_PICTURES = "insert or replace into pictures (appid, fileURL) values (?, ?)";

    /** Delete an app from the database */
    protected final static String DELETE_APP = "delete from app where appid=?";
    protected final static String DELETE_DEVELOPER = "delete from developer where developerid=?";
    protected final static String DELETE_REQUIREMENTS = "delete from requirements where requirementid=?";
    protected final static String DELETE_PICTURES = "delete from pictures where pictureid=?";
	
	/** app(appid, name, description, developerid, icon) */
	protected final static String APP_TABLE="app";
	protected final static String APP_ID="appid";
	protected final static String APP_NAME="name"; 
	protected final static String APP_RATING="rating"; 
	protected final static String APP_DEVELOPERID="developerid"; 
	protected final static String APP_CATEGORY="category"; 
	protected final static String APP_DESCRIPTION="description"; 
	
//	protected final static String APP_ICON="icon"; 
	
	/** developer(developerid, name, website) */
	protected final static String DEVELOPER_TABLE="developer";
	protected final static String DEVELOPER_ID="developerid";
	protected final static String DEVELOPER_NAME="name";
	protected final static String DEVELOPER_WEBSITE="website";
	
	/** pictures(pictureid, appid, fileURL) */
	protected final static String PICTURES_TABLE="pictures";
	protected final static String PICTURES_ID="pictureid";
	protected final static String PICTURES_APPID="appid";
	protected final static String PICTURES_FILEURL="fileURL";
	
	/** requirement(requirement, name, description) */
	protected final static String REQUIREMENTS_TABLE="requirements";
	protected final static String REQUIREMENTS_ID="requirementid";
	protected final static String REQUIREMENTS_NAME="name";
	protected final static String REQUIREMENTS_DESCRIPTION="description";
	
	/** appusespins(appid, requirementid) */
	protected final static String APPUSESPINS_TABLE="appusespins";
	protected final static String APPUSESPINS_APPID="appid";
	protected final static String APPUSESPINS_REQUIREMENTID="requirementid";
	
	/**	Database app creation sql statement */
	protected static final String DATABASE_CREATE_APP = 
			"CREATE TABLE IF NOT EXISTS app (" +
					"appid integer primary key autoincrement, " +
					"name varchar(160), " +
					"rating int(10), " +
					"developerid int(10)," +
					"category varchar(200)," +
					"description varchar(200))";
	
//	/**	Database app creation sql statement */
//	protected static final String DATABASE_CREATE_APP = 
//			"CREATE TABLE IF NOT EXISTS app (" +
//					"appid integer primary key autoincrement, " +
//					"name varchar(160), " +
//					"description varchar(200), " +
//					"developerid int(10), " +
//					"image BLOB)";

	/** Database developer creation sql statement */
	protected static final String DATABASE_CREATE_DEVELOPER = 
			"CREATE TABLE developer (" +
					"developerid integer primary key autoincrement, " +
					"name varchar(160), " +
					"website varchar(200))";
	
	/** Database pictures creation sql statement */
	protected static final String DATABASE_CREATE_PICTURES = 
			"CREATE TABLE pictures (" +
					"pictureid integer primary key autoincrement, " +
					"appid int(10), " +
					"fileURL varchar(200))";
	
	/** Database requirement creation sql statement */
	protected static final String DATABASE_CREATE_REQUIREMENTS = 
			"CREATE TABLE requirements (" +
					"requirementid integer primary key autoincrement, " +
					"name varchar(160), " +
					"description varchar(200))";
	
	/** Database appUsesPins creation sql statement */
	protected static final String DATABASE_CREATE_APPUSESPINS = 
				"CREATE TABLE appUsesPins (" +
						"appid int(10) , " +
						"requirementid int(10)" +
						"FOREIGN KEY (appid) REFERENCES app(appid)," +
						"FOREIGN KEY (requirementid) REFERENCES requirements(requirementid))";
	
	
}
