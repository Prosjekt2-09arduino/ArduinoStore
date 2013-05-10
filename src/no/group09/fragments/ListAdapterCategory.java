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
import no.group09.ucsoftwarestore.R;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Adapter for the list of categories
 */
public class ListAdapterCategory extends BaseAdapter {

	/** The context */
	private Context context;

	/** Arraylist of categories */
	private ArrayList<HashMap<String, String>> data;

	/** inflater of the layout */
	private static LayoutInflater inflater=null;

	protected static final String KEY_ID = "id";
	protected static final String APP_NAME = "title";
	protected static final String DISTRIBUTOR = "distributor";
	protected static final String RATING = "rating";

	/**
	 * 
	 * @param a - the context to current activity
	 * @param d - arraylist of HashMaps with categories
	 */
	public ListAdapterCategory(Context a, ArrayList<HashMap<String, String>> d) {
		context = a;
		data = d;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	/**
	 * Get number of categories
	 */
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		//Get the current view
		View vi=convertView;

		//If the view is not null, put the list inside the fragment view
		if(convertView==null)
			vi = inflater.inflate(R.layout.list_row_categories, null);

		//Category name
		TextView categoryName = (TextView)vi.findViewById(R.id.app_name);

		//Category image
		ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image);

		//Map of categories
		HashMap<String, String> listItem = new HashMap<String, String>();

		//Get item at clicked position in list
		listItem = data.get(position);

		// Setting all values in listview
		categoryName.setText(listItem.get(APP_NAME));

		//Change image to right category img
		if(listItem.get(APP_NAME).equals("Games")){
			thumb_image.setImageResource(R.drawable.games);
		}

		//Change image to right category img
		else if(listItem.get(APP_NAME).equals("Medical")){
			thumb_image.setImageResource(R.drawable.medical);
		}

		//Change image to right category img
		else if(listItem.get(APP_NAME).equals("Tools")){
			thumb_image.setImageResource(R.drawable.tools);
		}

		//Change image to right category img
		else if(listItem.get(APP_NAME).equals("Media")){
			thumb_image.setImageResource(R.drawable.media);
		}

		//Change image to right category img
		else if(listItem.get(APP_NAME).equals("All")){
			thumb_image.setImageResource(R.drawable.ic_launcher);
		}

		return vi;
	}

	/** Get the category name by its position */
	public String getName(int id){
		return data.get(id).get(APP_NAME);
	}
}