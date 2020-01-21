package idv.ron.orientationdemo;

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
    private float[] accelerometer_values = null;
    private float[] magnitude_values = null;

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
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI)
                &&
                sensorManager.registerListener(listener,
                        sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
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
            switch (event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    accelerometer_values = event.values.clone();
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    magnitude_values = event.values.clone();
                    break;
                default:
                    break;
            }

            if (magnitude_values == null || accelerometer_values == null) {
                return;
            }

            float[] R = new float[9];
            float[] values = new float[3];
            SensorManager.getRotationMatrix(R, null,
                    accelerometer_values, magnitude_values);
            SensorManager.getOrientation(R, values);
            String sensorInfo = "";
            for (int i = 0; i < values.length; i++)
                sensorInfo += "-values[" + i + "] = " + Math.toDegrees(values[i]) + "\n";
            tvMessage.setText(sensorInfo);
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Called when the accuracy of a sensor has changed.
        }
    };
}
