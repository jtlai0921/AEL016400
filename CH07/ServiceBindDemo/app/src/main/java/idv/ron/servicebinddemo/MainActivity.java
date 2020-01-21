package idv.ron.servicebinddemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tvMessage;
    private Button btPlay, btStop;
    private boolean isBound;
    private MusicService musicService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        findViews();
    }

    private void findViews() {
        tvMessage = (TextView) findViewById(R.id.tvMessage);
        btPlay = (Button) findViewById(R.id.btPlay);
        btStop = (Button) findViewById(R.id.btStop);
        btPlay.setVisibility(View.INVISIBLE);
        btStop.setVisibility(View.INVISIBLE);
    }

    public void onConnectClick(View view) {
        doBindService();
    }

    public void onDisconnectClick(View view) {
        doUnbindService();
    }

    public void onPlayClick(View view) {
        String message = musicService.play();
        tvMessage.setText(message);
    }

    public void onStopClick(View view) {
        String message = musicService.stop();
        tvMessage.setText(message);
    }

    void doBindService() {
        if (!isBound) {
            Intent intent = new Intent(this, MusicService.class);
            bindService(intent, serviceCon, Context.BIND_AUTO_CREATE);
            isBound = true;
        }
    }

    void doUnbindService() {
        if (isBound) {
            unbindService(serviceCon);
            isBound = false;
            btPlay.setVisibility(View.INVISIBLE);
            btStop.setVisibility(View.INVISIBLE);
            tvMessage.setText(R.string.msg_serviceDisconnected);
        }
    }

    private ServiceConnection serviceCon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder binder) {
            musicService = ((MusicService.ServiceBinder) binder).getService();
            tvMessage.setText(R.string.msg_serviceConnected);
            btPlay.setVisibility(View.VISIBLE);
            btStop.setVisibility(View.VISIBLE);
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            musicService = null;
            tvMessage.setText(R.string.msg_serviceLostConnection);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }
}
