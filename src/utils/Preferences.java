package utils;

import no.group09.arduinoair.R;
import no.group09.arduinoair.R.id;
import no.group09.arduinoair.R.layout;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Preferences extends Activity {

	private final String TAG = "Preferences";	//For debugging: log.d(TAG, "message");
	
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.main);
      
   }
}
