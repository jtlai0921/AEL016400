package idv.ron.servicebinddemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class MusicService extends Service {
    private final IBinder binder = new ServiceBinder();

    public String play() {
        return getString(R.string.msg_musicPlay);
    }

    public String stop() {
        return getString(R.string.msg_musicStop);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "onBind", Toast.LENGTH_SHORT).show();
        return binder;
    }

    public class ServiceBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this, "onUnbind", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
