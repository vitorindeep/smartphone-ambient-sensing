package com.islab.boredomappfase1;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileOutputStream;
import java.io.IOException;

import listeners.boredomapp.CallStateListener;
import listeners.boredomapp.LightSensorListener;
import listeners.boredomapp.ProximitySensorListener;
import receivers.boredomapp.KeysReceiver;
import services.boredomapp.NotificationService;
import services.boredomapp.OutgoingCallBackgroundService;
import services.boredomapp.SMSService;
import services.boredomapp.ScreenOnBackgroundService;

public class MainActivity extends AppCompatActivity {
    public static int counterNotifications;
    public static int socialNotifications;
    public static int chattingNotifications;
    public static int othersNotifications;
    public static int counterScreenOn;
    public static int counterIncomingCalls;
    public static int counterOutgoingCalls;
    public static int proximitySensor;
    public static SensorManager mySensorManager;
    public static Sensor myProximitySensor;
    public static int counterReceivedSMS;
    public static int lightSensor;
    public static Sensor myLightSensor;
    public static int homeKeyPressed;
    public static int recentAppsKeyPressed;
    public static ConnectivityManager cm;
    public static Context context;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Entries");
    private android.app.AlertDialog enableNotificationListenerAlertDialog;
    private int SENSOR_DELAY_CUSTOM = 10000000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        askForPermissions();
        startupNotificationService();
        startupScreenService();
        startupIncomingCallsService();
        startupOutgoingCallsService();
        startupProximitySensorService();
        startupSMSService();
        startupLightSensor();
        startupKeysService();
        startupConnectiityService();
    }

    public void visualizeData(View view) {
        Intent intent = new Intent(this, ViewListActivity.class);
        startActivity(intent);
    }

    public void question(View view) {
        RadioGroup radioGroup = findViewById(R.id.question);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton yes = findViewById(R.id.yes);
        String choice;
        if (selectedId == yes.getId()) choice = "yes";
        else choice = "no";
        try {
            FileOutputStream fos = openFileOutput("data.txt", MODE_APPEND);
            String writeLine = getBatteryLevel() + "," + socialNotifications + "," + chattingNotifications + "," + othersNotifications + "," + counterNotifications + ","
                    + counterScreenOn + "," + counterOutgoingCalls + "," + counterIncomingCalls + "," + proximitySensor + "," + counterReceivedSMS + "," + lightSensor + ","
                    + getScreenRotation() + "," + homeKeyPressed + "," + recentAppsKeyPressed + "," + getWifiStatus() + "," + getMobileConnectionStatus() + "," + choice + "\n";
            fos.write(writeLine.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Entry entrada = new Entry(getBatteryLevel() + "", socialNotifications + "", chattingNotifications + "", othersNotifications + ""
                , counterNotifications + "", counterScreenOn + "", counterOutgoingCalls + "", counterIncomingCalls + ""
                , proximitySensor + "", counterReceivedSMS + "", lightSensor + "", getScreenRotation() + "", homeKeyPressed + ""
                , recentAppsKeyPressed + "", getWifiStatus() + "", getMobileConnectionStatus() + "", choice + "");
        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) return;
        String imei = tm.getDeviceId();
        myRef.child(imei).child(System.currentTimeMillis()+"").setValue(entrada);
        Intent intent = new Intent(this, ThankYouActivity.class);
        startActivity(intent);
    }

    private void startupConnectiityService() {

        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    private void startupKeysService() {

        // initialize parameters
        homeKeyPressed = 0;
        recentAppsKeyPressed = 0;

        // register to keys receiver
        KeysReceiver keysReceiver = new KeysReceiver();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(keysReceiver, intentFilter);
    }

    private void startupLightSensor() {

        // initialize parameters
        lightSensor = 0;

        // check sensor availability
        if(checkLightSensorAvailable()) {
            // get Proximity Sensor Listener
            LightSensorListener lightSensorEventListener = new LightSensorListener();
            // register listener
            mySensorManager.registerListener(lightSensorEventListener,
                    myLightSensor,
                    SENSOR_DELAY_CUSTOM);
        }
    }

    private boolean checkLightSensorAvailable() {

        myLightSensor = mySensorManager.getDefaultSensor(
                Sensor.TYPE_LIGHT);
        if (myLightSensor == null) {
            return false;
        } else {
            return true;
        }
    }

    private void askForPermissions() {

        // The request code used in ActivityCompat.requestPermissions()
        // and returned in the Activity's onRequestPermissionsResult()
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                android.Manifest.permission.PROCESS_OUTGOING_CALLS,
                android.Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_PHONE_STATE
        };

        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
    }

    private void startupSMSService() {

        // initialize parameters
        counterReceivedSMS = 0;

        // check and ask for sms read permissions
        //isSMSReceivePermissionGranted();

        // Start the service to collect data
        Intent SMSIntent = new Intent(this, SMSService.class);
        startService(SMSIntent);
    }

    private void startupProximitySensorService() {

        // initialize parameters
        proximitySensor = 0;

        // check sensor availability
        if(checkProximitySensorAvailable()) {
            // get Proximity Sensor Listener
            ProximitySensorListener proximitySensorEventListener = new ProximitySensorListener();
            // register listener
            mySensorManager.registerListener(proximitySensorEventListener,
                    myProximitySensor,
                    SENSOR_DELAY_CUSTOM);
        }
    }

    private boolean checkProximitySensorAvailable() {

        mySensorManager = (SensorManager) getSystemService(
                Context.SENSOR_SERVICE);
        myProximitySensor = mySensorManager.getDefaultSensor(
                Sensor.TYPE_PROXIMITY);
        if (myProximitySensor == null) {
            return false;
        } else {
            return true;
        }
    }

    private void startupOutgoingCallsService() {

        // initialize parameters
        counterOutgoingCalls = 0;

        // check and ask for outgoing calls permissions
        //isOutgoingCallsPermissionGranted();

        // Start the service to collect data
        Intent outgoingCallsIntent = new Intent(this, OutgoingCallBackgroundService.class);
        startService(outgoingCallsIntent);
    }

    private void startupIncomingCallsService() {

        // initialize parameters
        counterIncomingCalls = 0;

        // initialize listener
        CallStateListener callStateListener = new CallStateListener();

        // subscribe to PhoneStateListener
        TelephonyManager telephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        telephony.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    private void startupScreenService() {

        // initialize parameters
        counterScreenOn = 0;

        // Start the service to collect data
        Intent screenBackgroundIntent = new Intent(this, ScreenOnBackgroundService.class);
        startService(screenBackgroundIntent);
    }

    private void startupNotificationService() {

        // initialize parameters
        counterNotifications = 0;
        socialNotifications = 0;
        chattingNotifications = 0;
        othersNotifications = 0;

        // If the user did not turn the notification listener service on we prompt him to do so
        if(!isNotificationServiceEnabled()){
            enableNotificationListenerAlertDialog = buildNotificationServiceAlertDialog();
            enableNotificationListenerAlertDialog.show();
        }

        // Start the service to collect data
        Intent notificationIntent = new Intent(this, NotificationService.class);
        startService(notificationIntent);
    }

    /** Get the battery level on snapshot time */
    public int getBatteryLevel() {
        Intent batteryIntent = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        // Error checking that probably isn't needed but I added just in case.
        if(level == -1 || scale == -1) {
            return 50;
        }

        return Math.round(((float)level / (float)scale) * 100.0f);
    }

    /** Get the screen orientation on snapshot time */
    public int getScreenRotation() {
        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            System.out.println("$$$ ORIENTATION: LANDSCAPE");
            return 1;
        } else {
            System.out.println("$$$ ORIENTATION: PORTRAIT");
            return 0;
        }
    }

    /** Get the wifi status on snapshot time */
    public int getWifiStatus() {

        if (cm.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
            System.out.println("$$$ WIFI_STATUS: ON");
            return 1;
        }
        else {
            System.out.println("$$$ WIFI_STATUS: OFF");
            return 0;
        }
    }

    /** Get the wifi status on snapshot time */
    public int getMobileConnectionStatus() {

        if (cm.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED) {
            System.out.println("$$$ MOBILE_STATUS: ON");
            return 1;
        }
        else {
            System.out.println("$$$ MOBILE_STATUS: OFF");
            return 0;
        }
    }

    /**
     * Is Notification Service Enabled.
     * Verifies if the notification listener service is enabled.
     * Got it from: https://github.com/kpbird/NotificationListenerService-Example/blob/master/NLSExample/src/main/java/com/kpbird/nlsexample/NLService.java
     * @return True if eanbled, false otherwise.
     */
    private boolean isNotificationServiceEnabled(){
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (String name : names) {
                final ComponentName cn = ComponentName.unflattenFromString(name);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Build Notification Listener Alert Dialog.
     * Builds the alert dialog that pops up if the user has not turned
     * the Notification Listener Service on yet.
     * @return An alert dialog which leads to the notification enabling screen
     */
    private android.app.AlertDialog buildNotificationServiceAlertDialog(){
        android.app.AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.notification_listener_service);
        alertDialogBuilder.setMessage(R.string.notification_listener_service_explanation);
        alertDialogBuilder.setPositiveButton(R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
                    }
                });
        alertDialogBuilder.setNegativeButton(R.string.no,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // If you choose to not enable the notification listener
                        // the app. will not work as expected
                    }
                });
        return(alertDialogBuilder.create());
    }

    /**
     * Build Notification Listener Alert Dialog.
     * Builds the alert dialog that pops up if the user has not turned
     * the Notification Listener Service on yet.
     * @return An alert dialog which leads to the notification enabling screen
     */
    public boolean isOutgoingCallsPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.PROCESS_OUTGOING_CALLS)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.PROCESS_OUTGOING_CALLS}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    private void isSMSReceivePermissionGranted() {

        // only needed fot android version 23+
        if (Build.VERSION.SDK_INT >= 23) {
            // if we already have SMS read permission, return
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED) {
                return;
            }
            // otherwise we ask for permission
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, 1);
            }
        }
        // permission granted on install for android 22-
        else {
            return;
        }
    }
}