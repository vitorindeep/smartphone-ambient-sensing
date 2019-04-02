package receivers.boredomapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.islab.boredomappfase1.MainActivity;

/**
 * Broadcast receiver to detect the outgoing calls.
 */
public class OutgoingCallReceiver extends BroadcastReceiver {

    private static long timeLastOutgoing = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        // if less than 5 sec passed, is just a repeated Broadcast, so we ignore it
        if(System.currentTimeMillis() < timeLastOutgoing + 5000) return;

        if(Intent.ACTION_NEW_OUTGOING_CALL.equals(action)) {
            // save last outgoing call time to avoid duplicates
            timeLastOutgoing = System.currentTimeMillis();
            // increase counter for outgoing calls
            MainActivity.counterOutgoingCalls++;
            System.out.println("$$$INFO #OUTGOING_CALLS = " + MainActivity.counterOutgoingCalls);
        }

    }
}