package no.group09.ucsoftwarestore;

import no.group09.ucsoftwarestore.MainActivity;

import com.jayway.android.robotium.solo.Solo;
import android.test.ActivityInstrumentationTestCase2;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity>{

	// this is Robotium class used to support test cases that span over multiple activities.
	private Solo solo; 

	public MainActivityTest() {
		super(MainActivity.class);
	}

	@Override
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
<<<<<<< HEAD:test/src/no/group09/ucsoftwarestore/MainActivityTest.java
		solo.clickOnActionBarItem(R.id.settings);
<<<<<<< HEAD
		
	}
	public void testHideShowCompatible() throws Exception{
		/**
		 * check what apps are shown, and if they are compatible or not
		 * open action overflow
		 * click hide/show compatible
		 * check if the correct apps are hidden
		 */
	}
	//	Test all filtrations, games, medical, etc. try different variations in order to try to bug it out.
//		Have crashed the app from the category view, not sure what happened.
=======

=======
		solo.clickOnMenuItem("Settings");
		solo.
>>>>>>> Searching now works and returns a toast.:test/src/no/group09/ucsoftwarestore/test/MainActivityTest.java
	}
	
>>>>>>> master
}