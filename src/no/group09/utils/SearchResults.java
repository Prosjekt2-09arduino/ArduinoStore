package no.group09.utils;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import no.group09.database.Save;
import no.group09.database.entity.App;
import no.group09.fragments.ListAdapter;
import no.group09.ucsoftwarestore.R;
/**
 * 
 * Activity for displaying searchresults.
 * Currently not searching, just displays a toast of search term(s)
 * @author Wschive
 *
 */
public class SearchResults extends Activity{

	private ListView list;
	private ListAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		handleIntent(getIntent());
	}

	/**
	 * Called if this activity is recalled. Makes sure there is not multiple instances of this activity on top of eachother.
	 */
	@Override
	 protected void onNewIntent(Intent intent) {
		setIntent(intent);
		handleIntent(intent);
	}

	/**
	 * Checks if the intent is a search, if so calls for doSearch
	 * @param intent
	 */
	private void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			Log.d("SEARCH", "You searched for: " + query);
			doSearch(query);
		}
	}

	/**
	 * Method that will do the search.
	 * Currently only shows a toast with query.
	 * @param query - String with searchquery
	 */
	public void doSearch(String query){
		ArrayList<HashMap<String, String>> application_list = new ArrayList<HashMap<String, String>>();

		//This adds elements from the database to the listview
		Save save = new Save(getBaseContext());

		//Get the preference for hide_incompatible
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean hideIncompatible = sharedPrefs.getBoolean("hide_incompatible", false);

		//Iterate over all the apps from the local database
		for(App app : save.getAllApps()){

			//Check if apps with category games shall be listed
			if((app.getName()).toLowerCase().contains(query.toLowerCase())){

				//The app should not be shown if the user has hide_incompatible = true and the app is not compatible
				if(save.getRequirementsByID(app.getRequirementID()).isCompatible()||!hideIncompatible){

					//Put the app in a HashMap that will be used in ListAdapter
					HashMap<String, String >map = new HashMap<String, String>();
					map.put(ListAdapter.KEY_ID, String.valueOf(app.getID()));
					map.put(ListAdapter.APP_NAME, app.getName());
					map.put(ListAdapter.DISTRIBUTOR, save.getDeveloperByID(app.getDeveloperID()).getName());
					map.put(ListAdapter.RATING, String.valueOf(app.getRating()));
					application_list.add(map);
				}
			}
		}
		
		list = (ListView) findViewById(R.id.list);

		// Getting adapter by passing xml data ArrayList
		adapter = new ListAdapter(getBaseContext(), application_list);
		list.setAdapter(adapter);

		// Click event for single list row
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				//Create a new intent for the application view
				Intent intent = new Intent(view.getContext(), AppView.class);

				//Fetch the application ID
				int appID = Integer.parseInt(adapter.getID(position));

				//Give the intent a message (which application to retreive from the db)
				intent.putExtra("app", appID);

				//Start the activity
				startActivity(intent);
			}
		});	
	}
}
