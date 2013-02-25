package com.androidhive.dashboard;

import java.util.Iterator;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.DetailedState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiConfiguration.PairwiseCipher;
import android.net.wifi.WifiConfiguration.Protocol;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import android.graphics.Bitmap;
import androidhive.dashboard.R;

public class NewsFeedActivity extends Activity {
  private static final int WHITE = 0xFFFFFFFF;
  private static final int BLACK = 0xFF000000;  
 	
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.news_feed_layout);
    
  /*  final EditText et;
    et = (EditText) findViewById(R.id.edittext);
    String text=et.getText().toString(); */ 
    
    //Initiate UI
  /*  SSID = (EditText)findViewById(R.id.args_pzpName);
	String pzpNameText = configParams.pzpName;
	if(pzpNameText != null)
	pzpName.setText(pzpNameText); */
    
    ImageView image = (ImageView)findViewById(R.id.imageView);
    image.setScaleType(ImageView.ScaleType.CENTER_CROP);
     
    Display display = getWindowManager().getDefaultDisplay();
    int display_width = display.getWidth();
    int display_height = display.getHeight();

    image.setMaxHeight((int)(display_height*0.5));
    image.setMaxWidth((int)(display_width*0.5)); 
    
    int width = (int)(display_width*0.3);
    int height = (int)(display_height*0.3);
    
    BitMatrix matrix = null;
    com.google.zxing.Writer writer = new QRCodeWriter();

    //Grab data that contains Access point and Password
    //getWiFiInfo();
    String connectedAP = null;
    ConnectivityManager connMgr = (ConnectivityManager)
    this.getSystemService(Context.CONNECTIVITY_SERVICE);
    android.net.NetworkInfo wifi =
      connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    if( wifi.isAvailable() && wifi.getDetailedState() == DetailedState.CONNECTED){
      //scan for other APs
      WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE); 
      List<WifiConfiguration> item = manager.getConfiguredNetworks();
      int num = item.size();
      Log.d("WifiPreference", "NO OF CONFIG " + num );
      Iterator<WifiConfiguration> iter =  item.iterator();
      for (int i=0; i<num; i++)
       {	 
        WifiConfiguration config = item.get(i);
        Log.d("WifiPreference", "SSID" + config.SSID);
        Log.d("WiFI status: ", "Status" + config.status);
        Log.d("WifiPreference", "NONE" + config.allowedPairwiseCiphers.get(PairwiseCipher.NONE));
        Log.d("WifiPreference", "WPA" + config.allowedProtocols.get(Protocol.WPA));
        Log.d("WifiPreference", "WEP Key Strings");
        String[] wepKeys = config.wepKeys;
        Log.d("WifiPreference", "WEP KEY 0" + wepKeys[0]);
        Log.d("WifiPreference", "WEP KEY 1" + wepKeys[1]);
        Log.d("WifiPreference", "WEP KEY 2" + wepKeys[2]);
        Log.d("WifiPreference", "WEP KEY 3" + wepKeys[3]);

        if(config.status == 0)
          connectedAP = config.SSID;
          //TODO: save the config list to candidate AP editing list
      }
      String data = connectedAP;
      
      try {
        matrix = writer.encode(data, BarcodeFormat.QR_CODE, width, height);
      } catch (com.google.zxing.WriterException e) {
        System.out.println(e.getMessage());
      }
      int[] pixels = new int[width * height];
      for (int y = 0; y < height; y++) {
        int offset = y * width;
        for (int x = 0; x < width; x++) {
          pixels[offset + x] = matrix.get(x, y) ? BLACK : WHITE;
        } 
      }
      Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
      bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
      image.setImageBitmap(bitmap);
    }
    else
    {
    }
  } 
}   
