package idv.ron.mediarecorderplayerdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private Button btRecord;
    private TextView tvMessage;
    private ListView listView;
    private String path;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        findViews();
    }

    private void findViews() {
        btRecord = (Button) findViewById(R.id.btRecord);
        tvMessage = (TextView) findViewById(R.id.tvMessage);
        listView = (ListView) findViewById(R.id.listView);

        File dir = getRecordDir();
        if (dir == null) {
            return;
        }
        List<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(dir.list()));
        arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = parent.getItemAtPosition(position).toString();
                File dir = getRecordDir();
                if (dir == null) {
                    tvMessage.setText(R.string.msg_DirNotFound);
                    return;
                }
                String path = new File(dir, name).getPath();
                playAudio(path);
            }
        });
    }

    // API 25 (Android 7.1)模擬器無法錄音，但API 23, 24模擬器和實機都沒問題
    public void onRecordClick(View view) {
        File dir = getRecordDir();
        if (dir == null) {
            tvMessage.setText(R.string.msg_DirNotFound);
            return;
        }
        String name = String.format(Locale.getDefault(),
                "%tY%<tm%<td_%<tH%<tM%<tS", new Date()) + ".3gp";
        path = new File(dir, name).getPath();
        if (recordAudio(path)) {
            tvMessage.setText(R.string.msg_Recording);
            btRecord.setEnabled(false);
        }
    }

    public void onStopRecordClick(View view) {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            String text = "File saved: " + path;
            tvMessage.setText(text);
            btRecord.setEnabled(true);

            File dir = new File(path).getParentFile();
            List<String> list = new ArrayList<>();
            list.addAll(Arrays.asList(dir.list()));
            arrayAdapter.clear();
            arrayAdapter.addAll(list);
            listView.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();
            listView.post(new Runnable() {
                @Override
                public void run() {
                    listView.setSelection(arrayAdapter.getCount() - 1);
                }
            });
        }
    }

    private boolean recordAudio(String path) {
        if (mediaRecorder == null) {
            mediaRecorder = new MediaRecorder();
        } else {
            mediaRecorder.reset();
        }
        try {
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile(path);
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }
        return true;
    }

    private void playAudio(String path) {
        if (path == null) {
            tvMessage.setText(R.string.msg_AudioFileNotExist);
            return;
        }
        tvMessage.setText(getString(R.string.msg_AudioFilePath) + " " + path);
        if (mediaPlayer == null)
            mediaPlayer = new MediaPlayer();
        else {
            mediaPlayer.reset();
        }
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    private boolean mediaMounted() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    private File getRecordDir() {
        if (!mediaMounted()) {
            return null;
        }
        return getExternalFilesDir(Environment.DIRECTORY_MUSIC);
    }

    private final static int REQ_PERMISSIONS = 0;

    @Override
    protected void onStart() {
        super.onStart();
        askPermissions();
    }

    private void askPermissions() {
        String[] permissions = {
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        Set<String> permissionsRequest = new HashSet<>();
        for (String permission : permissions) {
            int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionsRequest.add(permission);
            }
        }

        if (!permissionsRequest.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    permissionsRequest.toArray(new String[permissionsRequest.size()]),
                    REQ_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQ_PERMISSIONS:
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        String text = getString(R.string.text_ShouldGrant);
                        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                }
                break;
        }
    }
}
