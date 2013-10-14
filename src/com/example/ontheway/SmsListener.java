package com.example.ontheway;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class SmsListener extends BroadcastReceiver{

    private SharedPreferences preferences;
    private Location location;
    private LocationManager locationManager;
    private float speed = 0;
    private float speedInMph = 0;
    private float threshold = 25; //miles per hour
    
    

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
    	

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            SmsMessage[] msgs = null;
            String msg_from = "";
            if (bundle != null){
                //---retrieve the SMS message received---
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                   
                        String msgBody = msgs[i].getMessageBody();
                        Toast.makeText(context, ("Message from: " + msg_from + " Message= " + msgBody), Toast.LENGTH_SHORT).show();
                        Log.d("onReceive", ("Message from: " + msg_from + " Message= " + msgBody));
                    }
                    boolean shouldSend = checkSpeed(context);
                    
                    if(shouldSend){
                    	sendResponse(context, msg_from);
                    }
                    
                }catch(Exception e){
                            Log.e("onReceive", "Exception caught");
                }
            }
        }
    }
    
    public boolean checkSpeed(Context context){
    	location = Ontheway.location;//new Location(LocationManager.GPS_PROVIDER);
    	speed = location.getSpeed();
    	
    	speedInMph = (float) (2.237 * speed);
    	
    	
    	if(speedInMph < threshold){
        	Log.i("SmsReceiver", "Speed = " + speedInMph);
        	Toast.makeText(context, ("Speed = " + speedInMph + " mph"), Toast.LENGTH_SHORT).show();
        	return false;
    	} else {
    		Log.i("SmsReceiver", "Speed = " + speedInMph);
        	Toast.makeText(context, ("Speed = " + speedInMph + " mph"), Toast.LENGTH_SHORT).show();
        	return true; 
    	}
    }
    
    public void sendResponse(Context context, String destinationAddress){
    	SmsManager smsManager = SmsManager.getDefault();
    	String text = generateText();
    	String scAddress = null;
    	
//    	try{
//    		TelephonyManager tManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//    		scAddress = tManager.getLine1Number();
//    	} catch(Exception e) {
//    		//Do nothing... will send null.  //For some reasons there's problems when it's not null.
//    	}
    	
    	PendingIntent sentIntent, deliveryIntent;
    	
    	sentIntent = PendingIntent.getBroadcast(context, 0, new Intent("SMS_SENT"), 0);
    	deliveryIntent = PendingIntent.getBroadcast(context, 0, new Intent("SMS_DELIVERED"), 0);
    	
    	Log.d("SendResponse", "Try to send message");
    	
    	smsManager.sendTextMessage(destinationAddress, scAddress, text, sentIntent, deliveryIntent);
    	Log.d("SendResponse", "Sent message?");
    	
    }
    
    public String generateText(){
    	String [] text = {	"* Hey asshole...",
    						"* Are you trying to kill me?",
    						"* Aaaaaaaah dlkjfsdlfkjadkd!!! ",
    						"* Texting while driving makes a driver 23x more likely to crash. ",
    						"* Texting while driving keeps your eyes off the road 4 times more. ",
    						"* Texting while driving kills 11 teens each day. :-( "
    	};
    	
    	
    	int index = (int)(Math.random() * ((text.length) + 1));
    	return text[index] + " I'm driving. Ttyl. (Speed= " + speedInMph + " mph)";
    }
}