package idv.ron.activitiesdemo_object;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);
        tvResult = (TextView) findViewById(R.id.tvResult);
        showResults();
    }

    private void showResults() {
        Bundle bundle = getIntent().getExtras();
        Object score = bundle.getSerializable("score");
        if (score != null) {
            tvResult.setText(score.toString());
        }
    }

    public void onBackClick(View view) {
        finish();
    }
}
