package idv.ron.broadcastreceiverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;

public class PhoneReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String phoneState = bundle.getString(TelephonyManager.EXTRA_STATE);
        String incomingNumber;
        if (phoneState != null &&
                phoneState.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            incomingNumber = bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            String text = "Phone state: " + phoneState + "\nIncoming number: " + incomingNumber;
            Intent i = new Intent(context, MainActivity.class);
            Bundle b = new Bundle();
            b.putString("text", text);
            i.putExtras(b);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}
