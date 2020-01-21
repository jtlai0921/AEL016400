package idv.ron.recordvideodemo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_RECORD_VIDEO = 0;
    private TextView tvMessage;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        videoView = (VideoView) findViewById(R.id.videoView);
        tvMessage = (TextView) findViewById(R.id.tvMessage);
    }

    public void onRecordVideoClick(View view) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (isIntentAvailable(this, intent)) {
            startActivityForResult(intent, REQUEST_RECORD_VIDEO);
        } else {
            Toast.makeText(this, R.string.msg_NoCameraAppsFound, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_RECORD_VIDEO:
                    Uri uri = intent.getData();
                    tvMessage.setText(getRealPathFromUri(uri));
                    MediaController mediaController = new MediaController(this);
                    videoView.setMediaController(mediaController);
                    videoView.setVideoURI(uri);
                    videoView.start();
                    break;
            }
        }
    }

    private boolean isIntentAvailable(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private String getRealPathFromUri(Uri uri) {
        String[] columns = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, columns,
                null, null, null);
        String path = null;
        if (cursor != null && cursor.moveToFirst()) {
            path = cursor.getString(0);
            cursor.close();
        }
        return path;
    }

    @Override
    protected void onStart() {
        super.onStart();
        askPermissions();
    }

    private final static int REQ_PERMISSIONS = 0;

    private void askPermissions() {
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE
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