package listeners.boredomapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import com.islab.boredomappfase1.MainActivity;

public class ProximitySensorListener implements SensorEventListener {

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            // is near
            if(event.values[0] == 0) {
                MainActivity.proximitySensor = 0;
            }
            else {
                MainActivity.proximitySensor = 1;
            }
            System.out.println("$$$INFO PROX_SENSOR: " + MainActivity.proximitySensor);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        return;
    }

}
