package com.example.ontheway;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

//Does this class actually do anything?
public class DrivingProc extends IntentService{
	String TAG = "DrivingProc";
	public DrivingProc(){
		this("test");
	}
	
	public DrivingProc(String name) {
		super(name);
		//grab users contacts
		//grab mapped responses
		
		Log.d(TAG, "constructor");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent workIntent) {
		//majority of code should go here. Running process that checks for driving.
		//dies only when checked off by main activity.
		Log.d(TAG, "onHandleIntent");
		
		
		
	}

}
