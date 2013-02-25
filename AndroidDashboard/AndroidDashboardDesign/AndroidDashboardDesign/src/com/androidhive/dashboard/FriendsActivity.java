package com.androidhive.dashboard;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.DetailedState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiConfiguration.GroupCipher;
import android.net.wifi.WifiConfiguration.KeyMgmt;
import android.net.wifi.WifiConfiguration.Protocol;
import android.os.Bundle;
import android.util.Log;

import androidhive.dashboard.R;

public class FriendsActivity extends Activity {
	 
	 private static final String TAG = FriendsActivity.class.getSimpleName();
	 WiFiPromptResponseReceiver responseReceiver;
	 Context ctx = null;
	 Entry entry = new Entry(null, 0);
	 
	// HashMap<String, Entry> wifiDetails = new HashMap<String, Entry>();
	 HashMap<String, Object> wifiDetails = new HashMap<String, Object>();
	 
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.friends_layout);
        
      ctx = getApplicationContext();
        
      wifiDetails = getResult();
      
      Intent alert = new Intent(this, WiFiAlertActivity.class);
      alert.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      
      Bundle b = new Bundle();
      b.putSerializable("Details", wifiDetails);
      alert.putExtras(b); 
      
      //alert.putExtra("Details", wifiDetails);
		
		
      //register receiver
      responseReceiver = new WiFiPromptResponseReceiver(this);
      ctx.registerReceiver(responseReceiver, new IntentFilter(TAG+".PROMPT_RESPONSE"));
		
      ctx.startActivity(alert);
    }
    
    class WiFiPromptResponseReceiver extends BroadcastReceiver {
		
      public WiFiPromptResponseReceiver(FriendsActivity friendsActivity) {
			// TODO Auto-generated constructor stub
      }

      public void onReceive(Context ctx, Intent intent) {
        Log.v(TAG, "onReceive");
        Bundle extras = intent.getExtras();
        if(extras!=null)
        {
          String SSID = extras.getString(getResultData());
        }
      }

    }
    
    //private HashMap<String, Entry> getResult() {
    private HashMap<String, Object> getResult() {
    	ctx = getApplicationContext();
    	
      //HashMap<String, Entry> hashMap = new HashMap<String, Entry>();
    	HashMap<String, Object> hashMap = new HashMap<String, Object>();
      //Entry entry = new Entry("none", 0);
      //entry = new Entry("none", 0);
      	
      ConnectivityManager connMgr = (ConnectivityManager)
  		ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
  	  android.net.NetworkInfo wifi =
  	    connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
  	    
  	  if( wifi.isAvailable() && wifi.getDetailedState() == DetailedState.CONNECTED){
  	  	WifiManager manager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE); 
  	    List<WifiConfiguration> item = manager.getConfiguredNetworks();
  	    int num = item.size();
  	    Log.d("WifiPreference", "NO OF CONFIG " + num );
  	    Iterator<WifiConfiguration> iter =  item.iterator();
  	    for (int i=0; i<num; i++)
  	    {	 
  	    	WifiConfiguration config = item.get(i);
  	    	String security = getWifiConfigurationSecurity(config);
  	      if((security == "WPA" || security == "WPA2"))
  	      	security = "WPA/WPA2";
  	            
  	      entry.status = config.status;
  	      entry.sec = security;
  	       	
  	      hashMap.put(config.SSID, entry);
        } 
      }
      return hashMap;
    }

    
  //	class Entry implements Arraylist{
   // class Entry {
    class Entry implements Serializable { 

		String sec;
  		int status;
  	  public Entry(String sec, int status) {
  		  this.sec = sec;
  		  this.status = status;
  		}
      public String getSec() {
  		  return this.sec;
  		}
  		public int getStatus() {
        return this.status;
      }
  	}

    /**
     * @return The security of a given {@link WifiConfiguration}.
     */
    private String getWifiConfigurationSecurity(WifiConfiguration wifiConfig) {
    	String result = "None"; 
        if (wifiConfig.allowedKeyManagement.get(KeyMgmt.NONE)) {
            if (!wifiConfig.allowedGroupCiphers.get(GroupCipher.CCMP)
                    && (wifiConfig.allowedGroupCiphers.get(GroupCipher.WEP40)
                            || wifiConfig.allowedGroupCiphers.get(GroupCipher.WEP104))) {
            	result = "WEP";
            } 
        } else if (wifiConfig.allowedProtocols.get(Protocol.RSN)) {
        	result = "WPA2";
        } else if (wifiConfig.allowedKeyManagement.get(KeyMgmt.WPA_EAP)) {
        	result = "WPA_EAP";
        } else if (wifiConfig.allowedProtocols.get(Protocol.WPA)) {
        	result = "WPA";
        } else {
            Log.w(TAG, "Unknown security type from WifiConfiguration, falling back on open.");
            result = "None";
        }
        return result;
    }
    
}
