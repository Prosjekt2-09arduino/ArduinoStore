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
		boolean finish = false;
		
		//Check if refresh button is invisible (gone)
		assertEquals(8, solo.getView(R.id.refresh).getVisibility());
		
		while(!finish){
			
			//0=VISIBLE to 8=GONE with 4=INVISIBLE.
			if(solo.getView(R.id.refresh).getVisibility() == 0){
				
				//Check if refresh button is visible
				assertEquals(0, solo.getView(R.id.refresh).getVisibility());
				solo.clickOnText("Refresh");
				finish = true;
			}
		}
	}
}
