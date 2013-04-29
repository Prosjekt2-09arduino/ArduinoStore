package no.group09.utils;

import no.group09.stk500_v1.Logger;

public class LogForProtocol implements Logger {
	public static final String TAG = "BT-for-STK";
	
	public LogForProtocol(){
	}
	
	/** prints a msg on the UI screen **/
	public void makeToast(String msg){
//		final String out = msg;
//		main.runOnUiThread(new Runnable() {
//			
//			@Override
//			public void run() {
//				Toast.makeText(ctxt, out, Toast.LENGTH_SHORT).show();
//				
//			}
//		});
	}
	
	public void printToConsole(String msg){
//		final String out = msg;
//		main.runOnUiThread(new Runnable() {
//			
//			@Override
//			public void run() {
//				main.printToConsole(out);
//			}
//		});
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
