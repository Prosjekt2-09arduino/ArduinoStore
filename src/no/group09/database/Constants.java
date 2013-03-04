package no.group09.database;

public class Constants {

	/** Select an app from the database */
    protected final static String SELECT_APP = "select * from app where appid=?";
    protected final static String SELECT_DEVELOPER = "select * from developer where developerid=?";
    
    /** Insert an app to the database */
    protected final static String INSERT_APP = "insert or replace into app (name, rating, developerid, category) values (?, ?, ?, ?)";
    protected final static String INSERT_DEVELOPER = "insert or replace into app (name, rating, developerid, category) values (?, ?, ?, ?)";

    /** Delete an app from the database */
    protected final static String DELETE_APP = "delete from app where name=?";
    protected final static String DELETE_DEVELOPER = "delete from developer where name=?";
	
	/** app(appid, name, description, developerid, icon) */
	protected final static String APP_TABLE="app";
	protected final static String APP_ID="appid";
	protected final static String APP_NAME="name"; 
	protected final static String APP_RATING="rating"; 
	protected final static String APP_DEVELOPERID="developerid"; 
	protected final static String APP_CATEGORY="category"; 
//	protected final static String APP_ICON="icon"; 
	
	/** version(versionid, appid, version, fileURL, filesize) */
	protected final static String VERSION_TABLE="version";
	protected final static String VERSION_ID="versionid";
	protected final static String VERSION_APPID="appid";
	protected final static String VERSION_VERSION="version";
	protected final static String VERSION_FILEURL="fileURL";
	protected final static String VERSION_FILESIZE="filesize";
	
	/** ratings(ratingid, versionid, title, rating) */
	protected final static String RATINGS_TABLE="ratings";
	protected final static String RATINGS_ID="ratingid";
	protected final static String RATINGS_VERSIONID="versionid";
	protected final static String RATINGS_TITLE="title";
	protected final static String RATINGS_RATING="rating";
	
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
	
	/** hardware(hardwareid, name, description) */
	protected final static String HARDWARE_TABLE="hardware";
	protected final static String HARDWARE_ID="hardwareid";
	protected final static String HARDWARE_NAME="name";
	protected final static String HARDWARE_DESCRIPTION="description";
	
	/** platform(platformid, name, description, ramSize, romSize) */
	protected final static String PLATFORM_TABLE="platform";
	protected final static String PLATFORM_ID="platformid";
	protected final static String PLATFORM_NAME="name";
	protected final static String PLATFORM_DESCRIPTION="description";
	protected final static String PLATFORM_RAMSIZE="ramSize";
	protected final static String PLATFORM_ROMSIZE="romSize";
	
	/** btdevices(btdeviceid, name, mac-address, installedApp) */
	protected final static String BTDEVICES_TABLE="btdevices";
	protected final static String BTDEVICES_ID="btdeviceid";
	protected final static String BTDEVICES_NAME="name";
	protected final static String BTDEVICES_MACADDRESS="mac-address";
	protected final static String BTDEVICES_INSTALLEDAPP="installedApp";
	
	/** appusespins(appid, hardwareid) */
	protected final static String APPUSESPINS_TABLE="appusespins";
	protected final static String APPUSESPINS_APPID="appid";
	protected final static String APPUSESPINS_HARDWAREID="hardwareid";
	
	/**	Database app creation sql statement */
	protected static final String DATABASE_CREATE_APP = 
			"CREATE TABLE IF NOT EXISTS app (" +
					"appid integer primary key autoincrement, " +
					"name varchar(160), " +
					"rating int(10), " +
					"developerid int(10)," +
					"category varchar(200))";
	
	
//	/**	Database app creation sql statement */
//	protected static final String DATABASE_CREATE_APP = 
//			"CREATE TABLE IF NOT EXISTS app (" +
//					"appid integer primary key autoincrement, " +
//					"name varchar(160), " +
//					"description varchar(200), " +
//					"developerid int(10), " +
//					"image BLOB)";

	/** Database version creation sql statement */
	protected static final String DATABASE_CREATE_VERSION = 
			"CREATE TABLE version (" +
					"versionid integer primary key autoincrement, " +
					"version varchar(160), " +
					"fileURL varchar(200), " +
					"filesize int(10))";

	/** Database ratings creation sql statement */
	protected static final String DATABASE_CREATE_RATINGS = 
			"CREATE TABLE ratings (" +
					"ratingid integer primary key autoincrement, " +
					"title varchar(160), " +
					"comment varchar(200), " +
					"rating int(10))";
	
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
	
	/** Database hardware creation sql statement */
	protected static final String DATABASE_CREATE_HARDWARE = 
			"CREATE TABLE hardware (" +
					"hardwareid integer primary key autoincrement, " +
					"name varchar(160), " +
					"description varchar(200))";
	
	/** Database platform creation sql statement */
	protected static final String DATABASE_CREATE_PLATFORM = 
			"CREATE TABLE platform (" +
					"platformid integer primary key autoincrement, " +
					"name varchar(160), " +
					"description varchar(200)" +
					"ramSize int(100)" +
					"romSize int(100))";
	
	/** Database platform creation sql statement */
	protected static final String DATABASE_CREATE_BTDEVICES = 
			"CREATE TABLE platform (" +
					"btdeviceid integer primary key autoincrement, " +
					"name varchar(160), " +
					"mac-address varchar(200)" +
					"installedApp int(10))";
	
	/** Database appUsesPins creation sql statement */
	protected static final String DATABASE_CREATE_APPUSESPINS = 
				"CREATE TABLE appUsesPins (" +
						"appid int(10) , " +
						"hardwareid int(10)" +
						"FOREIGN KEY (appid) REFERENCES app(appid)," +
						"FOREIGN KEY (hardwareid) REFERENCES hardware(hardwareid))";
}
