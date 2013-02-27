package no.group09.utils;

import no.group09.arduinoair.R;
import android.app.Activity;
import android.os.Bundle;

public class AppView extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		//Set the xml layout
		setContentView(R.layout.devices);		
	}
}
