package no.group09.utils;

import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import no.group09.ucsoftwarestore.R;
/**
 * 
 * Activity for displaying searchresults.
 * Currently not searching, just displays a toast of search term(s)
 * @author Wschive
 *
 */
public class SearchResults extends Activity{


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchresults);

		handleIntent(getIntent());


	}


	@Override
	/**
	 * Called if this activity is recalled. Makes sure there is not multiple instances of this activity on top of eachother.
	 */
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
			doSearch(query);
		}
	}
	//    public boolean onSearchRequested(){
	//    	doSearch()
	//    }

	/**
	 * Method that will do the search.
	 * Currently only shows a toast with query.
	 * @param query - String with searchquery
	 */
	private void doSearch(String query) {
		// TODO Auto-generated method stub
		Toast toast = Toast.makeText(getApplicationContext(), "You searched for " + query, Toast.LENGTH_LONG);
		toast.show();

	}

}
