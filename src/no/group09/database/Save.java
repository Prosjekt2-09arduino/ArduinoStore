package no.group09.database;

import java.util.ArrayList;
import java.util.HashMap;
import no.group09.database.entity.App;
import no.group09.database.entity.Developer;
import no.group09.database.entity.Requirements;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
/**
 * Class for simpel communication with the database
 */
public class Save{

	private static final String TAG = "Save";

	public SQLiteDatabase db;
	private DatabaseHandler dbHelper;
	protected HashMap<String, Cursor> tables;

	public Save(Context context){
		dbHelper = new DatabaseHandler(context);
	}

	/**
	 * Populates the database with some hardcoded examples
	 * @param useContentProvider - if you want to use content provider or not
	 */
	public synchronized void populateDatabase(){
		Constants.populateDatabase(this);
	}

	/**
	 * Selects all the records for the applications in the database
	 * @return return a HashMap with Cursor's with all the values. Iterate over each cursor to get out the values.
	 * Check for each Cursor[i] if its is null!
	 */
	public HashMap<String, Cursor> selectAppRecords() {
		db = dbHelper.getWritableDatabase();
		tables = new HashMap<String, Cursor>();
		Cursor tempCursor = null;

		//app(appid, name, description, developerid, icon) 
		String[] app = new String[] {Constants.APP_ID, Constants.APP_NAME, Constants.APP_RATING, Constants.APP_DEVELOPERID, Constants.APP_CATEGORY, Constants.APP_DESCRIPTION, Constants.APP_REQUIREMENTID};  
		tempCursor = db.query(true, Constants.APP_TABLE, app, null, null, null, null, null, null); 

		if (tempCursor != null){
			tempCursor.moveToFirst();  
			tables.put("app", tempCursor);
		}

		db.close();
		
		return tables;
	}

	/**
	 * Gets all the apps from the database. This requires selectAppRecords()
	 * @return Arraylist of <App>
	 */
	public synchronized ArrayList<App> getAllApps(){
		//Gets a cursor for each app in the local database
		HashMap<String, Cursor> map = selectAppRecords();
		ArrayList<App> apps = new ArrayList<App>();
		Cursor cursor = map.get("app");

		while(cursor.isAfterLast() == false){
			apps.add(new App(
					cursor.getInt(0),		//appid
					cursor.getString(1),	//name
					cursor.getInt(2),		//rating
					cursor.getInt(3),		//developerid
					cursor.getString(4),	//category
					cursor.getString(5),	//description
					cursor.getInt(6)));		//requirementid
			cursor.moveToNext();
		}
		
		cursor.close();
		
		return apps;
	}

	/** Get the Developer from the local database by its ID */
	public synchronized Developer getDeveloperByID(int id){
		db = dbHelper.getWritableDatabase();
		Cursor c = null;
		Developer developer = null;

		try{
			c = db.rawQuery(Constants.SELECT_DEVELOPER, new String[] {String.valueOf(id)});

			if(c.moveToFirst()){
				developer = new Developer(
						c.getInt(0),		//developerid
						c.getString(1),		//name
						c.getString(2));	//website
			}
		}
		catch (SQLiteException e){ Log.e(TAG, e.getMessage());}
		catch (IllegalStateException e){ Log.e(TAG, e.getMessage());}
		finally { db.close(); if (c != null) c.close();}

		return developer;
	}

	/** Get the the Application from the local database by its ID */
	public synchronized App getAppByID(int id) {
		db = dbHelper.getWritableDatabase();
		Cursor c = null;
		App app = null;
		try {
			c = db.rawQuery(Constants.SELECT_APP, new String[] { String.valueOf(id) });

			if (c.moveToFirst()) {
				app = new App(
						c.getInt(0),		//appid
						c.getString(1),		//name
						c.getInt(2),		//rating
						c.getInt(3),		//developerid
						c.getString(4),		//category
						c.getString(5),		//description
						c.getInt(6));		//requirementid
			}
		}
		catch (SQLiteException e) { Log.e(TAG, e.getMessage()); }
		catch (IllegalStateException e) { Log.e(TAG, e.getMessage()); }
		finally { db.close(); if (c != null) { c.close(); } }

		return app;
	}

	/** Inserts an application to the local database */
	public void insertApp(App app) {
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
				insertStmt.bindString(6, String.valueOf(app.getRequirementID()));
				insertStmt.executeInsert();
			}
		}
		catch (SQLiteException e) { Log.e(TAG, e.getMessage()); }
		finally { db.close(); }
	}

	/** Inserts a developer to the local database */
	public void insertDeveloper(Developer developer) {
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
		finally { db.close(); }
	}

	/** Returns a Requirement-object with the parameter-ID from the local database */
	public synchronized Requirements getRequirementsByID(int id){
		db = dbHelper.getWritableDatabase();
		Cursor c = null;
		Requirements requirements = null;

		try{
			c = db.rawQuery(Constants.SELECT_REQUIREMENTS, new String[] {String.valueOf(id)});

			if(c.moveToFirst()){
				requirements = new Requirements(
						c.getInt(0),		//requirementid
						c.getString(1),		//name
						c.getString(2),		//description
						c.getString(3));	//compatible
			}
		}
		catch (SQLiteException e){ Log.e(TAG, e.getMessage());}
		catch (IllegalStateException e){ Log.e(TAG, e.getMessage());}
		finally { db.close(); if (c != null) c.close();}

		return requirements;
	}
	
	public synchronized void insertRequirements(Requirements req){
		db = dbHelper.getWritableDatabase();

		try {
			if (db.isOpen()) {
				SQLiteStatement insertStmt = db.compileStatement(Constants.INSERT_REQUIREMENTS);
				insertStmt.clearBindings();
				insertStmt.bindString(1, req.getName());
				insertStmt.bindString(2, req.getDescription());
				insertStmt.bindString(3, req.isCompatibleAsString());
				insertStmt.executeInsert();
			}
		}
		catch (SQLiteException e) { Log.e(TAG, e.getMessage()); }
		finally { db.close(); }
	}
}