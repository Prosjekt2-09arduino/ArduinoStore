package no.group09.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "uCSoftwareStore";

	private static final int DATABASE_VERSION = 2;

	// Database app creation sql statement
	private static final String DATABASE_CREATE_APP = 
			"CREATE TABLE app (" +
					"appid integer primary key autoincrement, " +
					"name varchar(160), " +
					"description varchar(200), " +
					"developerid int(10), " +
					"image BLOB)";

	// Database version creation sql statement
	private static final String DATABASE_CREATE_VERSION = 
			"CREATE TABLE version (" +
					"versionid integer primary key autoincrement, " +
					"version varchar(160), " +
					"fileURL varchar(200), " +
					"filesize int(10))";

	// Database ratings creation sql statement
	private static final String DATABASE_CREATE_RATINGS = 
			"CREATE TABLE ratings (" +
					"ratingid integer primary key autoincrement, " +
					"title varchar(160), " +
					"comment varchar(200), " +
					"rating int(10))";
	
	// Database developer creation sql statement
	private static final String DATABASE_CREATE_DEVELOPER = 
			"CREATE TABLE developer (" +
					"developerid integer primary key autoincrement, " +
					"name varchar(160), " +
					"website varchar(200))";
	
	// Database pictures creation sql statement
	private static final String DATABASE_CREATE_PICTURES = 
			"CREATE TABLE pictures (" +
					"pictureid integer primary key autoincrement, " +
					"appid int(10), " +
					"fileURL varchar(200))";
	
	// Database hardware creation sql statement
	private static final String DATABASE_CREATE_HARDWARE = 
			"CREATE TABLE hardware (" +
					"hardwareid integer primary key autoincrement, " +
					"name varchar(160), " +
					"description varchar(200))";
	
	// Database platform creation sql statement
	private static final String DATABASE_CREATE_PLATFORM = 
			"CREATE TABLE platform (" +
					"platformid integer primary key autoincrement, " +
					"name varchar(160), " +
					"description varchar(200)" +
					"ramSize int(100)" +
					"romSize int(100))";
	
	// Database platform creation sql statement
	private static final String DATABASE_CREATE_BTDEVICES = 
			"CREATE TABLE platform (" +
					"btdeviceid integer primary key autoincrement, " +
					"name varchar(160), " +
					"mac-address varchar(200)" +
					"installedApp int(10))";
	
	// Database appUsesPins creation sql statement
		private static final String DATABASE_CREATE_APPUSESPINS = 
				"CREATE TABLE appUsesPins (" +
						"appid int(10) , " +
						"hardwareid int(10)" +
						"FOREIGN KEY (appid) REFERENCES app(appid)," +
						"FOREIGN KEY (hardwareid) REFERENCES hardware(hardwareid))";
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Method is called during creation of the database
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE_APP);
		database.execSQL(DATABASE_CREATE_VERSION);
		database.execSQL(DATABASE_CREATE_RATINGS);
		database.execSQL(DATABASE_CREATE_DEVELOPER);
		database.execSQL(DATABASE_CREATE_PICTURES);
		database.execSQL(DATABASE_CREATE_HARDWARE);
		database.execSQL(DATABASE_CREATE_PLATFORM);
		database.execSQL(DATABASE_CREATE_BTDEVICES);
		database.execSQL(DATABASE_CREATE_APPUSESPINS);
	}

	// Method is called during an upgrade of the database,
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.d(DatabaseHelper.class.getName(), "Upgrading database from version " + 
				oldVersion + " to " + 
				newVersion + ", which will destroy all old data");

		database.execSQL("DROP TABLE IF EXISTS app");
		database.execSQL("DROP TABLE IF EXISTS version");
		database.execSQL("DROP TABLE IF EXISTS ratings");
		database.execSQL("DROP TABLE IF EXISTS developer");
		database.execSQL("DROP TABLE IF EXISTS pictures");
		database.execSQL("DROP TABLE IF EXISTS hardware");
		database.execSQL("DROP TABLE IF EXISTS platform");
		database.execSQL("DROP TABLE IF EXISTS btdevices");
		database.execSQL("DROP TABLE IF EXISTS appUsesPins");

		onCreate(database);
	}
}