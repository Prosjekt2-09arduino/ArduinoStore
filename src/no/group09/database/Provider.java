package no.group09.database;

import org.societies.android.api.cis.SocialContract;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

public class Provider extends ContentProvider{

	private UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	private Save save = null;

	//The method addURI() maps an authority and path to an integer value.
	//The method match() returns the integer value for a URI.

	final int URI_MATCHER_INDEX_APP = 1;	//FIXME: SocialContract.UriMatcherIndex.APP
	final int URI_MATCHER_INDEX_APP_SHARP = 2;
	final int URI_MATCHER_INDEX_DEVELOPER = 3;
	final int URI_MATCHER_INDEX_DEVELOPER_SHARP = 4;
	final int URI_MATCHER_INDEX_REQUIREMENTS = 5;
	final int URI_MATCHER_INDEX_REQUIREMENTS_SHARP = 6;

	final int APP_ID = 7; //FIXME: SocialContract.App._ID
	final int DEVELOPER_ID = 8;
	final int REQUIREMENTS_ID = 9;

	String URI_PATH_INDEX_APP = "";	//FIXME: SocialContract.UriPathIndex.APP
	String URI_PATH_INDEX_DEVELOPER = "";
	String URI_PATH_INDEX_REQUIREMENTS = "";

	{
		sUriMatcher.addURI(SocialContract.AUTHORITY.getAuthority(),
				URI_PATH_INDEX_APP, URI_MATCHER_INDEX_APP);

		sUriMatcher.addURI(SocialContract.AUTHORITY.getAuthority(),
				URI_PATH_INDEX_DEVELOPER, URI_MATCHER_INDEX_DEVELOPER);

		sUriMatcher.addURI(SocialContract.AUTHORITY.getAuthority(),
				URI_PATH_INDEX_REQUIREMENTS, URI_MATCHER_INDEX_REQUIREMENTS);
	}


	@Override
	public boolean onCreate(){
		save = new Save(getContext());
		save.open();
		return true;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		String rowID = null;
		String selectionNew = null;
		int deleteCount = 0;

		switch (sUriMatcher.match(uri)){
		case URI_MATCHER_INDEX_APP:
			//To return the number of deleted items, you must
			//specify a where clause. To delete all rows and
			//return a value pass in "1":
			if (selection == null)
				selection = "1";
			deleteCount = save.deleteApp(selection, selectionArgs);
			//Inform resolvers about change:
			getContext().getContentResolver().notifyChange(uri, null);
			//Return number of rows updated:
			return deleteCount;

		case URI_MATCHER_INDEX_APP_SHARP:
			//If this is a # query, add row ID to the selection:
			rowID = uri.getPathSegments().get(1);
			selectionNew = SocialContract.Me._ID+" = "+ rowID
					+ (!TextUtils.isEmpty(selection) ?
							" AND (" + selection + ")" : "");
			deleteCount = save.deleteApp(selectionNew, selectionArgs);
			//Inform resolvers about change:
			getContext().getContentResolver().notifyChange(uri, null);
			//Return number of rows updated:
			return deleteCount;

		case URI_MATCHER_INDEX_DEVELOPER:
			//To return the number of deleted items, you must
			//specify a where clause. To delete all rows and
			//return a value pass in "1":
			if (selection == null)
				selection = "1";
			deleteCount = save.deleteDeveloper(selection, selectionArgs);
			//Inform resolvers about change:
			getContext().getContentResolver().notifyChange(uri, null);
			//Return number of rows updated:
			return deleteCount;

		case URI_MATCHER_INDEX_DEVELOPER_SHARP:
			//If this is a # query, add row ID to the selection:
			rowID = uri.getPathSegments().get(1);
			selectionNew = SocialContract.Me._ID+" = "+ rowID
					+ (!TextUtils.isEmpty(selection) ?
							" AND (" + selection + ")" : "");
			deleteCount = save.deleteDeveloper(selectionNew, selectionArgs);
			//Inform resolvers about change:
			getContext().getContentResolver().notifyChange(uri, null);
			//Return number of rows updated:
			return deleteCount;

		case URI_MATCHER_INDEX_REQUIREMENTS:
			//To return the number of deleted items, you must
			//specify a where clause. To delete all rows and
			//return a value pass in "1":
			if (selection == null)
				selection = "1";
			deleteCount = save.deleteRequirements(selection, selectionArgs);
			//Inform resolvers about change:
			getContext().getContentResolver().notifyChange(uri, null);
			//Return number of rows updated:
			return deleteCount;

		case URI_MATCHER_INDEX_REQUIREMENTS_SHARP:
			//If this is a # query, add row ID to the selection:
			rowID = uri.getPathSegments().get(1);
			selectionNew = SocialContract.Me._ID+" = "+ rowID
					+ (!TextUtils.isEmpty(selection) ?
							" AND (" + selection + ")" : "");
			deleteCount = save.deleteRequirements(selectionNew, selectionArgs);
			//Inform resolvers about change:
			getContext().getContentResolver().notifyChange(uri, null);
			//Return number of rows updated:
			return deleteCount;

		default:
			throw new IllegalArgumentException("Unsupported URI in SocialProvider delete method:" + uri);
		}


	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {

		int index = sUriMatcher.match(uri);
		long newID;
		Uri returnUri = null;

		switch(index){
		case URI_MATCHER_INDEX_APP:
			newID = save.insertApp(values);
			if (newID == -1)  //_values does not contain correct parameters.
				throw new IllegalArgumentException("Unsupported parameters for new app:" + values.toString());
			returnUri = Uri.withAppendedPath(uri, Long.toString(newID));
			break;

		case URI_MATCHER_INDEX_DEVELOPER:
			newID = save.insertDeveloper(values);
			if (newID == -1)  //_values does not contain correct parameters.
				throw new IllegalArgumentException("Unsupported parameters for new developer:" + values.toString());
			returnUri = Uri.withAppendedPath(uri, Long.toString(newID));
			break;

		case URI_MATCHER_INDEX_REQUIREMENTS:
			newID = save.insertRequirements(values);
			if (newID == -1)  //_values does not contain correct parameters.
				throw new IllegalArgumentException("Unsupported parameters for new requirements:" + values.toString());
			returnUri = Uri.withAppendedPath(uri, Long.toString(newID));
			break;
		default:
			throw new IllegalArgumentException("Unsupported URI sent to SocialProvider insert:" + uri);    	

		}

		//Inform content resolvers about changes:
		getContext().getContentResolver().notifyChange(returnUri, null);

		return returnUri;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		String rowID = null;

		//Switch on the name of the path used in the query:
		switch (sUriMatcher.match(uri)){

		case URI_MATCHER_INDEX_APP:
			return save.queryApp(projection, selection, selectionArgs, sortOrder);

		case URI_MATCHER_INDEX_APP_SHARP:
			//I have to set selection to exact row ID:
			rowID = uri.getPathSegments().get(1);
			return save.queryApp(projection, APP_ID+" = "+rowID, selectionArgs, sortOrder);

		case URI_MATCHER_INDEX_DEVELOPER:
			return save.queryDeveloper(projection, selection, selectionArgs, sortOrder);

		case URI_MATCHER_INDEX_DEVELOPER_SHARP:
			//I have to set selection to exact row ID:
			rowID = uri.getPathSegments().get(1);
			return save.queryDeveloper(projection, DEVELOPER_ID+" = "+rowID, selectionArgs, sortOrder);

		case URI_MATCHER_INDEX_REQUIREMENTS:
			return save.queryRequirements(projection, selection, selectionArgs, sortOrder);

		case URI_MATCHER_INDEX_REQUIREMENTS_SHARP:
			//I have to set selection to exact row ID:
			rowID = uri.getPathSegments().get(1);
			return save.queryRequirements(projection, REQUIREMENTS_ID+" = "+rowID, selectionArgs, sortOrder);

		default:
			throw new IllegalArgumentException("Unsupported URI in SocialProvider query method:" + uri);   			
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {

		int updateCount = 0;
		String rowID = null;
		String selectionNew = null;

		switch (sUriMatcher.match(uri)){
		case URI_MATCHER_INDEX_APP:
			//Call the right method with original parameters:
			updateCount = save.updateApp(values, selection, selectionArgs);
			//Inform resolvers about change:
			getContext().getContentResolver().notifyChange(uri, null);
			//Return number of rows updated:
			return updateCount;

		case URI_MATCHER_INDEX_APP_SHARP:
			//If this is a # query, add row ID to the selection:
			rowID = uri.getPathSegments().get(1);
			selectionNew = SocialContract.Me._ID+" = "+ rowID
					+ (!TextUtils.isEmpty(selection) ?
							" AND (" + selection + ")" : "");
			updateCount = save.updateApp(values, selectionNew, selectionArgs);
			//Inform resolvers about change:
			getContext().getContentResolver().notifyChange(uri, null);
			//Return number of rows updated:
			return updateCount;

			///////

		case URI_MATCHER_INDEX_DEVELOPER:
			//Call the right method with original parameters:
			updateCount = save.updateDeveloper(values, selection, selectionArgs);
			//Inform resolvers about change:
			getContext().getContentResolver().notifyChange(uri, null);
			//Return number of rows updated:
			return updateCount;

		case URI_MATCHER_INDEX_DEVELOPER_SHARP:
			//If this is a # query, add row ID to the selection:
			rowID = uri.getPathSegments().get(1);
			selectionNew = SocialContract.Me._ID+" = "+ rowID
					+ (!TextUtils.isEmpty(selection) ?
							" AND (" + selection + ")" : "");
			updateCount = save.updateDeveloper(values, selectionNew, selectionArgs);
			//Inform resolvers about change:
			getContext().getContentResolver().notifyChange(uri, null);
			//Return number of rows updated:
			return updateCount;

			/////

		case URI_MATCHER_INDEX_REQUIREMENTS:
			//Call the right method with original parameters:
			updateCount = save.updateRequirements(values, selection, selectionArgs);
			//Inform resolvers about change:
			getContext().getContentResolver().notifyChange(uri, null);
			//Return number of rows updated:
			return updateCount;

		case URI_MATCHER_INDEX_REQUIREMENTS_SHARP:
			//If this is a # query, add row ID to the selection:
			rowID = uri.getPathSegments().get(1);
			selectionNew = SocialContract.Me._ID+" = "+ rowID
					+ (!TextUtils.isEmpty(selection) ?
							" AND (" + selection + ")" : "");
			updateCount = save.updateRequirements(values, selectionNew, selectionArgs);
			//Inform resolvers about change:
			getContext().getContentResolver().notifyChange(uri, null);
			//Return number of rows updated:
			return updateCount;

		default:
			throw new IllegalArgumentException("Unsupported URI in SocialProvider update method:" + uri);    				
		}
	}
}