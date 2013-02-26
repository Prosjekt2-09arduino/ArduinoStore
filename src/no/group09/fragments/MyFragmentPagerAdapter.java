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

		CharSequence tab = "";	//TODO: maybe make this protected and static

		switch(position){
		case 0: tab = "CATEGORIES"; break;
		case 1: tab = "ALL"; break;
		case 2: tab = "TOP HITS"; break;
		default: tab = "";
		}

		return tab;
	}
}