package idv.ron.assetdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        TextView tvAsset = (TextView) findViewById(R.id.tvAsset);
        BufferedReader br = null;
        try {
            InputStream is = getAssets().open("android_intro.txt");
            br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null) {
                sb.append(text);
                sb.append("\n");
            }
            tvAsset.setText(sb);
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
    }
}
