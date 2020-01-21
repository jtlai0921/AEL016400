package idv.ron.servicedemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.content.LocalBroadcastManager;

public class MainService extends Service {
    private final static int NOTIFICATION_ID = 0;
    public final static String ACTION_SERVICE_START = "idv.ron.servicedemo.service.start";
    private PowerManager.WakeLock wakeLock;
    NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakeLock");
        wakeLock.acquire();

        notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).
                sendBroadcast(new Intent(ACTION_SERVICE_START));
        return START_STICKY;
    }

    private void showNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, 0);
        Notification notification = new Notification.Builder(this)
                .setTicker("Service Stopped")
                .setContentTitle("Service Stopped")
                .setContentText("Service stopped!!")
                .setSmallIcon(android.R.drawable.ic_menu_info_details)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    @Override
    public void onDestroy() {
        showNotification();
        wakeLock.release();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}