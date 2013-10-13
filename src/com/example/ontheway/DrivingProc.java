package com.example.ontheway;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Intent;;

public class DrivingProc extends IntentService{
	
	public DrivingProc(String name) {
		super(name);
		//grab users contacts
		//grab mapped responses
		
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent workIntent) {
		//majority of code should go here. Running process that checks for driving.
		//dies only when checked off by main activity
		
		
	}

}
