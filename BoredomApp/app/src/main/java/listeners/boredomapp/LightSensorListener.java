package listeners.boredomapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.islab.boredomappfase1.MainActivity;

import androidx.annotation.RequiresApi;

public class LightSensorListener implements SensorEventListener {

    private static final long NOTIFICATION_INTERVAL = 60000;
    private long timeLastNotification = 0;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            MainActivity.lightSensor =  Math.round(event.values[0]);
            //System.out.println("$$$INFO LIGHT_SENSOR: " + MainActivity.lightSensor);
            System.out.println("$$$NOT PROXIMITY: " + MainActivity.proximitySensor);
            System.out.println("$$$NOT LIGHT_SENSOR: " + MainActivity.lightSensor);
            System.out.println("$$$NOT CHATTING: " + MainActivity.chattingNotifications);
            System.out.println("$$$NOT SOCIAL: " + MainActivity.socialNotifications);
            System.out.println("$$$NOT OTHERS: " + MainActivity.othersNotifications);
            System.out.println("$$$NOT ATUAL: " + MainActivity.counterNotifications);
            System.out.println("$$$NOT CALLS_IN: " + MainActivity.counterIncomingCalls);
            System.out.println("$$$NOT CALLS_OUT: " + MainActivity.counterOutgoingCalls);
            System.out.println("$$$NOT LAST_NOT: " + timeLastNotification);

            // if passed more than NOTIFICATION_INTERVAL between notification
            if(System.currentTimeMillis() > timeLastNotification + NOTIFICATION_INTERVAL) {

                // Create channel
                String channelId = "channel-id";
                String channelName = "Channel Name";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(
                        channelId, channelName, importance);
                NotificationManager notificationManager = (NotificationManager) MainActivity.context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(mChannel);

                // send BOREDOM notification if certain conditions triggered
                if (MainActivity.proximitySensor == 1 &&
                        MainActivity.lightSensor < 500 &&
                        (MainActivity.chattingNotifications < 2 && MainActivity.socialNotifications < 2 && MainActivity.othersNotifications < 2) &&
                        MainActivity.counterNotifications < 1 &&
                        (MainActivity.counterIncomingCalls < 1 || MainActivity.counterOutgoingCalls < 1)
                ) {
                    // Build notification
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.context, channelId)
                            .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                            .setContentTitle("Aborrecido?")
                            .setContentText("Pareces aborrecido. Larga o telemóvel e vai viver a vida!")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                    // Save time from last notification
                    timeLastNotification = System.currentTimeMillis();

                    // Show notification
                    notificationManager.notify(1, builder.build());

                }
                // send LOW LIGHT AMBIENT notification
                else if (MainActivity.lightSensor < 25) {
                    // Build notification
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.context, channelId)
                            .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                            .setContentTitle("Pouca luz ambiente!")
                            .setContentText("Considere aumentar a luminosidade à sua volta.")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                    // Save time from last notification
                    timeLastNotification = System.currentTimeMillis();

                    // Show notification
                    notificationManager.notify(1, builder.build());
                }
                // send TOO MUCH CALLS notification
                else if (MainActivity.counterOutgoingCalls > 4) {
                    // Build notification
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.context, channelId)
                            .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                            .setContentTitle("Tantas chamadas?")
                            .setContentText("Cuidado com as distrações possíveis.")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                    // Save time from last notification
                    timeLastNotification = System.currentTimeMillis();

                    // Show notification
                    notificationManager.notify(1, builder.build());
                }

                // Clearing data between NOTIFICATION_INTERVAL
                MainActivity.chattingNotifications = 0;
                MainActivity.socialNotifications = 0;
                MainActivity.othersNotifications = 0;
                MainActivity.counterNotifications = 0;
                MainActivity.counterIncomingCalls = 0;
                MainActivity.counterOutgoingCalls = 0;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        return;
    }

}