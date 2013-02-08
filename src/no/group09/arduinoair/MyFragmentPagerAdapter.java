package no.group09.arduinoair;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class MyFragmentPagerAdapter extends FragmentPagerAdapter{
 
    final int PAGE_COUNT = 5;
 
    /** Constructor of the class */
    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    /** This method will be invoked when a page is requested to create */
    @Override
    public Fragment getItem(int arg0) {
 
        MyFragment myFragment = new MyFragment();
        Bundle data = new Bundle();
        data.putInt("current_page", arg0+1);
        myFragment.setArguments(data);
        return myFragment;
    }
 
    /** Returns the number of pages */
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
    
    @Override
    public CharSequence getPageTitle(int position) {
    	
        String headline = "";
        
        switch(position){
        case 0: headline = "Categories"; break;
        case 1: headline = "Top Hits"; break;
        case 2: headline = "Most Popular"; break;
        case 3: headline = "Top Rating"; break;
        case 4: headline = "New"; break;
        }
        return headline;
    }
}