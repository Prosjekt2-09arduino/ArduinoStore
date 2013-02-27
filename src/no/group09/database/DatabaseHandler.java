package no.group09.database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static final String TAG = "DatabaseHandler";
	private SQLiteDatabase myDataBase = null;
	private Context ctxt;

	public DatabaseHandler(Context context) {
		super(context, Db.DATABASE_NAME, null, Db.DATABASE_VERSION);
		this.ctxt = context;
	}

	// Method is called during creation of the database
	@Override
	public void onCreate(SQLiteDatabase database) {

		database.execSQL(Constants.DATABASE_CREATE_APP);
//		database.execSQL(Constants.DATABASE_CREATE_VERSION);
//		database.execSQL(Constants.DATABASE_CREATE_RATINGS);
//		database.execSQL(Constants.DATABASE_CREATE_DEVELOPER);
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
//		database.execSQL("DROP TABLE IF EXISTS developer");
//		database.execSQL("DROP TABLE IF EXISTS pictures");
//		database.execSQL("DROP TABLE IF EXISTS hardware");
//		database.execSQL("DROP TABLE IF EXISTS platform");
//		database.execSQL("DROP TABLE IF EXISTS btdevices");
//		database.execSQL("DROP TABLE IF EXISTS appUsesPins");

		onCreate(database);
	}

//	/**
//	 * Check if the database already exist to avoid re-copying the file each time you open the application.
//	 * @return true if it exists, false if it doesn't
//	 */
//	private boolean checkDataBase(){
//
//		SQLiteDatabase checkDB = null;
//
//		try{
//			String myPath = Db.DATABASE_PATH + Db.DATABASE_NAME;
//			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
//		}
//
//		catch(SQLiteException e){
//			Log.d(TAG, "database does't exist yet.");
//		}
//
//		if(checkDB != null){
//			checkDB.close();
//		}
//
//		return checkDB != null ? true : false;
//	}
//
//
//	@Override
//	public synchronized void close() {
//
//		if(myDataBase != null)
//			myDataBase.close();
//
//		super.close();
//	}
//
//	/**
//	 * Creates a empty database on the system and rewrites it with your own database.
//	 * */
//	public void createDataBase() throws IOException{
//
//		boolean dbExist = checkDataBase();
//
//		if(dbExist){
//			//do nothing - database already exist
//		}
//
//		else{
//
//			//By calling this method and empty database will be created into the default system path
//			//of your application so we are gonna be able to overwrite that database with our database.
//			this.getReadableDatabase();
//
//			try {
//				copyDataBase();
//			} 
//
//			catch (IOException e) {
//				throw new Error("Error copying database");
//			}
//		}
//	}
//
//	/**
//	 * Copies your database from your local assets-folder to the just created empty database in the
//	 * system folder, from where it can be accessed and handled.
//	 * This is done by transfering bytestream.
//	 * */
//	public void copyDataBase() throws IOException{
//
//		//Open your local db as the input stream
//		InputStream myInput = ctxt.getAssets().open(Db.DATABASE_NAME);
//
//		// Path to the just created empty db
//		String outFileName = Db.DATABASE_PATH + Db.DATABASE_NAME;
//
//		//Open the empty db as the output stream
//		OutputStream myOutput = new FileOutputStream(outFileName);
//
//		//transfer bytes from the inputfile to the outputfile
//		byte[] buffer = new byte[1024];
//		int length;
//
//		while ((length = myInput.read(buffer))>0){
//			myOutput.write(buffer, 0, length);
//		}
//
//		//Close the streams
//		myOutput.flush();
//		myOutput.close();
//		myInput.close();
//	}
}