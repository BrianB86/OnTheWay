package com.example.ontheway;

import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class Ontheway extends Activity {
	public LocationManager locationManager;
	public Location location;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent mServiceIntent = new Intent(this, DrivingProc.class);
        //mServiceIntent.setData(Uri.parse());
        this.startService(mServiceIntent);
        
        setContentView(R.layout.activity_ontheway);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ontheway, menu);
        return true;
    }    
    
}
