/*
 * Licensed to UbiCollab.org under one or more contributor
 * license agreements.  See the NOTICE file distributed 
 * with this work for additional information regarding
 * copyright ownership. UbiCollab.org licenses this file
 * to you under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package no.group09.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import no.group09.database.Save;
import no.group09.database.objects.App;
import no.group09.ucsoftwarestore.MainActivity;
import no.group09.ucsoftwarestore.R;
import no.group09.utils.AppView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class TopHits extends Fragment{

	private int mCurrentPage;
	private ListView list;
	private ListAdapter adapter;
	private View view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/** Getting the arguments to the Bundle object */
		Bundle data = getArguments();

		/** Getting integer data of the key current_page from the bundle */
		mCurrentPage = data.getInt("current_page", 3);
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.main, container,false);

		update();
		
		return view;
	}
	
	/** Update the list-content */
	public void update(){
		ArrayList<HashMap<String, String>> application_list = new ArrayList<HashMap<String, String>>();

		//This adds elements from the database to the listview
		Save save = new Save(this.view.getContext());
		save.open();

		//Iterate over all the applications in the database
		for(App app : save.getAllApps()){

			if(app.getCategory().equals("Games") && MainActivity.pagerAdapter.page2 == Page.GAMES_MOST_POPULAR || MainActivity.pagerAdapter.page2 == Page.TOPHITS){
				HashMap<String, String >map = new HashMap<String, String>();
				map.put(ListAdapter.KEY_ID, String.valueOf(app.getID()));
				map.put(ListAdapter.APP_NAME, app.getName());
				map.put(ListAdapter.DISTRIBUTOR, save.getDeveloper(app.getDeveloperID()).getName());	//Fetch the developer from the ID
				map.put(ListAdapter.RATING, String.valueOf(app.getRating()));
				application_list.add(map);
			}
			else if(app.getCategory().equals("Medical") && MainActivity.pagerAdapter.page2 == Page.MEDICAL_MOST_POPULAR || MainActivity.pagerAdapter.page2 == Page.TOPHITS){
				HashMap<String, String >map = new HashMap<String, String>();
				map.put(ListAdapter.KEY_ID, String.valueOf(app.getID()));
				map.put(ListAdapter.APP_NAME, app.getName());
				map.put(ListAdapter.DISTRIBUTOR, save.getDeveloper(app.getDeveloperID()).getName());
				map.put(ListAdapter.RATING, String.valueOf(app.getRating()));
				application_list.add(map);
			}
			else if(app.getCategory().equals("Tools") && MainActivity.pagerAdapter.page2 == Page.TOOLS_MOST_POPULAR || MainActivity.pagerAdapter.page2 == Page.TOPHITS){
				HashMap<String, String >map = new HashMap<String, String>();
				map.put(ListAdapter.KEY_ID, String.valueOf(app.getID()));
				map.put(ListAdapter.APP_NAME, app.getName());
				map.put(ListAdapter.DISTRIBUTOR, save.getDeveloper(app.getDeveloperID()).getName());
				map.put(ListAdapter.RATING, String.valueOf(app.getRating()));
				application_list.add(map);
			}
			else if(app.getCategory().equals("Media") && MainActivity.pagerAdapter.page2 == Page.MEDIA_MOST_POPULAR || MainActivity.pagerAdapter.page2 == Page.TOPHITS){
				HashMap<String, String >map = new HashMap<String, String>();
				map.put(ListAdapter.KEY_ID, String.valueOf(app.getID()));
				map.put(ListAdapter.APP_NAME, app.getName());
				map.put(ListAdapter.DISTRIBUTOR, save.getDeveloper(app.getDeveloperID()).getName());
				map.put(ListAdapter.RATING, String.valueOf(app.getRating()));
				application_list.add(map);
			}
		}

		list = (ListView)this.view.findViewById(R.id.list);

		// Getting adapter by passing xml data ArrayList
		adapter = new ListAdapter(this.view.getContext(), application_list);        
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