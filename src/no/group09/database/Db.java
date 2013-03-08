package no.group09.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class Db{  

	public static final String TAG = "Db";
	public static final String DATABASE_NAME = "uCSoftwareStore.db";
	public static final String DATABASE_PATH = "/data/data/no.group09.ucsoftwarestore/databases/";
	public static final int DATABASE_VERSION = 2;
	
	public Db(){  
	}
	
    public static SQLiteDatabase openDb(String db_name, int flags) {
        try {
            return SQLiteDatabase.openDatabase(DATABASE_PATH + db_name, null, flags);
        }
        
        catch (SQLiteException e) {
            Log.e(TAG, e.getMessage());
        }
        
        return null;
    }
}