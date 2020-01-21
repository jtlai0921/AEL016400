package idv.ron.servicedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btStart, btStop;
    private MyReceiver myReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        findViews();
        resetLayout(false);
        registerMyReceiver();
    }

    private void findViews() {
        btStart = (Button) findViewById(R.id.btStart);
        btStop = (Button) findViewById(R.id.btStop);
    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            showToast("Service starting");
        }
    }

    private void registerMyReceiver() {
        IntentFilter filter = new IntentFilter(MainService.ACTION_SERVICE_START);
        myReceiver = new MyReceiver();
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(myReceiver, filter);
    }

    public void onStartClick(View view) {
        Intent intent = new Intent(this, MainService.class);
        startService(intent);
        resetLayout(true);
    }

    public void onStopClick(View view) {
        Intent intent = new Intent(this, MainService.class);
        stopService(intent);
        resetLayout(false);
    }

    private void resetLayout(boolean isActive) {
        if (isActive) {
            btStart.setVisibility(View.GONE);
            btStop.setVisibility(View.VISIBLE);
        } else {
            btStart.setVisibility(View.VISIBLE);
            btStop.setVisibility(View.GONE);
        }
    }


    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver);
    }

    private void showToast(String text) {
        Toast.makeText(
                MainActivity.this,
                text,
                Toast.LENGTH_SHORT)
                .show();
    }
}