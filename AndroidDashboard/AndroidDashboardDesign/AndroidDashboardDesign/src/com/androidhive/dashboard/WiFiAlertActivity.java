package com.androidhive.dashboard;

import java.util.HashMap;

import androidhive.dashboard.R;
import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.DialogInterface;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiConfiguration.GroupCipher;
import android.net.wifi.WifiConfiguration.KeyMgmt;
import android.net.wifi.WifiConfiguration.Protocol;
import android.os.Bundle;

import com.androidhive.dashboard.FriendsActivity.Entry;

public class WiFiAlertActivity extends Activity {
	
	private static final String TAG = WiFiAlertActivity.class.getSimpleName();
	Context ctx;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_entry_layout);
        
        ctx = getApplicationContext();
        this.getWindow().getDecorView().setBackgroundColor(0xFFFFFFFF);
		Log.v(TAG, "onCreate");
		
		LayoutInflater factory = LayoutInflater.from(this);
		
		
		try {  
         Bundle bn = new Bundle();
         bn = getIntent().getExtras();
         HashMap<String, Object> getobj = new HashMap<String, Object>();
         getobj = (HashMap<String, Object>) bn.getSerializable("Details");
        // getsetclass  d = (getsetclass) getobj.get("hashmapkey");
     } catch (Exception e) {
         Log.e("Err", e.getMessage());
     } 
		
		
		
		/* LayoutInflater factory = LayoutInflater.from(this);
		 final View textEntryView = factory.inflate(R.layout.wifi_entry_layout, null);
		 text_entry is an Layout XML file containing two text field to display in alert dialog
		 final EditText input1 = (EditText) textEntryView.findViewById(R.id.EditText1);
		 final EditText input2 = (EditText) textEntryView.findViewById(R.id.EditText2);             
		 input1.setText("DefaultValue", TextView.BufferType.EDITABLE);
		 input2.setText("DefaultValue", TextView.BufferType.EDITABLE); */
		 
		/*	ScrollView sv = new ScrollView(this);
		 	LinearLayout layout= new LinearLayout(this);
		    layout.setOrientation(1); //1 is for vertical orientation
		    final TextView ssid = new TextView(this) ;
		    ssid.setText("SSID:");
		    final EditText AP = new EditText(this);
		    final TextView security = new TextView(this);
		    security.setText("security:");
		    //final EditText auth = new EditText(this);
		    String[] s = { "None", "WEP", "WPA/WPA2" };
		    Spinner auth = new Spinner(this);
		    ArrayAdapter<String> adp = new ArrayAdapter<String>(this,
		    		android.R.layout.simple_spinner_item, s);
		    auth.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		    auth.setAdapter(adp);
		    
		    final TextView password = new TextView(this);
		    password.setText("Password");
		    
		    final EditText passwd = new EditText(this);
		    
		    layout.addView(ssid);
		    layout.addView(AP);
		    layout.addView(security);
		    layout.addView(auth);
		    layout.addView(password);
		    layout.addView(passwd);
		    sv.addView(layout); 
		 final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		 builder.setTitle("WiFi Input");
		// builder.setView(textEntryView);
		 //builder.setView(layout);
		 builder.setView(sv);
		 
		 
		 builder.setPositiveButton("Done",
				 new DialogInterface.OnClickListener() {
				 public void onClick(DialogInterface dialog,
				 int whichButton) {
				 Log.i("AlertDialog","TextEntry 1 Entered "+AP.getText().toString());
				// Log.i("AlertDialog","TextEntry 2 Entered "+auth.getText().toString());
				 Log.i("AlertDialog","TextEntry 2 Entered "+passwd.getText().toString());
				 // User clicked OK so do some stuff 
				 }
				 });
		 
		 /*DialogInterface.OnClickListener response = new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int id) {
				}
			}; */
		 
		 
		/* builder.setCancelable(false);
		// builder.setItems(details, response);
		 
		 AlertDialog alert = builder.create();
			
		 alert.show(); */
		 
	}
	
	
}

