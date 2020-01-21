package idv.ron.videoviewdemo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        videoView = (VideoView) findViewById(R.id.videoView);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
        videoView.setMediaController(new MediaController(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        String path = "android.resource://" + getPackageName() + "/" + R.raw.count_down;
        videoView.setVideoURI(Uri.parse(path));
        videoView.start();
    }
}
