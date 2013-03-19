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

import no.group09.ucsoftwarestore.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BluetoothDeviceAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;

	public BluetoothDeviceAdapter(Context a, ArrayList<HashMap<String, String>> d) {
		super();
		
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

		View vi = convertView;

		if(convertView == null){
			vi = LayoutInflater.from(context).inflate(R.layout.bluetooth_device_list_row, null);
		}
			
		TextView deviceName = (TextView)vi.findViewById(R.id.bluetooth_device_name);
		
		TextView deviceMac = (TextView)vi.findViewById(R.id.bluetooth_device_mac);

		HashMap<String, String> listItem = new HashMap<String, String>();
		listItem = data.get(position);

		// Setting all values in listview
		deviceName.setText(listItem.get("name"));
		deviceMac.setText(listItem.get("mac"));
		
		if(!listItem.get("pager").equals("708")){
			ImageView image = (ImageView)vi.findViewById(R.id.list_image);
			image.setImageResource(R.drawable.bluetooth);
		}

		return vi;
	}
	
	/**
	 * Returns the BT name of the devices
	 * @param id - the position in the BluetoothDeviceAdapter list you want the name for
	 */
	public String getName(int id){
		return data.get(id).get("name");
	}
	
	/**
	 * Returns the BT mac address of the devices
	 * @param id - the position in the BluetoothDeviceAdapter list you want the mac address for
	 */
	public String getMacAddress(int id){
		return data.get(id).get("mac");
	}
	
	public String getPager(int id){
		return data.get(id).get("pager");
	}
}
