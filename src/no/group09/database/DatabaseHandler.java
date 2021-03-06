package no.group09.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Class for handling the database
 * This will create the tables or delete them on update
 */
public class DatabaseHandler extends SQLiteOpenHelper  {

	/** Name of the database file */
	public static final String DATABASE_NAME = "uCSoftwareStore.db";
	
	/** Path of the database */
	public static final String DATABASE_PATH = "/data/data/no.group09.ucsoftwarestore/databases/";
	
	/** Version of the database */
	public static final int DATABASE_VERSION = 22;	//If you change the database, increase this

	/**
	 * Constructor to database handler
	 * @param context - the context of the current activity
	 */
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}


	// Method is called during creation of the database
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(Constants.DATABASE_CREATE_APP);
		database.execSQL(Constants.DATABASE_CREATE_DEVELOPER);
		database.execSQL(Constants.DATABASE_CREATE_PICTURES);
		database.execSQL(Constants.DATABASE_CREATE_REQUIREMENTS);
		database.execSQL(Constants.DATABASE_CREATE_BINARYFILES);
	}

	//This method is called when database is upgraded like modifying the table structure, adding constraints to database etc
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		
		database.execSQL("DROP TABLE IF EXISTS app");
		database.execSQL("DROP TABLE IF EXISTS developer");
		database.execSQL("DROP TABLE IF EXISTS pictures");
		database.execSQL("DROP TABLE IF EXISTS requirements");
		database.execSQL("DROP TABLE IF EXISTS binaryfiles");

		onCreate(database);
	}
}