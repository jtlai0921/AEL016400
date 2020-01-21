package idv.ron.intentservicedemo;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class BootReceiver extends WakefulBroadcastReceiver {
    private final static String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Boot completed and starts MyIntentService");
        intent.setClass(context, MyIntentService.class);
        startWakefulService(context, intent);
    }
}
