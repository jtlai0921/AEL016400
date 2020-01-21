package idv.ron.audioresdemo;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private SoundPool soundPool;
    private int soundId;
    private int streamID;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        //SoundPool建構式在API 21時列為deprecated；改用SoundPool.Builder來建立SoundPool實體
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool.Builder().build();
        } else {
            int maxStreams = 1;
            int streamType = AudioManager.STREAM_MUSIC;
            int srcQuality = 0;
            soundPool = new SoundPool(maxStreams, streamType, srcQuality);
        }
        int priority = 1;
        soundId = soundPool.load(this, R.raw.sound, priority);
    }

    public void onPlaySoundClick(View view) {
        if (soundPool != null) {
            int leftVolume = 1;
            int rightVolume = 1;
            int priority = 0;
            int loop = 0;
            int rate = 1;
            streamID = soundPool.play(
                    soundId, leftVolume, rightVolume, priority, loop, rate);
        }
    }

    public void onStopSoundClick(View view) {
        if (soundPool != null) {
            soundPool.stop(streamID);
        }
    }

    public void onPlayMp3Click(View view) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.music);
        }
        mediaPlayer.start();
    }

    public void onStopMp3Click(View view) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
