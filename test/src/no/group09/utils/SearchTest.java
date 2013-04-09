package no.group09.utils;

import com.jayway.android.robotium.solo.Solo;

import no.group09.ucsoftwarestore.MainActivity;
import no.group09.ucsoftwarestore.R;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.Toast;

public class SearchTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private Solo solo; 

	public SearchTest(){
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

	public void testSuccesfulSearch(){

		solo.clickOnActionBarItem(R.id.menu_search);

		solo.enterText(0, "Game");
		solo.sendKey(Solo.ENTER);
		
		assertTrue(solo.searchText("Game", 2));
	}
	public void testFailedSearch(){
		
		solo.clickOnActionBarItem(R.id.menu_search);

		String query = "aeorohiergiouegriuegriuregqioherghoegr";
		solo.enterText(0, query);
		solo.sendKey(Solo.ENTER);
		
		try {
			Thread.sleep(3505);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Log.e("Wait", "Error when waiting in testFailedSearch");
		}
		assertFalse(solo.searchText(query));
	}
}
