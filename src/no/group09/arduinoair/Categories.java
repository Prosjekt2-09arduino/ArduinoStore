package no.group09.arduinoair;

/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView;
import android.widget.Toast;

public class Categories extends MyFragment{

	private int mCurrentPage;
	private Activity activity;
	private Fragment fragment;
	
	ListView list;
    LazyAdapter adapter;
    Context ctx;
	
	static final String KEY_ID = "id";
	static final String APP_NAME = "title";
	static final String DISTRIBUTOR = "artist";
	static final String RATING = "duration";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/** Getting the arguments to the Bundle object */
		Bundle data = getArguments();

		/** Getting integer data of the key current_page from the bundle */
		mCurrentPage = data.getInt("current_page", 0);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		final View v = inflater.inflate(R.layout.main, container,false);
//		Intent myIntent = new Intent(v.getContext(), something.class);
		
		ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
		
		//This is all the elements in the list. TODO: change this with SQLLite or something
		for (int i = 0; i < 10; i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(KEY_ID, i + "KEY_ID");
			map.put(APP_NAME, "APP " + i);
			map.put(DISTRIBUTOR, i + " Corporation");
			map.put(RATING, i + " Stars");
			songsList.add(map);
		}
		
		list = (ListView)v.findViewById(R.id.list);
		
		// Getting adapter by passing xml data ArrayList
        adapter = new LazyAdapter(v.getContext(), songsList);        
        list.setAdapter(adapter);

        // Click event for single list row
        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(view.getContext(), "WOW, nice try!", Toast.LENGTH_SHORT).show();
			}
		});	
		
		return v;
	}
}