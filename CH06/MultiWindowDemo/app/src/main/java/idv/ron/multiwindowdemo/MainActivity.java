package idv.ron.multiwindowdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        tvMessage = (TextView) findViewById(R.id.tvMessage);
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        showMultiWindowMode(isInMultiWindowMode);
    }

    @Override
    protected void onStart() {
        super.onStart();
        showMultiWindowMode(isInMultiWindowMode());
    }

    private void showMultiWindowMode(boolean isInMultiWindowMode) {
        if (!isInMultiWindowMode) {
            tvMessage.setText(R.string.text_MultiWindowDisabled);
        } else {
            tvMessage.setText(R.string.text_MultiWindowEnabled);
        }
    }

    public void onAdjacentActivityClick(View view) {
        /*
         * Start this activity adjacent to the focused activity (ie. this activity) if possible.
         * Note that this flag is just a hint to the system and may be ignored. For example,
         * if the activity is launched within the same task, it will be launched on top of the
         * previous activity that started the Intent. That's why the Intent.FLAG_ACTIVITY_NEW_TASK
         * flag is specified here in the intent - this will start the activity in a new task.
         */
        Intent intent = new Intent(this, AdjacentActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void onUnresizableActivityClick(View view) {
        Intent intent = new Intent(this, UnresizableActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
