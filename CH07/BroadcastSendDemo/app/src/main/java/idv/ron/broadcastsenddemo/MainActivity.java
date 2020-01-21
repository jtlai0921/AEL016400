package idv.ron.broadcastsenddemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String BROADCAST_ACTION =
            "idv.ron.broadcastsenddemo.test";
    private MyReceiver myReceiver;
    private LocalBroadcastManager localBroadcastManager;

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            showToast("Broadcast received");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        registerMyReceiver();
    }


    private void registerMyReceiver() {
        IntentFilter filter = new IntentFilter(BROADCAST_ACTION);
        myReceiver = new MyReceiver();
        localBroadcastManager.registerReceiver(myReceiver, filter);
        showToast("Broadcast registered");
    }

    private void showToast(String text) {
        Toast.makeText(
                MainActivity.this,
                text,
                Toast.LENGTH_SHORT)
                .show();
    }

    public void onSendClick(View view) {
        Intent intent = new Intent(BROADCAST_ACTION);
        localBroadcastManager.sendBroadcast(intent);
        showToast("Broadcast sent");
    }

    public void onUnregisterClick(View view) {
        localBroadcastManager.unregisterReceiver(myReceiver);
        showToast("BroadcastReceiver unregistered");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(myReceiver);
    }
}
