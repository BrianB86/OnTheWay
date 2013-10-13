package com.example.ontheway;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SmsListener extends BroadcastReceiver{

    private SharedPreferences preferences;
    private Location location;
    
    

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
    	

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            SmsMessage[] msgs = null;
            String msg_from;
            if (bundle != null){
                //---retrieve the SMS message received---
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        Toast.makeText(context, "Message from: " + msg_from, Toast.LENGTH_SHORT).show();
                        String msgBody = msgs[i].getMessageBody();
                    }
                    checkSpeed(context);
                }catch(Exception e){
//                            Log.d("Exception caught",e.getMessage());
                }
            }
        }
    }
    
    public void checkSpeed(Context context){
    	location = new Location(LocationManager.GPS_PROVIDER);
        float speed = location.getSpeed();

    	Log.i("SmsReceiver", "Speed = " + speed);
    	Toast.makeText(context, "Speed = " + speed, Toast.LENGTH_SHORT).show();
    }
}