package no.group09.utils;

import com.jayway.android.robotium.solo.Solo;
import no.group09.connection.BluetoothConnection;
import no.group09.ucsoftwarestore.R;
import no.group09.ucsoftwarestore.MainActivity;
import android.test.ActivityInstrumentationTestCase2;

public class DevicesTest extends ActivityInstrumentationTestCase2<MainActivity>{

	private Solo solo; 

	public DevicesTest() {
		super(MainActivity.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

	public void testHandshakeAndReliableConnection() throws Exception {
		solo.clickOnActionBarItem(R.id.device_list);
		boolean finish = false;
		
		//Search for bt device ArduinoBT
		while(!finish){
			if(solo.searchText("ArduinoBT")){
				//Try to connect to ArduinoBT
				solo.clickOnText("ArduinoBT");
				finish = true;
			}
			//Wait for the BT device to show up (give it some time)
			Thread.sleep(100);
		}
		
		//Give it 5 seconds to try to handshake
		Thread.sleep(5000);
		assertEquals(BluetoothConnection.ConnectionState.STATE_CONNECTED, 
				BtArduinoService.getBtService().getBluetoothConnection().getConnectionState());
		
		solo.clickOnText("Ok");
		
		assertEquals(BluetoothConnection.ConnectionState.STATE_CONNECTED, 
				BtArduinoService.getBtService().getBluetoothConnection().getConnectionState());
		
		solo.goBack();
		assertEquals("All - ArduinoBT", solo.getCurrentActivity().getTitle());
		
		solo.clickOnText("Medical");
		
		assertEquals(BluetoothConnection.ConnectionState.STATE_CONNECTED, 
				BtArduinoService.getBtService().getBluetoothConnection().getConnectionState());
		
		solo.clickOnText("Helper");
		
		assertEquals(BluetoothConnection.ConnectionState.STATE_CONNECTED, 
				BtArduinoService.getBtService().getBluetoothConnection().getConnectionState());
		
		solo.goBack();
		solo.clickOnActionBarItem(R.id.settings);
		
		assertEquals(BluetoothConnection.ConnectionState.STATE_CONNECTED, 
				BtArduinoService.getBtService().getBluetoothConnection().getConnectionState());
		
		solo.clickOnText("Connected device");
		assertEquals(true, solo.searchText("Device name: ArduinoBT"));
		
		solo.clickOnText("OK");
		solo.goBack();
		solo.clickOnActionBarItem(R.id.device_list);
		
		assertEquals(BluetoothConnection.ConnectionState.STATE_CONNECTED, 
				BtArduinoService.getBtService().getBluetoothConnection().getConnectionState());
	}
}
