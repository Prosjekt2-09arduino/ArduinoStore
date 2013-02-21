package no.group09.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import no.group09.arduinoair.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;

		if(convertView == null){
			vi = LayoutInflater.from(context).inflate(R.layout.bluetooth_device_list_row, null);
		}
			
		TextView appName = (TextView)vi.findViewById(R.id.bluetooth_device);

		HashMap<String, String> listItem = new HashMap<String, String>();
		listItem = data.get(position);

		// Setting all values in listview
		appName.setText(listItem.get("name") + ", " + listItem.get("mac"));

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
}
