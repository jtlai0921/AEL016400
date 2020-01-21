package idv.ron.externalstoragedemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    private final static String FILE_NAME_PUBLIC = "boy.gif";
    private final static String FILE_NAME_PRIVATE = "girl.gif";
    private ImageView ivPicture;
    private TextView tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        findViews();
    }

    private void findViews() {
        ivPicture = (ImageView) findViewById(R.id.ivPicture);
        tvMessage = (TextView) findViewById(R.id.tvMessage);
    }

    public void onSavePublicClick(View view) {
        File dir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        saveFile(dir, FILE_NAME_PUBLIC);
    }

    public void onSavePrivateClick(View view) {
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        saveFile(dir, FILE_NAME_PRIVATE);
    }

    public void onOpenPublicClick(View view) {
        File dir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        openFile(dir, FILE_NAME_PUBLIC);
    }

    public void onOpenPrivateClick(View view) {
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        openFile(dir, FILE_NAME_PRIVATE);
    }

    private void saveFile(File dir, String fileName) {
        if (!mediaMounted()) {
            showToast(R.string.msg_ExternalStorageNotFound);
            return;
        }
        InputStream is = null;
        OutputStream os = null;
        try {
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    showToast(R.string.msg_DirectoryNotCreated);
                    return;
                }
            }
            is = getAssets().open(fileName);
            File file = new File(dir, fileName);
            os = new FileOutputStream(file);
            byte[] buffer = new byte[is.available()];
            while (is.read(buffer) != -1) {
                os.write(buffer);
            }
            String text = getString(R.string.msg_SavedFilePath) +
                    "\n" + file.toString();
            tvMessage.setText(text);
        } catch (IOException e) {
            Log.e(TAG, e.toString());
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

    private void openFile(File dir, String fileName) {
        if (!mediaMounted()) {
            showToast(R.string.msg_ExternalStorageNotFound);
            return;
        }
        File file = new File(dir, fileName);
        Bitmap bitmap = BitmapFactory.decodeFile(file.toString());
        if (bitmap != null) {
            ivPicture.setImageBitmap(bitmap);
            String text = getString(R.string.msg_OpenedFilePath) +
                    "\n" + file.toString();
            tvMessage.setText(text);
        } else {
            ivPicture.setImageResource(R.drawable.picture_not_found);
            showToast(R.string.msg_FileNotFound);
        }
    }

    private boolean mediaMounted() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    private void showToast(int messageResId) {
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    private final static int REQ_PERMISSIONS = 0;

    @Override
    protected void onStart() {
        super.onStart();
        askPermissions();
    }

    private void askPermissions() {
        String[] permissions = {
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
