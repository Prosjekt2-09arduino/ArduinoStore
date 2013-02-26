package no.group09.fragments;

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

import java.util.ArrayList;
import java.util.HashMap;

import no.group09.arduinoair.R;
import no.group09.arduinoair.R.id;
import no.group09.arduinoair.R.layout;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
 
public class MyFragment extends Fragment{
 
	private final String TAG = "MyFragment";
	
	private int mCurrentPage;
	private Activity activity;
	
	private ListView list;
    private ListAdapter adapter;
 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/** Getting the arguments to the Bundle object */
		Bundle data = getArguments();

		/** Getting integer data of the key current_page from the bundle */
//		mCurrentPage = data.getInt("current_page", 1);
	}
 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		final View v = inflater.inflate(R.layout.main, container,false);
//        activity = (Activity) v.getContext();
//        SharedPreferences prefs = activity.getSharedPreferences("no.group09.arduinoair", Context.MODE_PRIVATE);
		
		ArrayList<HashMap<String, String>> category_list = new ArrayList<HashMap<String, String>>();
		
		////////////////////////////////////////////////////////////////////////////////////
		//TODO: change this with SQLLite or something

		HashMap<String, String> map = new HashMap<String, String>();
		map.put(ListAdapter.KEY_ID, "1");
		map.put(ListAdapter.APP_NAME, "Something Cool");
		map.put(ListAdapter.DISTRIBUTOR, "Miskrosoft Corporation");
		map.put(ListAdapter.RATING, "***");
		category_list.add(map);
		
		map = new HashMap<String, String>();
		map.put(ListAdapter.KEY_ID, "2");
		map.put(ListAdapter.APP_NAME, "House Sofa App");
		map.put(ListAdapter.DISTRIBUTOR, "Appfel AS");
		map.put(ListAdapter.RATING, "****");
		category_list.add(map);
		
		map = new HashMap<String, String>();
		map.put(ListAdapter.KEY_ID, "3");
		map.put(ListAdapter.APP_NAME, "Where is my phonebook?");
		map.put(ListAdapter.DISTRIBUTOR, "Telemor");
		map.put(ListAdapter.RATING, "*****");
		category_list.add(map);
		
		map = new HashMap<String, String>();
		map.put(ListAdapter.KEY_ID, "4");
		map.put(ListAdapter.APP_NAME, "Phonebook 2000");
		map.put(ListAdapter.DISTRIBUTOR, "Nextcom");
		map.put(ListAdapter.RATING, "**");
		category_list.add(map);
		
		map = new HashMap<String, String>();
		map.put(ListAdapter.KEY_ID, "5");
		map.put(ListAdapter.APP_NAME, "Earth Control");
		map.put(ListAdapter.DISTRIBUTOR, "Obama");
		map.put(ListAdapter.RATING, "****");
		category_list.add(map);
		
		map = new HashMap<String, String>();
		map.put(ListAdapter.KEY_ID, "6");
		map.put(ListAdapter.APP_NAME, "Where is my house? Im drunk!");
		map.put(ListAdapter.DISTRIBUTOR, "Alcohol AS");
		map.put(ListAdapter.RATING, "*");
		category_list.add(map);
		
		map = new HashMap<String, String>();
		map.put(ListAdapter.KEY_ID, "7");
		map.put(ListAdapter.APP_NAME, "The coolest app");
		map.put(ListAdapter.DISTRIBUTOR, "Appelapp");
		map.put(ListAdapter.RATING, "*****");
		category_list.add(map);
		////////////////////////////////////////////////////////////////////////////////////
		
		list = (ListView)v.findViewById(R.id.list);
		
		// Getting adapter by passing xml data ArrayList
        adapter = new ListAdapter(v.getContext(), category_list);        
        list.setAdapter(adapter);

        // Click event for single list row
        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(view.getContext(), "You hit the button", Toast.LENGTH_SHORT).show();
			}
		});	
		
		return v;
	}
    
 
}
