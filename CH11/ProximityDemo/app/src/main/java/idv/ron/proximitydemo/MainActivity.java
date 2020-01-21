package idv.ron.proximitydemo;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private SensorManager sensorManager;
    private LinearLayout linearLayout;
    private TextView tvMessage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        findViews();
    }

    private void findViews() {
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        tvMessage = (TextView) findViewById(R.id.tvMessage);
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean enable = sensorManager.registerListener(listener,
                sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
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
            sensorInfo += "sensor maximum range: " +
                    sensor.getMaximumRange() + "\n";
            sensorInfo += "values[0] = " + values[0] + "\n";
            tvMessage.setText(sensorInfo);

            if (values[0] < 1.0) {
                linearLayout.setBackgroundColor(Color.rgb(0, 220, 220));
            } else {
                linearLayout.setBackgroundColor(Color.rgb(220, 220, 0));
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Called when the accuracy of a sensor has changed.
        }
    };
}
