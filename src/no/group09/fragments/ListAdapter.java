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
    
    private Context context;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    
	public static final String KEY_ID = "id";
	public static final String APP_NAME = "title";
	public static final String DISTRIBUTOR = "distributor";
	public static final String RATING = "rating";
	public static final String IMAGE = "image";
    
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
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);

        TextView appName = (TextView)vi.findViewById(R.id.app_name);
        TextView distributor = (TextView)vi.findViewById(R.id.distributor);
        RatingBar getRatingBar = (RatingBar) vi.findViewById(R.id.ratingBarIndicator);;
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image);
        
        HashMap<String, String> listItem = new HashMap<String, String>();
        listItem = data.get(position);
        
        // Setting all values in listview
        appName.setText(listItem.get(APP_NAME));
        distributor.setText(listItem.get(DISTRIBUTOR));

        getRatingBar.setRating(Integer.parseInt(listItem.get(RATING)));
        
        return vi;
    }
    
    public String getID(int position){
    	return data.get(position).get(KEY_ID);
    }
}