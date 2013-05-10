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
import no.group09.ucsoftwarestore.MainActivity;
import no.group09.ucsoftwarestore.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Fragment with Category view
 */
public class Categories extends Fragment{

	private ListView list;
	private ListAdapterCategory adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		//Put the view inside the owner (parent view)
		final View v = inflater.inflate(R.layout.main, container,false);

		//Arraylist of categories
		ArrayList<HashMap<String, String>> category_list = new ArrayList<HashMap<String, String>>();

		//Put category GAMES with its data inside a HashMap
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(ListAdapterCategory.KEY_ID, "1");
		map.put(ListAdapterCategory.APP_NAME, "Games");
		map.put(ListAdapterCategory.DISTRIBUTOR, "");
		map.put(ListAdapterCategory.RATING, "");
		category_list.add(map);

		//Put category MEDICAL with its data inside a HashMap
		map = new HashMap<String, String>();
		map.put(ListAdapterCategory.KEY_ID, "2");
		map.put(ListAdapterCategory.APP_NAME, "Medical");
		map.put(ListAdapterCategory.DISTRIBUTOR, "");
		map.put(ListAdapterCategory.RATING, "");
		category_list.add(map);

		//Put category TOOLS with its data inside a HashMap
		map = new HashMap<String, String>();
		map.put(ListAdapterCategory.KEY_ID, "3");
		map.put(ListAdapterCategory.APP_NAME, "Tools");
		map.put(ListAdapterCategory.DISTRIBUTOR, "");
		map.put(ListAdapterCategory.RATING, "");
		category_list.add(map);

		//Put category MEDIA with its data inside a HashMap
		map = new HashMap<String, String>();
		map.put(ListAdapterCategory.KEY_ID, "4");
		map.put(ListAdapterCategory.APP_NAME, "Media");
		map.put(ListAdapterCategory.DISTRIBUTOR, "");
		map.put(ListAdapterCategory.RATING, "");
		category_list.add(map);

		//Put category ALL with its data inside a HashMap
		map = new HashMap<String, String>();
		map.put(ListAdapterCategory.KEY_ID, "5");
		map.put(ListAdapterCategory.APP_NAME, "All");
		map.put(ListAdapterCategory.DISTRIBUTOR, "");
		map.put(ListAdapterCategory.RATING, "");
		category_list.add(map);

		//Get the category list view from the xml
		list = (ListView)v.findViewById(R.id.list);

		// Getting adapter by passing xml data ArrayList
		adapter = new ListAdapterCategory(v.getContext(), category_list);   
		
		//Set the adapter to the category list
		list.setAdapter(adapter);

		// Click event for single list row
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				//Get the page adapter from main activity
				MyFragmentPagerAdapter pageAdapter = MainActivity.pagerAdapter;

				//Set the type for the #2 tab. This will edit the content and the title
				pageAdapter.page1 = Page.getType(adapter.getName(position), 1);

				//Set the type for the #3 tab. This will edit the content and the title
				pageAdapter.page2 = Page.getType(adapter.getName(position), 2);

				//Update the content on the #2 tab
				pageAdapter.all.update();

				//Notify the page adapter that there have been some changes
				pageAdapter.notifyDataSetChanged();

				//Move to selected category
				MainActivity.pager.setCurrentItem(1);
			}
		});	

		return v;
	}
}