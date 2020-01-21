package idv.ron.accelerometerdemo;

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
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
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
        @Override
        public void onSensorChanged(SensorEvent event) {
            Sensor sensor = event.sensor;
            String sensorInfo = "";
            sensorInfo += "sensor name: " + sensor.getName() + "\n";
            sensorInfo += "sensor type: " + sensor.getType() + "\n";
            sensorInfo += "used power: " + sensor.getPower() + " mA\n";
            sensorInfo += "values: \n";
            float[] values = event.values;
            for (int i = 0; i < values.length; i++) {
                sensorInfo += "-values[" + i + "] = " + values[i] + "\n";
            }
            tvMessage.setText(sensorInfo);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Called when the accuracy of a sensor has changed.
        }
    };
}
