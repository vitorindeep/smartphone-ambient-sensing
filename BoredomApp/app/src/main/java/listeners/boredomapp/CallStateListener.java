package listeners.boredomapp;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import com.islab.boredomappfase1.MainActivity;

/**
 * Listener to detect incoming calls.
 */
public class CallStateListener extends PhoneStateListener {
    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                // called when someone is ringing to this phone
                MainActivity.counterIncomingCalls++;
                System.out.println("$$$INFO #INCOMING_CALLS = " + MainActivity.counterIncomingCalls);
        }
    }
}