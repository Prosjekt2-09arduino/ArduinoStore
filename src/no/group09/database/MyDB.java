package no.group09.database;

import java.io.IOException;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class MyDB{  

	private DatabaseHelper dbHelper;  
	private SQLiteDatabase database;  
	
    public static final String PATH = "/data/data/no.group09.network/files/";
    public static final String DB_SERVICES = "services.db";
    public static final String DB_PROBES = "probes.db";
    public static final String DB_NIC = "nic.db";
    public static final String DB_SAVES = "saves.db";

	/** 
	 * 
	 * @param context 
	 */  
	public MyDB(Context context){  
		dbHelper = new DatabaseHelper(context);  
		database = dbHelper.getWritableDatabase(); 
		
	        dbHelper = new DatabaseHelper(context);
	 
	        try {
	 
	        	dbHelper.createDataBase();
	 
	 	} catch (IOException ioe) {
	 
	 		throw new Error("Unable to create database");
	 
	 	}
	 
	 	try {
	 
	 		dbHelper.openDataBase();
	 
	 	}catch(SQLException sqle){
	 
	 		throw sqle;
	 
	 	}
	}

	public long createRecords(String id, String name){  
		ContentValues values = new ContentValues();  
		values.put(Constants.APP_ID, id);  
		values.put(Constants.APP_NAME, name);  
		return database.insert(Constants.APP_TABLE, null, values);  
	}    

	/**
	 * Selects all the records in the database
	 * @return return a Cursor[] with all the values. Iterate over each cursor to get out the values.
	 * Check for each Cursor[i] if its is null!
	 */
	public Cursor[] selectRecords() {
		
		Cursor mCursor[] = new Cursor[9];

		//app(appid, name, description, developerid, icon) 
		String[] app = new String[] {Constants.APP_ID, Constants.APP_NAME, Constants.APP_DESCRIPTION, Constants.APP_DEVELOPERID, Constants.APP_ICON};  
		mCursor[1] = database.query(true, Constants.APP_TABLE, app, null, null, null, null, null, null); 
		if (mCursor[1] != null) mCursor[1].moveToFirst();  
		
		//version(versionid, appid, version, fileURL, filesize)
		String[] version = new String[] {Constants.VERSION_ID, Constants.VERSION_APPID, Constants.VERSION_VERSION, Constants.VERSION_FILEURL, Constants.VERSION_FILESIZE};
		mCursor[2] = database.query(true, Constants.VERSION_TABLE, version, null, null, null, null, null, null); 
		if (mCursor[2] != null) mCursor[2].moveToFirst();  
		
		//ratings(ratingid, versionid, title, rating)
		String[] ratings = new String[] {Constants.RATINGS_ID, Constants.RATINGS_VERSIONID, Constants.RATINGS_TITLE, Constants.RATINGS_RATING};
		mCursor[3] = database.query(true, Constants.RATINGS_TABLE, ratings, null, null, null, null, null, null); 
		if (mCursor[3] != null) mCursor[3].moveToFirst();  
		
		//developer(developerid, name, website)
		String[] developer = new String[] {Constants.DEVELOPER_ID, Constants.DEVELOPER_NAME, Constants.DEVELOPER_WEBSITE};
		mCursor[4] = database.query(true, Constants.DEVELOPER_TABLE, developer, null, null, null, null, null, null); 
		if (mCursor[4] != null) mCursor[4].moveToFirst(); 
		
		//pictures(pictureid, appid, fileURL)
		String[] pictures = new String[] {Constants.PICTURES_ID, Constants.PICTURES_APPID, Constants.PICTURES_FILEURL};
		mCursor[5] = database.query(true, Constants.PICTURES_TABLE, pictures, null, null, null, null, null, null); 
		if (mCursor[5] != null) mCursor[5].moveToFirst();  		
		
		//hardware(hardwareid, name, description)
		String[] hardware = new String[] {Constants.HARDWARE_ID, Constants.HARDWARE_NAME, Constants.HARDWARE_DESCRIPTION};
		mCursor[6] = database.query(true, Constants.HARDWARE_TABLE, hardware, null, null, null, null, null, null); 
		if (mCursor[6] != null) mCursor[6].moveToFirst();  
		
		//platform(platformid, name, description, ramSize, romSize)
		String[] platform = new String[] {Constants.PLATFORM_ID, Constants.PLATFORM_NAME, Constants.PLATFORM_DESCRIPTION, Constants.PLATFORM_RAMSIZE, Constants.PLATFORM_ROMSIZE};
		mCursor[7] = database.query(true, Constants.PLATFORM_TABLE, platform, null, null, null, null, null, null); 
		if (mCursor[7] != null) mCursor[7].moveToFirst();  
		
		//btdevices(btdeviceid, name, mac-address, installedApp)
		String[] btdevices = new String[] {Constants.BTDEVICES_ID, Constants.BTDEVICES_NAME, Constants.BTDEVICES_MACADDRESS, Constants.BTDEVICES_INSTALLEDAPP};
		mCursor[8] = database.query(true, Constants.BTDEVICES_TABLE, btdevices, null, null, null, null, null, null); 
		if (mCursor[8] != null) mCursor[8].moveToFirst();  		
		
		//appusespins(appid, hardwareid)
		String[] appusespins = new String[] {Constants.APPUSESPINS_APPID, Constants.APPUSESPINS_HARDWAREID};
		mCursor[9] = database.query(true, Constants.APPUSESPINS_TABLE, appusespins, null, null, null, null, null, null); 
		if (mCursor[9] != null) mCursor[9].moveToFirst();  

		return mCursor;
	}
}