package receivers.boredomapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.islab.boredomappfase1.MainActivity;

public class KeysReceiver extends BroadcastReceiver {

    final String SYSTEM_DIALOG_REASON_KEY = "reason";
    final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
    final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)) {
            String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
            if (reason != null) {
                if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                    MainActivity.homeKeyPressed++;
                }
                else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
                    MainActivity.recentAppsKeyPressed++;
                    System.out.println("$$$INFO #RECENT_APPS_BUTTON = " + MainActivity.recentAppsKeyPressed);
                }
            }
        }
    }
}
