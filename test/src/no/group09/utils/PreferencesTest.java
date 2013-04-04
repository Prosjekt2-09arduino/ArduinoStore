package no.group09.utils;

import no.group09.ucsoftwarestore.MainActivity;
import no.group09.ucsoftwarestore.R;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

public class PreferencesTest  extends ActivityInstrumentationTestCase2<MainActivity>{

	private Solo solo; 

	public PreferencesTest() {
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

	public void testHideShowIncompatible() throws Exception {
		//3 & 5
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		solo.clickOnText("Games");
		
		assertEquals(!prefs.getBoolean("hide_incompatible", true), solo.searchText("FunTime"));
		
		solo.clickOnActionBarItem(R.id.toggle_incompitable);
		
		assertEquals(!prefs.getBoolean("hide_incompatible", false), solo.searchText("FunTime"));
		
		solo.clickOnActionBarItem(R.id.settings);
		
		assertEquals(prefs.getBoolean("hide_incompatible", true), solo.isCheckBoxChecked("Hide incompatible"));
		
		solo.clickOnText("Hide incompatible");
		
		assertEquals(!prefs.getBoolean("hide_incompatible", false), solo.isCheckBoxChecked("Hide incompatible"));
		
	}

}
