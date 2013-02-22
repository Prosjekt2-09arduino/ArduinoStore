package no.group09.utils;

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

import no.group09.arduinoair.R;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;

public class Preferences extends PreferenceActivity implements OnSharedPreferenceChangeListener{
	
	private String TAG = "Preferences.java";
	private PreferenceScreen ps = null;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/*This method is deprecated, but have to be used when developing for
		 * Android 3.0 or lower.
		 */
		addPreferencesFromResource(R.xml.preferences);
		
		ps = getPreferenceScreen();
		ps.getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
		
//		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
//		DialogPreference dp = (DialogPreference) ps.findPreference("conn_device_dialog");
//		
//		dp.setDialogMessage(sharedPref.getString("conn_device_dialog", "No connected device"));
		
	}

	/**
	 * Method called when changes are made in the shared preferences file. Can
	 * be used to tell other instances of changes made.
	 */
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if (key.equals("hide_incompatible")) {
			Log.d(TAG, "'onSharedPreferenceChanged' in Preferences.java has been called");
		}
		else if (key.equals("conn_device_dialog")) {
			Log.d(TAG, "hajkldsfhajklsdfhajkhdfladkjsfh");
		}

	}
}