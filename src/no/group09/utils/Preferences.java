package no.group09.utils;

import no.group09.arduinoair.R;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

public class Preferences extends PreferenceActivity implements OnSharedPreferenceChangeListener{

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/*This method is deprecated, but have to be used when developing for
		 * Android 3.0 or lower.
		 */
		addPreferencesFromResource(R.xml.preferences);
		
//	
//		setContentView(R.layout.preferences);
//		
//		l = (TextView)findViewById(R.id.pin_tv);
//		
	}

	/* Denne metoden maa implementes for at settings-filen skal lytte
	 * til endringer. Dette skal brukes slik at naar brukeren trykker
	 * paa Hide incompatible i action overflow, skal settingsfilen 
	 * oppdateres. Har ikke forstaatt dette enda, men jeg tror det
	 * er saann det skal brukes. (non-Javadoc)
	 * TODO: Fiks dette!
	 */
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {


	}
}