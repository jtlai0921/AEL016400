package idv.ron.audioexternaldemo;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    private final static String AUDIO_FILE = "ring.mp3";
    public static final int PERMISSION_WRITE_EXTERNAL_STORAGE = 0;

    private String audioPath;
    private MediaPlayer mediaPlayer;
    private TextView tvMessage;
    private Button btPlayExternal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        findViews();
    }

    private void findViews() {
        tvMessage = (TextView) findViewById(R.id.tvMessage);
        btPlayExternal = (Button) findViewById(R.id.btPlayExternal);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        askPermissions(this, permissions, PERMISSION_WRITE_EXTERNAL_STORAGE);
    }

    // 點擊Play Audio from the External Storage按鈕開始播放外部儲存體的audio檔案
    public void onPlayExternalClick(View view) {
        playAudio(audioPath);
    }

    // 點擊Play Audio File from URL按鈕開始播放網路影音資料串流
    public void onPlayUrlClick(View view) {
        String url = "http://sites.google.com/site/ronforwork/Home/android-2/ring.mp3";
        playAudio(url);
    }

    // 點擊Stop按鈕停止播放
    public void onStopClick(View view) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    // 播放audio檔案
    private void playAudio(String path) {
        // 如果audio檔案路徑為null就顯示錯誤訊息
        if (path == null) {
            tvMessage.setText(R.string.msg_AudioFileNotExist);
            return;
        }
        tvMessage.setText(getString(R.string.msg_AudioFilePath) + " " + path);
        // 如果MediaPlayer為null，就建立物件實體
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        } else {
            // 播放過audio檔案後需要呼叫reset()重置才能再次播放
            mediaPlayer.reset();
        }
        try {
            // 指定audio檔案的位置
            mediaPlayer.setDataSource(path);
            // 指定audio檔案類型為音樂，使用者欲調整音量就必須調整音樂的音量
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            // 準備播放，MediaPlayer需要註冊監聽器，等到準備好了才播放
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    // 將assets內的audio檔案複製到外部儲存體供之後播放
    private String copyAudioToExternal() {
        if (!mediaMounted()) {
            return null;
        }
        // 取得外部儲存體的MUSIC目錄路徑
        File dir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MUSIC);
        File audioFile = new File(dir, AUDIO_FILE);
        // 如果audio檔案已經存在就回傳路徑，而無需再做檔案複製
        if (audioFile.exists()) {
            return audioFile.getPath();
        }

        InputStream is = null;
        OutputStream os = null;
        try {
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    return null;
                }
            }
            // 從assets目錄讀入audio檔案後複製到外部儲存體，並回傳複製後的檔案路徑
            is = getAssets().open(AUDIO_FILE);
            File file = new File(dir, AUDIO_FILE);
            os = new FileOutputStream(file);
            byte[] buffer = new byte[is.available()];
            while (is.read(buffer) != -1) {
                os.write(buffer);
            }
            return file.getPath();
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return null;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                Log.e(TAG, e.toString());
            }
        }
    }

    // 外部儲存體是否處於已掛載狀態(代表可讀寫資料)
    private boolean mediaMounted() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    // 畫面被切換時釋放MediaPlayer資源並設為null
    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    // New Permission see Appendix A
    public void askPermissions(Activity activity, String[] permissions, int requestCode) {
        Set<String> permissionsRequest = new HashSet<>();
        for (String permission : permissions) {
            int result = ContextCompat.checkSelfPermission(activity, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionsRequest.add(permission);
            }
        }

        if (!permissionsRequest.isEmpty()) {
            ActivityCompat.requestPermissions(activity,
                    permissionsRequest.toArray(new String[permissionsRequest.size()]),
                    requestCode);
        } else {
            audioPath = copyAudioToExternal();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //若使用者同意存取外部儲存體，複製assets目錄內的ring.mp3檔案至外部儲存體並讓按鈕可以運作
                    audioPath = copyAudioToExternal();
                    btPlayExternal.setEnabled(true);
                } else {
                    //若使用者不同意存取，讓按鈕失效以免產生執行錯誤
                    btPlayExternal.setEnabled(false);
                }
                break;
        }
    }
}
