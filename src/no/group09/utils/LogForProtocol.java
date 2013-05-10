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

import no.group09.stk500_v1.Logger;

public class LogForProtocol implements Logger {
	public static final String TAG = "BT-for-STK";
	
	public LogForProtocol(){
	}
	
	/** prints a msg on the UI screen **/
	public void makeToast(String msg){
	}
	
	public void printToConsole(String msg){
	}
	
	public void logcat(String msg, String level) {
		if (level.equals("v")) {
			android.util.Log.v(TAG, msg);
		}
		else if (level.equals("d")){
			android.util.Log.d(TAG, msg);
		}
		else if (level.equals("i")) {
			android.util.Log.i(TAG, msg);
		}
		else if (level.equals("w")) {
			android.util.Log.w(TAG, msg);
		}
		else if (level.equals("e")) {
			android.util.Log.e(TAG, msg);
		}
	}

}
