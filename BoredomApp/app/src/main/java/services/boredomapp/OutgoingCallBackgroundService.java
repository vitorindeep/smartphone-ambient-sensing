package services.boredomapp;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import receivers.boredomapp.OutgoingCallReceiver;

public class OutgoingCallBackgroundService extends Service {

    private OutgoingCallReceiver outgoingCallReceiver = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Create an IntentFilter instance.
        IntentFilter intentFilter = new IntentFilter();

        // Add network connectivity change action.
        intentFilter.addAction("android.intent.action.NEW_OUTGOING_CALL");

        // Set broadcast receiver priority.
        intentFilter.setPriority(100);

        // Create a network change broadcast receiver.
        outgoingCallReceiver = new OutgoingCallReceiver();

        // Register the broadcast receiver with the intent filter object.
        registerReceiver(outgoingCallReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Unregister screenOnOffReceiver when destroy.
        if(outgoingCallReceiver!=null)
        {
            unregisterReceiver(outgoingCallReceiver);
        }
    }
}