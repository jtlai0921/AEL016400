package idv.ron.intentservicedemo;

import android.app.IntentService;
import android.content.Intent;

public class MyIntentService extends IntentService {
    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        intent.setClass(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        BootReceiver.completeWakefulIntent(intent);
    }
}
