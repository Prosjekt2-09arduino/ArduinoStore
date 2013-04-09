package no.group09.ucsoftwarestore;

import java.util.ArrayList;
import no.group09.database.Save;
import no.group09.database.entity.App;
import no.group09.ucsoftwarestore.MainActivity;

import com.jayway.android.robotium.solo.Solo;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

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

	public void testPopulateDB() throws Exception{
		ArrayList<App> allApps = new ArrayList<App>();

		Save save = new Save(getActivity());
		//		save.populateDatabase();
		allApps = save.getAllApps();

		assertNotNull("AllApps is empty!",allApps.get(0));
		Log.d("AllApps size", "Size in populateDB is " + String.valueOf(allApps.size()));


	}
	/**
	 * Goes through all filtration options to ensure proper filtration.
	 * @throws Exception
	 */
	public void testTotalShopFiltration() throws Exception{

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

		solo.clickOnActionBarItem(R.id.settings);
		if(prefs.getBoolean("hide_incompatible", false)){
			solo.clickOnText("Hide incompatible");
		}
		solo.goBack();

		ArrayList<App> allApps = new ArrayList<App>();

		ArrayList<App> allGames = new ArrayList<App>();
		ArrayList<App> allMedical = new ArrayList<App>();
		ArrayList<App> allTools = new ArrayList<App>();
		ArrayList<App> allMedia = new ArrayList<App>();
		ArrayList<App> allCompatible = new ArrayList<App>();
		ArrayList<App> notCompatible = new ArrayList<App>();

		Save save = new Save(getActivity());
		allApps = save.getAllApps();

		assertNotNull("AllApps is empty!",allApps.get(0));
		Log.d("AllApps size", "Size in filtration is " + String.valueOf(allApps.size()));

		for (App app : allApps) {

			String category = app.getCategory();

			if(category.equals("Games")){
				allGames.add(app);
			}
			else if(category.equalsIgnoreCase("medical")){
				allMedical.add(app);
			}
			else if(category.equalsIgnoreCase("tools")){
				allTools.add(app);
			}
			else if(category.equalsIgnoreCase("media")){
				allMedia.add(app);
			}

			if(save.getRequirementsByID(app.getRequirementID()).isCompatible()){
				allCompatible.add(app);
			}


		}
		assertNotNull("AllGames is empty!",allGames.get(0));
		assertNotNull("AllMedical is emtpy!",allMedical.get(0));
		assertNotNull("AllTools is emtpy!",allTools.get(0));
		assertNotNull("AllMedia is emtpy!",allMedia.get(0));

		solo.clickOnText("Games");
		for (App app : allGames) {
			assertTrue(solo.searchText(app.getName()));
		}
		//		
		//		solo.scrollToSide(Solo.LEFT);
		//		solo.clickOnText("Medical");
		//		for (App app : allMedical) {
		//			assertTrue(solo.searchText(app.getName()));
		//		}
		//		
		//		solo.scrollToSide(Solo.LEFT);
		//		solo.clickOnText("Tools");
		//		for (App app : allTools) {
		//			assertTrue(solo.searchText(app.getName()));
		//		}
		//		
		//		solo.scrollToSide(Solo.LEFT);
		//		solo.clickOnText("Media");
		//		for (App app : allMedia) {
		//			assertTrue(solo.searchText(app.getName()));
		//		}
		//		
		//		solo.scrollToSide(Solo.LEFT);
		//		solo.clickOnText("All");
		//		for (App app : allApps) {
		//			assertTrue(solo.searchText(app.getName()));
		//		}
		//		
		//		fills not compatible with not compatible apps
		for (App app : allApps) {
			if(!allCompatible.contains(app))
				notCompatible.add(app);
		}
		assertNotNull(notCompatible.get(0));
		////		checks if not compatible are shown
		//		for (App app : notCompatible) {
		//			assertTrue(solo.searchText(app.getName()));
		//		}


		solo.clickOnActionBarItem(R.id.settings);
		solo.clickOnText("Hide incompatible");


		solo.goBack();
		solo.scrollToSide(Solo.LEFT);
		solo.clickOnText("All");

		//		checks that compatible still are shown
		for (App app : allCompatible) {
			assertTrue(solo.searchText(app.getName()));
		}

		//		checks that not compatible are not shown
		for (App app : notCompatible) {
			Log.d("notCompatible", app.getName());

			if(!app.getName().equals("Medical"))
				assertFalse(solo.searchText(app.getName()));

		}

		/**
		 * check what apps are shown, and if they are compatible or not
		 * open action overflow
		 * click hide/show compatible
		 * check if the correct apps are hidden
		 */

	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}

