package no.group09.arduinoair;

/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
 
public class MyFragment extends Fragment{
 
	private final String TAG = "MyFragment";
	
    private int currentTab;
    protected Context ctxt;
    private Activity activity;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        /** Getting the arguments to the Bundle object. */
        Bundle data = getArguments();
 
        /** Getting integer data of the key current_page from the bundle */
        currentTab = data.getInt("current_page", 0);
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.myfragment_layout, container,false);
        TextView tv = (TextView ) v.findViewById(R.id.tv);
        
//        Intent myIntent = new Intent(v.getContext(), Categories.class);
//        v.getContext().startActivity(myIntent);

        tv.setText("Swipe Horizontally left / right");
        return v;
    }
    
 
}
