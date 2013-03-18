package no.group09.ucsoftwarestore.test;

import no.group09.ucsoftwarestore.MainActivity;
import com.jayway.android.robotium.solo.Solo;
import android.test.ActivityInstrumentationTestCase2;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity>{

	// this is Robotium class used to support test cases that span over multiple activities.
	private Solo solo; 

	public MainActivityTest() {
		super(MainActivity.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		//takes in the instrumentation and the start activity.
		solo = new Solo(getInstrumentation(), getActivity());
	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
	
	public void testSettings() throws Exception{
		solo.clickOnMenuItem("Settings");
	}
}