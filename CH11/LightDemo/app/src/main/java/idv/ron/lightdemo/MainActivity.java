package idv.ron.lightdemo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private SensorManager sensorManager;
    private TextView tvMessage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        findViews();
    }

    private void findViews() {
        tvMessage = (TextView) findViewById(R.id.tvMessage);
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean enable = sensorManager.registerListener(listener,
                sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
                SensorManager.SENSOR_DELAY_UI);
        if (!enable) {
            sensorManager.unregisterListener(listener);
            Log.e(TAG, getString(R.string.msg_SensorNotSupported));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listener);
    }

    SensorEventListener listener = new SensorEventListener() {
        public void onSensorChanged(SensorEvent event) {
            Sensor sensor = event.sensor;
            float[] values = event.values;
            String sensorInfo = "";
            sensorInfo += "sensor name: " + sensor.getName() + "\n";
            sensorInfo += "sensor type: " + sensor.getType() + "\n";
            sensorInfo += "used power: " + sensor.getPower() + " mA\n";
            sensorInfo += getString(R.string.maxRange) +
                    sensor.getMaximumRange() + "\n";
            sensorInfo += "values[0] = " + values[0] + "\n";

            if (values[0] >= 10000) {
                sensorInfo += getString(R.string.anyThing);
            } else if (values[0] >= 7000) {
                sensorInfo += getString(R.string.surgery);
            } else if (values[0] >= 500) {
                sensorInfo += getString(R.string.read);
            } else if (values[0] >= 100) {
                sensorInfo += getString(R.string.dailyLife);
            } else if (values[0] >= 30) {
                sensorInfo += getString(R.string.watchTV);
            } else if (values[0] >= 5) {
                sensorInfo += getString(R.string.walk);
            } else {
                sensorInfo += getString(R.string.sleep);
            }

            tvMessage.setText(sensorInfo);
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Called when the accuracy of a sensor has changed.
        }
    };
}
