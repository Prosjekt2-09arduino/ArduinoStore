package no.group09.arduinoair;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class MyFragmentPagerAdapter extends FragmentPagerAdapter{
 
	final int PAGE_COUNT = 3;

	/** Constructor of the class */
	public MyFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	/** This method will be invoked when a page is requested to create */
	@Override
	public Fragment getItem(int page) {

		//Temporary
		Fragment fragment = null;

		//Categories tab
		if(page == 0){
			fragment = new Categories();
		}

		//TopHits tab
		else if(page == 1){
			fragment = new All();
		}
		
		//Devices tab
		else if(page == 2){
			fragment = new TopHits();
		}

		Bundle data = new Bundle();
		data.putInt("current_page", page+1);
		fragment.setArguments(data);

		return fragment;
	}

	/** Returns the number of pages */
	@Override
	public int getCount() {
		return PAGE_COUNT;
	}

	@Override
	public CharSequence getPageTitle(int position) {

		CharSequence tab = "";

		switch(position){
		case 0: tab = "CATEGORIES"; break;
		case 1: tab = "ALL"; break;
		case 2: tab = "TOP HITS"; break;
		default: tab = "";
		}

		return tab;
	}
}