package no.group09.utils;

import com.jayway.android.robotium.solo.Solo;

import no.group09.ucsoftwarestore.R;
import no.group09.utils.Devices;
import android.test.ActivityInstrumentationTestCase2;

public class DevicesTest extends ActivityInstrumentationTestCase2<Devices>{

	private Solo solo; 
	
	public DevicesTest() {
		super(Devices.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
	
	public void testDeviceList() throws Exception {
		
		
	}
}
