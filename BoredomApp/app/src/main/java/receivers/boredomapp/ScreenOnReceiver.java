package receivers.boredomapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.islab.boredomappfase1.MainActivity;

public class ScreenOnReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(Intent.ACTION_SCREEN_ON.equals(action)) {
            MainActivity.counterScreenOn++;
            System.out.println("$$$INFO #SCREEN_ON = " + MainActivity.counterScreenOn);
        }
    }
}