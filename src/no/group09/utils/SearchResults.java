package no.group09.utils;

import no.group09.ucsoftwarestore.R;
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

public class SearchResults extends Activity{

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchresults);
		
        handleIntent(getIntent());
        
       
    }
	

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }
    }
//    public boolean onSearchRequested(){
//    	doSearch()
//    }

	private void doSearch(String query) {
		// TODO Auto-generated method stub
		Toast toast = Toast.makeText(getApplicationContext(), "You searched for " + query, Toast.LENGTH_LONG);
		toast.show();
		
	}

}
