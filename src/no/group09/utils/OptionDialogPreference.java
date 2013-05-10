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

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;

/**
 * The OptionDialogPreference will display a dialog, and will persist the
 * <code>true</code> when pressing the positive button and <code>false</code>
 * otherwise. It will persist to the android:key specified in xml-preference.
 */
public class OptionDialogPreference extends DialogPreference {

	SharedPreferences sharedPref = null;
	/**
	 * Constructor for OptionDialogPreference
	 * @param context - Context
	 * @param attrs - AttributeSet
	 */
	public OptionDialogPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		sharedPref = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
		this.setDialogMessage(sharedPref.getString("conn_device_dialog", "No device detected"));
	}

	@Override
	protected void onDialogClosed(boolean positiveResult) {
		super.onDialogClosed(positiveResult);
		if (positiveResult){
			sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
			Editor edit = sharedPref.edit();
			edit.putString("conn_device_dialog", "No device detected");
			edit.commit();
		}
	}
}