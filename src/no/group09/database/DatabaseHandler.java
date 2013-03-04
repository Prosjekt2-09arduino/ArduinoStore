package no.group09.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	public DatabaseHandler(Context context) {
		super(context, Db.DATABASE_NAME, null, Db.DATABASE_VERSION);
	}

	// Method is called during creation of the database
	@Override
	public void onCreate(SQLiteDatabase database) {

		database.execSQL(Constants.DATABASE_CREATE_APP);
//		database.execSQL(Constants.DATABASE_CREATE_VERSION);
//		database.execSQL(Constants.DATABASE_CREATE_RATINGS);
		database.execSQL(Constants.DATABASE_CREATE_DEVELOPER);
//		database.execSQL(Constants.DATABASE_CREATE_PICTURES);
//		database.execSQL(Constants.DATABASE_CREATE_HARDWARE);
//		database.execSQL(Constants.DATABASE_CREATE_PLATFORM);
//		database.execSQL(Constants.DATABASE_CREATE_BTDEVICES);
//		database.execSQL(Constants.DATABASE_CREATE_APPUSESPINS);
	}

	//This method is called when database is upgraded like modifying the table structure, adding constraints to database etc
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.d(DatabaseHandler.class.getName(), "Upgrading database from version " + 
				oldVersion + " to " + 
				newVersion + ", which will destroy all old data");

		database.execSQL("DROP TABLE IF EXISTS app");
//		database.execSQL("DROP TABLE IF EXISTS version");
//		database.execSQL("DROP TABLE IF EXISTS ratings");
		database.execSQL("DROP TABLE IF EXISTS developer");
//		database.execSQL("DROP TABLE IF EXISTS pictures");
//		database.execSQL("DROP TABLE IF EXISTS hardware");
//		database.execSQL("DROP TABLE IF EXISTS platform");
//		database.execSQL("DROP TABLE IF EXISTS btdevices");
//		database.execSQL("DROP TABLE IF EXISTS appUsesPins");

		onCreate(database);
	}
}