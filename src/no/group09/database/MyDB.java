//package no.group09.database;
//public class MyDB{  
//
//private MyDatabaseHelper dbHelper;  
//
//private SQLiteDatabase database;  
//
//public final static String EMP_TABLE="MyEmployees"; // name of table 
//
//public final static String EMP_ID="_id"; // id value for employee
//public final static String EMP_NAME="name";  // name of employee
///** 
// * 
// * @param context 
// */  
//public MyDB(Context context){  
//    dbHelper = new DatabaseHelper(context);  
//    database = dbHelper.getWritableDatabase();  
//}
//
//
//public long createRecords(String id, String name){  
//   ContentValues values = new ContentValues();  
//   values.put(EMP_ID, id);  
//   values.put(EMP_NAME, name);  
//   return database.insert(EMP_TABLE, null, values);  
//}    
//
//     public selectRecords() {
//       String[] cols = new String[] {EMP_ID, EMP_NAME};  
//       Cursor mCursor = database.query(true, EMP_TABLE,cols,null  
//            , null, null, null, null, null);  
//       if (mCursor != null) {  
//        mCursor.moveToFirst();  
//
//
//     }  
//     return mCursor; // iterate to get each value.
//}