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
import android.widget.RatingBar;
import android.widget.TextView;
/**
 * basic ListAdapter for apps
 */
public class ListAdapter extends BaseAdapter {

	/** The context */
	private Context context;

	/** List of HashMap with applications */
	private ArrayList<HashMap<String, String>> data;

	/** Layout inflater */
	private static LayoutInflater inflater=null;

	public static final String KEY_ID = "id";
	public static final String APP_NAME = "title";
	public static final String DISTRIBUTOR = "distributor";
	public static final String RATING = "rating";
	public static final String IMAGE = "image";
	public static final String CATEGORY = "category";

	/**
	 * Constructor for the list adapter for a fragment
	 * @param a - context of the activity owne
	 * @param d - arraylist of application data
	 */
	public ListAdapter(Context a, ArrayList<HashMap<String, String>> d) {
		context = a;
		data = d;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
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

		//Convert the view by putting its xml inside the parent xml if its not null
		if(convertView==null)
			vi = inflater.inflate(R.layout.list_row, null);

		//Get all the xml components
		TextView appName = (TextView)vi.findViewById(R.id.app_name);
		TextView distributor = (TextView)vi.findViewById(R.id.distributor);
		RatingBar getRatingBar = (RatingBar) vi.findViewById(R.id.ratingBarIndicator);;
		ImageView thumb_image = (ImageView)vi.findViewById(R.id.list_image);

		//HashMap of current element
		HashMap<String, String> listItem = new HashMap<String, String>();

		//Fetch the data from current clicked element from the list of application
		listItem = data.get(position);

		// Setting all values in listview
		appName.setText(listItem.get(APP_NAME));
		distributor.setText(listItem.get(DISTRIBUTOR));
		getRatingBar.setRating(Integer.parseInt(listItem.get(RATING)));

		//Set the image to the application
		setThumbImage(listItem, thumb_image, appName);

		return vi;
	}

	/**
	 * 
	 * @param position - the item position from the list
	 * @return - the database id to the current application
	 */
	public String getID(int position){
		return data.get(position).get(KEY_ID);
	}
	
	/**
	 * Method for illustration purposes. This method initializes each element
	 * with an image when the app view of the selected app is entered. 
	 */
	public void setThumbImage(HashMap<String, String> listItem, ImageView thumb_image, TextView appName) {
		
		//Set the correct image to its corresponding category
		if(listItem.get(CATEGORY).equals("Games")){
			if (appName.getText().equals("Super Mario Tune")) 
				thumb_image.setImageResource(R.drawable.supermario);
			else
				thumb_image.setImageResource(R.drawable.games);
		}

		//Set the correct image to its corresponding category
		else if(listItem.get(CATEGORY).equals("Medical")){
			thumb_image.setImageResource(R.drawable.medical);
		}

		//Set the correct image to its corresponding category
		else if(listItem.get(CATEGORY).equals("Tools")){
			if (appName.getText().equals("Thermometer Celsius") 
					|| appName.getText().equals("Thermometer Fahrenheit"))
				thumb_image.setImageResource(R.drawable.thermometer);
			else
				thumb_image.setImageResource(R.drawable.tools);
		}

		//Set the correct image to its corresponding category
		else if(listItem.get(CATEGORY).equals("Media")){
			if (appName.getText().equals("Flashing LEDs sequential") 
					|| appName.getText().equals("Flashing LEDs alternative"))
				thumb_image.setImageResource(R.drawable.led);
			else
				thumb_image.setImageResource(R.drawable.media);
		}
	}
}