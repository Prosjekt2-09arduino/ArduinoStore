package no.group09.database;

import java.util.ArrayList;
import java.util.HashMap;
import no.group09.database.entity.App;
import no.group09.database.entity.Developer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class Save{

	private static final String TAG = "Save";

	public SQLiteDatabase db;
	private DatabaseHandler dbHelper;
	private Context ctx;

	protected HashMap<String, Cursor> tables;

	public Save(Context context){
		dbHelper = new DatabaseHandler(context);
	}

	public void close(){
		dbHelper.close();
	}

	public void open(){
		db = dbHelper.getWritableDatabase();
	}
	
	/**
	 * Selects all the records in the database
	 * @return return a Cursor[] with all the values. Iterate over each cursor to get out the values.
	 * Check for each Cursor[i] if its is null!
	 */
	public HashMap<String, Cursor> selectRecords() {

		tables = new HashMap<String, Cursor>();
		Cursor tempCursor = null;

		//app(appid, name, description, developerid, icon) 
		String[] app = new String[] {Constants.APP_ID, Constants.APP_NAME, Constants.APP_RATING, Constants.APP_DEVELOPERID, Constants.APP_CATEGORY, Constants.APP_DESCRIPTION};  
		tempCursor = db.query(true, Constants.APP_TABLE, app, null, null, null, null, null, null); 
		if (tempCursor != null){
			tempCursor.moveToFirst();  
			tables.put("app", tempCursor);
			tempCursor = null;
		}

		//developer(developerid, name, website)
		String[] developer = new String[] {Constants.DEVELOPER_ID, Constants.DEVELOPER_NAME, Constants.DEVELOPER_WEBSITE};
		tempCursor = db.query(true, Constants.DEVELOPER_TABLE, developer, null, null, null, null, null, null); 
		if (tempCursor != null){
			tempCursor.moveToFirst(); 
			tables.put("developer", tempCursor);
			tempCursor = null;
		}
		
		//TODO: check if this is possible if the database is empty
//
//		//pictures(pictureid, appid, fileURL)
//		String[] pictures = new String[] {Constants.PICTURES_ID, Constants.PICTURES_APPID, Constants.PICTURES_FILEURL};
//		tempCursor = db.query(true, Constants.PICTURES_TABLE, pictures, null, null, null, null, null, null); 
//		if (tempCursor != null){
//			tempCursor.moveToFirst(); 
//			tables.put("pictures", tempCursor);
//			tempCursor = null;
//		}
//
//		//requirements(requirementid, name, description)
//		String[] requirements = new String[] {Constants.REQUIREMENTS_ID, Constants.REQUIREMENTS_NAME, Constants.REQUIREMENTS_DESCRIPTION};
//		tempCursor = db.query(true, Constants.REQUIREMENTS_TABLE, requirements, null, null, null, null, null, null); 
//		if (tempCursor != null){
//			tempCursor.moveToFirst();  
//			tables.put("requirements", tempCursor);
//			tempCursor = null;
//		}
//
//		//appusespins(appid, requirementid)
//		String[] appusespins = new String[] {Constants.APPUSESPINS_APPID, Constants.APPUSESPINS_REQUIREMENTID};
//		tempCursor = db.query(true, Constants.APPUSESPINS_TABLE, appusespins, null, null, null, null, null, null); 
//		if (tempCursor != null){
//			tempCursor.moveToFirst();  
//			tables.put("appusespins", tempCursor);
//			tempCursor = null;
//		}

		return tables;
	}

	/**
	 * Gets all the apps from the database. This requires selectRecords()
	 * @return Arraylist of <App>
	 */
	public synchronized ArrayList<App> getAllApps(){
		HashMap<String, Cursor> map = selectRecords();
		ArrayList<App> apps = new ArrayList<App>();
		
		Cursor cursor = map.get("app");
		
		while(cursor.isAfterLast() == false){
			apps.add(new App(
					cursor.getInt(0),
					cursor.getString(1),
					cursor.getInt(2),
					cursor.getInt(3),
					cursor.getString(4),
					cursor.getString(5)));
			cursor.moveToNext();
		}
		
		return apps;
	}
	
	/** Get the requested developer from the database */
	public synchronized Developer getDeveloper(int id){
		Cursor c = null;
		Developer developer = null;
		
		try{
			c = db.rawQuery(Constants.SELECT_DEVELOPER, new String[] {String.valueOf(id)});
			
			if(c.moveToFirst()){
				developer = new Developer(
						c.getInt(0),
						c.getString(1),
						c.getString(2));
			}
		}
		catch (SQLiteException e){ Log.e(TAG, e.getMessage());}
		
		catch (IllegalStateException e){ Log.e(TAG, e.getMessage());}
		
		finally { if (c != null) c.close();}
		
		return developer;
	}

	/** Get the the requested app from the database */
	public synchronized App getApp(int id) {

		Cursor c = null;
		App app = null;
		try {
			//			db = getDb();
			c = db.rawQuery(Constants.SELECT_APP, new String[] { String.valueOf(id) });

			if (c.moveToFirst()) {
				app = new App(
						c.getInt(0),
						c.getString(1),
						c.getInt(2),
						c.getInt(3),
						c.getString(4),
						c.getString(5));
			}
		}

		catch (SQLiteException e) { Log.e(TAG, e.getMessage()); }

		catch (IllegalStateException e) { Log.e(TAG, e.getMessage()); }

		finally { if (c != null) { c.close(); } }

		return app;
	}

	public void insertApp(App app) {

		//Get the database
		db = dbHelper.getWritableDatabase();

		try {
			if (db.isOpen()) {
				SQLiteStatement insertStmt = db.compileStatement(Constants.INSERT_APP);
				insertStmt.clearBindings();
				insertStmt.bindString(1, app.getName());
				insertStmt.bindString(2, String.valueOf(app.getRating()));
				insertStmt.bindString(3, String.valueOf(app.getDeveloperID()));
				insertStmt.bindString(4, app.getCategory());
				insertStmt.bindString(5, app.getDescription());
				insertStmt.executeInsert();
			}
		}

		catch (SQLiteException e) { Log.e(TAG, e.getMessage()); }

		finally { close(); }
	}
	
	public void deleteApp(App app){
		
	}
	
	public void insertDeveloper(Developer developer) {

		//Get the database
		db = dbHelper.getWritableDatabase();
		
		try {
			if (db.isOpen()) {
				SQLiteStatement insertStmt = db.compileStatement(Constants.INSERT_DEVELOPER);
				insertStmt.clearBindings();
				insertStmt.bindString(1, developer.getName());
				insertStmt.bindString(2, developer.getWebsite());
				insertStmt.executeInsert();
			}
		}

		catch (SQLiteException e) { Log.e(TAG, e.getMessage()); }

		finally { close(); }
	}

	/** Populates the database with some hardcoded examples */
	public synchronized void populateDatabase(){
//		ContentValues v = new ContentValues();
//		v.put("name", "FunGame");
//		v.put("rating", 3);
//		v.put("developerid", 1);
//		v.put("category", "Games");
//		v.put("description", "This describes this amazing, life changing app. yey!");
//		insertApp(v);
//		
//		v = new ContentValues();
//		v.put("name", "Medic");
//		v.put("rating", 1);
//		v.put("developerid", 2);
//		v.put("category", "Medical");
//		v.put("description", "This describes this amazing, life changing app. yey!");
//		insertApp(v);
//		
//		v = new ContentValues();
//		v.put("name", "PlayerX");
//		v.put("rating", 4);
//		v.put("developerid", 3);
//		v.put("category", "Media");
//		v.put("description", "This describes this amazing, life changing app. yey!");
//		insertApp(v);
//		
//		v = new ContentValues();
//		v.put("name", "Toolio");
//		v.put("rating", 3);
//		v.put("developerid", 4);
//		v.put("category", "Tools");
//		v.put("description", "This describes this amazing, life changing app. yey!");
//		insertApp(v);
//		
//		v = new ContentValues();
//		v.put("name", "Whilhelm");
//		v.put("website", "www.lol.com");
//		insertDeveloper(v);
//		
//		v = new ContentValues();
//		v.put("name", "Robin");
//		v.put("website", "www.haha.com");
//		insertDeveloper(v);
//		
//		v = new ContentValues();
//		v.put("name", "Jeppe");
//		v.put("website", "www.hehe.com");
//		insertDeveloper(v);
		
		insertApp(new App("FunGame", 3, 1, "Games", "This describes this amazing, life changing app. yey!"));	
		insertApp(new App("Game", 4, 2, "Games", "This describes this amazing, life changing app. yey!"));	
		insertApp(new App("PlayTime", 2, 5, "Games", "This describes this amazing, life changing app. yey!"));	
		insertApp(new App("FunTime", 4, 4, "Games", "This describes this amazing, life changing app. yey!"));	
		insertApp(new App("PlayWithPlayers", 1, 2, "Games", "This describes this amazing, life changing app. yey!"));	
		
		insertApp(new App("Medic", 1, 1, "Medical", "This describes this amazing, life changing app. yey!"));	
		insertApp(new App("Medical", 6, 3, "Medical", "This describes this amazing, life changing app. yey!"));	
		insertApp(new App("Helper", 4, 5, "Medical", "This describes this amazing, life changing app. yey!"));	
		
		insertApp(new App("Tool", 5, 5, "Tools", "This describes this amazing, life changing app. yey!"));	
		insertApp(new App("ToolBox", 5, 3, "Tools", "This describes this amazing, life changing app. yey!"));	
		insertApp(new App("BoxTooler", 2, 1, "Tools", "This describes this amazing, life changing app. yey!"));	
		insertApp(new App("ToolTooler", 3, 1, "Tools", "This describes this amazing, life changing app. yey!"));	
		insertApp(new App("ScrewDriver", 4, 1, "Tools", "This describes this amazing, life changing app. yey!"));	
		
		insertApp(new App("Player", 4, 5, "Media", "This describes this amazing, life changing app. yey!"));	
		insertApp(new App("MusicP", 2, 2, "Media", "This describes this amazing, life changing app. yey!"));
		
		insertDeveloper(new Developer("Wilhelm", "www.lol.com"));
		insertDeveloper(new Developer("Robin", "www.haha.com"));
		insertDeveloper(new Developer("Jeppe", "www.hehe.com"));
		insertDeveloper(new Developer("Bjørn", "www.hoho.com"));
		insertDeveloper(new Developer("Ståle", "www.rofl.com"));
		insertDeveloper(new Developer("Nina", "www.kake.com"));
		
		
	}
	

	//APP DATABASE FUNCTIONS
	public long insertApp(ContentValues values) {
		db = dbHelper.getWritableDatabase();
		return db.insert(Constants.APP_TABLE, null, values);
	}

	public Cursor queryApp(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		db = dbHelper.getWritableDatabase();
		return db.query(Constants.APP_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
	}

	public int updateApp(ContentValues values, String selection, String[] selectionArgs) {
		db = dbHelper.getWritableDatabase();
		return db.update(Constants.APP_TABLE, values, selection, selectionArgs);
	}

	public int deleteApp(String selection, String[] selectionArgs){
		db = dbHelper.getWritableDatabase();
		return db.delete(Constants.APP_TABLE, selection, selectionArgs);
	}
	
	//DEVELOPER DATABASE FUNCTIONS
	public long insertDeveloper(ContentValues values) {
		db = dbHelper.getWritableDatabase();
		return db.insert(Constants.DEVELOPER_TABLE, null, values);
	}
	
	public Cursor queryDeveloper(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		db = dbHelper.getWritableDatabase();
		return db.query(Constants.DEVELOPER_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
	}

	public int updateDeveloper(ContentValues values, String selection, String[] selectionArgs) {
		db = dbHelper.getWritableDatabase();
		return db.update(Constants.DEVELOPER_TABLE, values, selection, selectionArgs);
	}

	public int deleteDeveloper(String selection, String[] selectionArgs){
		db = dbHelper.getWritableDatabase();
		return db.delete(Constants.DEVELOPER_TABLE, selection, selectionArgs);
	}
	
	//REQUIREMENTS DATABASE FUNCTIONS
	public long insertRequirements(ContentValues values) {
		db = dbHelper.getWritableDatabase();
		return db.insert(Constants.REQUIREMENTS_TABLE, null, values);
	}
	
	public Cursor queryRequirements(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		db = dbHelper.getWritableDatabase();
		return db.query(Constants.REQUIREMENTS_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
	}

	public int updateRequirements(ContentValues values, String selection, String[] selectionArgs) {
		db = dbHelper.getWritableDatabase();
		return db.update(Constants.REQUIREMENTS_TABLE, values, selection, selectionArgs);
	}

	public int deleteRequirements(String selection, String[] selectionArgs){
		db = dbHelper.getWritableDatabase();
		return db.delete(Constants.REQUIREMENTS_TABLE, selection, selectionArgs);
	}
}