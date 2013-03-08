package no.group09.utils;

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

    public OptionDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
//        this.setSummary(sharedPref.getString("conn_device_dialog", "None"));
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        this.setDialogMessage(sharedPref.getString("conn_device_dialog", "No device detected"));
//        this.setDialogMessage(context.getApplicationContext().getSharedPreferences("sharedPref", 0).getString("conn_device_dialog", "No connected device"));
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
//        persistBoolean(positiveResult);
    }
}