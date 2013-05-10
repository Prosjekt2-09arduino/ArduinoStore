package no.group09.fragments;

/*
 * Licensed to UbiCollab.org under one or more contributor
 * license agreements.  See the NOTICE file distributed 
 * with this work for additional information regarding
 * copyright ownership. UbiCollab.org licenses this file
 * to you under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import no.group09.ucsoftwarestore.MainActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 /**
  * Adapter for fragment pages
  */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter{
 
	private final int PAGE_COUNT = 3;
	
	/** This is how we make the second tab unique for the chosen category 
	 * (Switch database content in relation to enum type on page 1) */
	public Page page1 = Page.ALL;
	
	/** This is how we make the third tab unique for the chosen category 
	 * (Switch database content in relation to enum type on page 2) */
	public Page page2 = Page.TOPHITS;
	
	/** The second tab. This is needed for editing the content on selected category */
	public All all;
	
	/** The third tab. This is needed for editing the content on selected category */
	public TopHits topHits;
	
	/** Category fragment */
	public Categories categories;
	
	MainActivity main;

	/** Constructor of the class */
	public MyFragmentPagerAdapter(FragmentManager fm, MainActivity main) {
		super(fm);
		this.main = main;
	}
	
	/** This method will be invoked when a page is requested to create */
	@Override
	public Fragment getItem(int page) {

		//Temporary
		Fragment fragment = null;

		//For each page, create a fragment
		switch(page){
		case 0: fragment = new Categories(); 
				categories = (Categories) fragment;
				break;
		
		case 1: fragment = new All(); 
				all = (All) fragment; 
				break;
				
		case 2: fragment = new TopHits(); 
				topHits = (TopHits) fragment;
				break;
		}
		
		//Give the fragment an information about which pagenumber it is
		Bundle data = new Bundle();
		data.putInt("current_page", page + 1);
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

		//Set the title on the fragment by which potition it is at
		switch(position){
		case 0: tab = "CATEGORIES"; break;
		case 1: tab = getTitleForPage1(); break;
		case 2: tab = getTitleForPage2(); break;
		default: tab = "";
		}
		
		//Set the title on main activity
		main.setActivityTitle();
		
		return tab;
	}
	
	/** Returns the title for the second tab*/
	private String getTitleForPage1(){
		switch(page1){
		case ALL : return "ALL APPS";
		case GAMES_ALL : return "ALL GAMES";
		case MEDICAL_ALL : return "ALL MEDICAL";
		case TOOLS_ALL : return "ALL TOOLS";
		case MEDIA_ALL : return "ALL MEDIA";
		default: return "ALL";
		}
	}
	
	/** Returns the title for the third tab */
	private String getTitleForPage2(){
		switch(page2){
		case TOPHITS : return "TOP HITS";
		default : return "MOST POPULAR";
		}
	}
}