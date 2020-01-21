package idv.ron.internalstoragedemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    private final static String FILE_NAME = "input.txt";
    private EditText etInput;
    private TextView tvInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        findViews();
    }

    private void findViews() {
        etInput = (EditText) findViewById(R.id.etInput);
        tvInput = (TextView) findViewById(R.id.tvInput);
    }

    public void onSaveClick(View view) {
        BufferedWriter bw = null;
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(etInput.getText().toString());
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                Log.e(TAG, e.toString());
            }
        }
        showToast(R.string.fileSaved);
    }

    public void onAppendClick(View view) {
        BufferedWriter bw = null;
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_APPEND);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(etInput.getText().toString());
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                Log.e(TAG, e.toString());
            }
        }
        showToast(R.string.wordsAppended);
    }

    public void onOpenClick(View view) {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            br = new BufferedReader(new InputStreamReader(fis));
            String text;
            while ((text = br.readLine()) != null) {
                sb.append(text);
                sb.append("\n");
            }
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                Log.e(TAG, e.toString());
            }
        }
        tvInput.setText(sb);
    }

    public void onClearClick(View view) {
        etInput.setText(null);
        tvInput.setText(null);
    }

    private void showToast(int messageResId) {
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
}