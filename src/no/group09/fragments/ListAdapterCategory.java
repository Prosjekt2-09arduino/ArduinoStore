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
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapterCategory extends BaseAdapter {
    
    private Context context;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    
	protected static final String KEY_ID = "id";
	protected static final String APP_NAME = "title";
	protected static final String DISTRIBUTOR = "distributor";
	protected static final String RATING = "rating";
    
    public ListAdapterCategory(Context a, ArrayList<HashMap<String, String>> d) {
        context = a;
        data = d;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row_categories, null);

        TextView appName = (TextView)vi.findViewById(R.id.app_name);
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image);
        
        HashMap<String, String> listItem = new HashMap<String, String>();
        listItem = data.get(position);
        
        // Setting all values in listview
        appName.setText(listItem.get(APP_NAME));
        
        if(listItem.get(APP_NAME).equals("Games")){
        	thumb_image.setImageResource(R.drawable.games);
        }
        
        else if(listItem.get(APP_NAME).equals("Medical")){
        	thumb_image.setImageResource(R.drawable.medical);
        }
        
        else if(listItem.get(APP_NAME).equals("Tools")){
        	thumb_image.setImageResource(R.drawable.tools);
        }
        
        else if(listItem.get(APP_NAME).equals("Media")){
        	thumb_image.setImageResource(R.drawable.media);
        }
        
        return vi;
    }
    
	public String getName(int id){
		return data.get(id).get(APP_NAME);
	}
}