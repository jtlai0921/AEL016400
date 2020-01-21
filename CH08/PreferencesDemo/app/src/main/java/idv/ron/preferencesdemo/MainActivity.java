package idv.ron.preferencesdemo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final static String PREFERENCES_NAME = "preferences";
    private final static String DEFAULT_FILE_NAME = "image";
    private final static boolean DEFAULT_AUTO_FOCUS = true;
    private final static int DEFAULT_AUTO_SHOT_TIME = 10;

    private EditText etFileName, etAutoShotTime;
    private RadioButton rbYes, rbNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        findViews();
        loadPreferences();
    }

    private void findViews() {
        etFileName = (EditText) findViewById(R.id.etFileName);
        etAutoShotTime = (EditText) findViewById(R.id.etAutoShotTime);
        rbYes = (RadioButton) findViewById(R.id.rbYes);
        rbNo = (RadioButton) findViewById(R.id.rbNo);
    }

    private void loadPreferences() {
        SharedPreferences preferences =
                getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        String fileName = preferences.getString("fileName", DEFAULT_FILE_NAME);
        boolean autoFocus = preferences.getBoolean("autoFocus", DEFAULT_AUTO_FOCUS);
        int autoShotTime = preferences.getInt("autoShotTime", DEFAULT_AUTO_SHOT_TIME);

        etFileName.setText(fileName);
        if (autoFocus) {
            rbYes.setChecked(true);
        } else {
            rbNo.setChecked(true);
        }
        etAutoShotTime.setText(String.valueOf(autoShotTime));
    }

    private void restoreDefaults() {
        etFileName.setText(DEFAULT_FILE_NAME);
        rbYes.setChecked(DEFAULT_AUTO_FOCUS);
        etAutoShotTime.setText(String.valueOf(DEFAULT_AUTO_SHOT_TIME));
    }

    public void onPrefSaveClick(View view) {
        SharedPreferences preferences =
                getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);

        String fileName = etFileName.getText().toString();

        boolean autoFocus = rbYes.isChecked();

        int autoShotTime;
        try {
            autoShotTime = Integer.parseInt(etAutoShotTime.getText().toString());
        } catch (NumberFormatException e) {
            showToast(R.string.msg_RequireNumber);
            return;
        }

        preferences.edit()
                .putString("fileName", fileName)
                .putBoolean("autoFocus", autoFocus)
                .putInt("autoShotTime", autoShotTime)
                .apply();
        showToast(R.string.msg_preferencesSaved);
    }

    public void onPrefLoadClick(View view) {
        loadPreferences();
        showToast(R.string.msg_preferencesLoaded);
    }

    public void onDefaultClick(View view) {
        restoreDefaults();
        showToast(R.string.msg_DefaultsRestored);
    }

    private void showToast(int messageResId) {
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
}
