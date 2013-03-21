package no.group09.database;

import no.group09.database.entity.App;
import no.group09.database.entity.Developer;
import no.group09.database.entity.Requirements;
/**
 * Constants for use in the database.
 * Strings which contain the extended SQL queries
 */
public class Constants {

	/** Select an app from the database */
    protected final static String SELECT_APP = "select * from app where appid=?";
    protected final static String SELECT_DEVELOPER = "select * from developer where developerid=?";
    protected final static String SELECT_REQUIREMENTS = "select * from requirements where requirementid=?";
    protected final static String SELECT_PICTURES = "select * from pictures where pictureid=?";
    
    /** Insert an app to the database */
    protected final static String INSERT_APP = "insert or replace into app (name, rating, developerid, category, description, requirementid) values (?, ?, ?, ?, ?, ?)";
    protected final static String INSERT_DEVELOPER = "insert or replace into developer (name, website) values (?, ?)";
    protected final static String INSERT_REQUIREMENTS = "insert or replace into requirements (name, description, compatible) values (?, ?, ?)";
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
	protected final static String APP_REQUIREMENTID="requirementid"; 
	
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
	protected final static String REQUIREMENTS_COMPATIBLE="compatible";
	
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
					"description varchar(200)," +
					"requirementid int(10))";

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
					"description varchar(200)," +
					"compatible varchar(10))";
	
	/** Database appUsesPins creation sql statement */
	protected static final String DATABASE_CREATE_APPUSESPINS = 
				"CREATE TABLE appUsesPins (" +
						"appid int(10) , " +
						"requirementid int(10)" +
						"FOREIGN KEY (appid) REFERENCES app(appid)," +
						"FOREIGN KEY (requirementid) REFERENCES requirements(requirementid))";

	/**
	 * Populates the database with some hardcoded examples
	 * @param save - the current save class
	 * @param useContentProvider - if you want to use content provider or not
	 */
	public static void populateDatabase(Save save) {
			//App(name, rating, developerid, category, description, requirementid)
			save.insertApp(new App("FunGame", 3, 1, "Games", "This describes this amazing, life changing app. yey!", 1));	
			save.insertApp(new App("Game", 4, 2, "Games", "This describes this amazing, life changing app. yey!", 2));	
			save.insertApp(new App("PlayTime", 2, 5, "Games", "This describes this amazing, life changing app. yey!", 2));	
			save.insertApp(new App("FunTime", 4, 4, "Games", "This describes this amazing, life changing app. yey!", 3));	
			save.insertApp(new App("PlayWithPlayers", 1, 2, "Games", "This describes this amazing, life changing app. yey!", 3));	
			
			save.insertApp(new App("Medic", 1, 1, "Medical", "This describes this amazing, life changing app. yey!", 2));	
			save.insertApp(new App("Medical", 6, 3, "Medical", "This describes this amazing, life changing app. yey!", 3));	
			save.insertApp(new App("Helper", 4, 5, "Medical", "This describes this amazing, life changing app. yey!", 1));	
			
			save.insertApp(new App("Tool", 5, 5, "Tools", "This describes this amazing, life changing app. yey!", 2));	
			save.insertApp(new App("ToolBox", 5, 3, "Tools", "This describes this amazing, life changing app. yey!", 3));	
			save.insertApp(new App("BoxTooler", 2, 1, "Tools", "This describes this amazing, life changing app. yey!", 3));	
			save.insertApp(new App("ToolTooler", 3, 1, "Tools", "This describes this amazing, life changing app. yey!", 1));	
			save.insertApp(new App("ScrewDriver", 4, 1, "Tools", "This describes this amazing, life changing app. yey!", 2));	
			
			save.insertApp(new App("Player", 4, 5, "Media", "This describes this amazing, life changing app. yey!", 1));	
			save.insertApp(new App("MusicP", 2, 2, "Media", "This describes this amazing, life changing app. yey!", 3));
			
			save.insertDeveloper(new Developer("Wilhelm", "www.lol.com"));
			save.insertDeveloper(new Developer("Robin", "www.haha.com"));
			save.insertDeveloper(new Developer("Jeppe", "www.hehe.com"));
			save.insertDeveloper(new Developer("Bjørn", "www.hoho.com"));
			save.insertDeveloper(new Developer("Ståle", "www.rofl.com"));
			save.insertDeveloper(new Developer("Nina", "www.kake.com"));
			
			save.insertRequirements(new Requirements("name", "description", true));
			save.insertRequirements(new Requirements("name", "desc..", true));
			save.insertRequirements(new Requirements("name", "desc..", false));
	}
}
