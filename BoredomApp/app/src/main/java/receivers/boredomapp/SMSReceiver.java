package receivers.boredomapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.islab.boredomappfase1.MainActivity;

public class SMSReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            MainActivity.counterReceivedSMS++;
            System.out.println("$$$INFO #RECEIVED_SMS: " + MainActivity.counterReceivedSMS);
        }
    }
}