package no.group09.arduinoair;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
 
public class MyFragment extends Fragment{
 
	private final String TAG = "MyFragment";
	
    int currentTab;
    protected Context ctxt;
    
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        /** Getting the arguments to the Bundle object */
        Bundle data = getArguments();
 
        /** Getting integer data of the key current_page from the bundle */
        currentTab = data.getInt("current_page", 0);
 
        
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.myfragment_layout, container,false);
        TextView tv = (TextView ) v.findViewById(R.id.tv);
        

        tv.setText("Swipe Horizontally left / right");
        return v;
    }
    
 
}
