package com.example.ontheway;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class Ontheway extends Activity {
	public static LocationManager locationManager;
	public static Location location;
	public static SmsListener smsListener;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent mServiceIntent = new Intent(this, DrivingProc.class);
        //mServiceIntent.setData(Uri.parse());
        this.startService(mServiceIntent);
        
        Context context = getApplicationContext();
		smsListener = new SmsListener();
		IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
		registerReceiver(smsListener,intentFilter);
        
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		LocationListener locationListener = new LocationListener() {
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				Log.i("LocationManager", "Provider = " + provider);
				
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLocationChanged(Location location) {
				Ontheway.this.location = location;
				Log.i("OntheWay", "Location changed. Longitude = " + location.getLongitude() + " Latitude=" + location.getLatitude());
			}
		};
		
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
		
        setContentView(R.layout.activity_ontheway);
    }

    @Override
    protected void onPause(){
    	super.onPause();
    	//unregisterReceiver(smsListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ontheway, menu);
        return true;
    }

	@Override
	protected void onDestroy() {
		try{
			unregisterReceiver(smsListener);
		} catch (Exception e){
			//Do nothing.
		}
		
		super.onDestroy();
	}  
    

    
}
