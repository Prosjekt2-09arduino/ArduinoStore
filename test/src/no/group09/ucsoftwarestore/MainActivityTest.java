package no.group09.ucsoftwarestore;

import java.util.ArrayList;

import junit.framework.Assert;

import no.group09.database.Save;
import no.group09.database.entity.App;
import no.group09.ucsoftwarestore.MainActivity;

import com.jayway.android.robotium.solo.Solo;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity>{

	// this is Robotium class used to support test cases that span over multiple activities.
	private Solo solo; 
	private ArrayList<App> allApps = new ArrayList<App>();

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
		Save save = new Save(getActivity());
		save.populateDatabase();
		allApps = save.getAllApps();

		assertEquals(15, allApps.size());

	}
	/**
	 * Goes through all filtration options to ensure proper filtration.
	 * @throws Exception
	 */
	public void testTotalShopFiltration() throws Exception{

		ArrayList<App> allGames = new ArrayList<App>();
		ArrayList<App> allMedical = new ArrayList<App>();
		ArrayList<App> allTools = new ArrayList<App>();
		ArrayList<App> allMedia = new ArrayList<App>();
		ArrayList<App> allCompatible = new ArrayList<App>();

		Save save = new Save(getActivity());
		
		for (App app : allApps) {

			String category = app.getCategory();

			if(category.equalsIgnoreCase("games")){
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
			solo.clickOnText("Games");

			ArrayList<View> currentViews = solo.getViews();
			Log.e("mainactivity currentViews Size", String.valueOf(currentViews.size()));

		}


		/**
		 * check what apps are shown, and if they are compatible or not
		 * open action overflow
		 * click hide/show compatible
		 * check if the correct apps are hidden
		 */

		//	Test all filtrations, games, medical, etc. try different variations in order to try to bug it out.
		//		Have crashed the app from the category view, not sure what happened.		

	}
	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
	
	public void testSettings() throws Exception{
		solo.clickOnActionBarItem(R.id.settings);

		solo.clickOnMenuItem("Settings");
	}
	
}