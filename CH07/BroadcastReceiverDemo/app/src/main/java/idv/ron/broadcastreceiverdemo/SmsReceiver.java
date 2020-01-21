package idv.ron.broadcastreceiverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.util.Date;

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String messages = "";
        String sender = "";
        Date date = null;
        Bundle bundle = intent.getExtras();
        Object[] pdus = (Object[]) bundle.get("pdus");

        if (pdus != null) {
            SmsMessage[] smsMessages = new SmsMessage[pdus.length];
            for (int i = 0; i < pdus.length; i++) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    smsMessages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], "3gpp");
                } else {
                    smsMessages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                messages += smsMessages[i].getDisplayMessageBody();
            }
            sender = smsMessages[0].getDisplayOriginatingAddress();
            date = new Date(smsMessages[0].getTimestampMillis());
        }

        Sms sms = new Sms(sender, messages, date);
        Intent i = new Intent(context, MainActivity.class);
        Bundle b = new Bundle();
        b.putString("text", sms.toString());
        i.putExtras(b);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}











