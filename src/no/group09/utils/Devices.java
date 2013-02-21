package no.group09.utils;

import java.util.ArrayList;
import java.util.HashMap;
import no.group09.arduinoair.R;
import no.group09.fragments.BluetoothDeviceAdapter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * This class searches for the BT devices in range and put them in a list
 */
public class Devices extends Activity{

	private static final String TAG = "DEVICES";
	private static final int REQUEST_ENABLE_BT = 1;
	private ListView tv;
	private BluetoothDeviceAdapter adapter;
	private BluetoothAdapter btAdapter; 
	private ArrayList<HashMap<String, String>> category_list;
	private IntentFilter filter;

	private ProgressBar progressBar;
	private ArrayList<HashMap<String, String>> device_list;
	private boolean alreadyChecked = false;
	private ProgressBar progBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Set the xml layout
		setContentView(R.layout.devices);	

		//Register the BroadcastReceiver
		filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		filter.addAction(BluetoothDevice.ACTION_UUID);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

		//Registrer the BT receiver with the requested filters
		//Don't forget to unregister during onDestroy
		registerReceiver(ActionFoundReceiver, filter);

		// Getting the Bluetooth adapter
		btAdapter = BluetoothAdapter.getDefaultAdapter();

		category_list = new ArrayList<HashMap<String, String>>();

		//		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		progressBar = (ProgressBar) findViewById(R.id.progbar);

		progressBar.setVisibility(View.VISIBLE);

		// Getting adapter by passing xml data ArrayList
		adapter = new BluetoothDeviceAdapter(getBaseContext(), category_list);        

		//List of devices
		device_list = new ArrayList<HashMap<String, String>>();

		//Get the xml to how the list is structured
		tv = (ListView) findViewById(R.id.bluetooth_devices_list);

		//Get the adapter for the device list
		adapter = new BluetoothDeviceAdapter(getBaseContext(), device_list);        

		//Set the adapter
		tv.setAdapter(adapter);

		//Click event for single list row
		tv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(view.getContext(), "You clicked on: " + adapter.getName(position), Toast.LENGTH_SHORT).show();
			}
		});	

		//Check the BT state
		checkBTState();
	}

	/* This routine is called when an activity completes.*/
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_ENABLE_BT) {
			checkBTState();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (btAdapter != null) {
			btAdapter.cancelDiscovery();
		}
		unregisterReceiver(ActionFoundReceiver);
	}
	/**
	 * Checks the current BT state
	 */
	private void checkBTState() {

		//Check if the Android support bluetooth
		if(btAdapter==null) return;
		else {

			//Check if the BT is turned on
			if (btAdapter.isEnabled()) {

				// Starting the device discovery
				btAdapter.startDiscovery();
			}

			//If the BT is not turned on, request the user to turn it on
			else if (!btAdapter.isEnabled() && !alreadyChecked){
				Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

				//The user have already turned it on or off: don't request again
				alreadyChecked = true;
			}
		}
	}

	private final BroadcastReceiver ActionFoundReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			//			String action = intent.getAction();
			//			if(BluetoothDevice.ACTION_FOUND.equals(action)) {
			//				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			//				
			//				//Adding found device
			//				HashMap<String, String> map = new HashMap<String, String>();
			//				map = new HashMap<String, String>();
			//				map.put("name", device.getName());
			//				map.put("mac", device.getAddress());
			//				device_list.add(map);
			//
			//				adapter.notifyDataSetChanged();

			final Intent fIntent = intent;

			progressBar.setVisibility(View.VISIBLE);

			new AsyncTask<Void,Void,Void>(){

				//The variables you need to set go here

				@Override
				protected void onPreExecute(){

					progressBar.setVisibility(View.VISIBLE);
				}

				@Override
				protected Void doInBackground(final Void... params){

					// Do your loading here. Don't touch any views from here, and then return null

					progressBar.setVisibility(View.VISIBLE);

					String action = fIntent.getAction();
					if(BluetoothDevice.ACTION_FOUND.equals(action)) {
						BluetoothDevice device = fIntent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

						//Adding found device
						HashMap<String, String> map = new HashMap<String, String>();
						map = new HashMap<String, String>();
						map.put("name", device.getName());
						map.put("mac", device.getAddress());
						device_list.add(map);
					}

					return null;
				}

				@Override
				protected void onPostExecute(final Void result){

					// Update your views here

					adapter.notifyDataSetChanged();
					progressBar.setVisibility(View.GONE);
				}
			}.execute();

			//			} else {
			//				if(BluetoothDevice.ACTION_UUID.equals(action)) {
			//					BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			//					Parcelable[] uuidExtra = intent.getParcelableArrayExtra(BluetoothDevice.EXTRA_UUID);
			//					for (int i=0; i<uuidExtra.length; i++) {
			//						Log.d(TAG, "\n  Device: " + device.getName() + ", " + device + ", Service: " + uuidExtra[i].toString());
			//					}
			//				} else {
			//					if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
			//						Log.d(TAG, "\nDiscovery Started...");
			//					} else {
			//						if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
			//							Log.d(TAG, "\nDiscovery Finished");
			//							Iterator<BluetoothDevice> itr = btDeviceList.iterator();
			//							while (itr.hasNext()) {
			//								// Get Services for paired devices
			//								BluetoothDevice device = itr.next();
			//								Log.d(TAG, "\nGetting Services for " + device.getName() + ", " + device);
			//							
			//								adapter.notifyDataSetChanged();
			////								if(!device.fetchUuidsWithSdp()) {
			////									Log.d(TAG, "\nSDP Failed for " + device.getName());
			////								}
			//
			//							}
			//						}
			//					}
			//				}

		};
	};
}