package idv.ron.activitiesdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText etProgramming, etDataStructure, etAlgorithm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        findViews();
    }

    private void findViews() {
        etProgramming = (EditText) findViewById(R.id.etProgramming);
        etDataStructure = (EditText) findViewById(R.id.etDataStructure);
        etAlgorithm = (EditText) findViewById(R.id.etAlgorithm);
    }

    private boolean isValid(EditText editText) {
        String pattern = "1[0]{2}|[0-9]{1,2}";
        String text = editText.getText().toString();
        if (!text.matches(pattern)) {
            editText.setError("0 ~ 100");
            return false;
        } else {
            return true;
        }
    }

    public void onSubmitClick(View view) {
        // use "&" not "&&" because even the first isValid() return false,
        // the second isValid() will still be called
        boolean isValid =
                isValid(etProgramming) & isValid(etDataStructure) & isValid(etAlgorithm);
        if (!isValid) {
            return;
        }
        int programming = Integer.parseInt(etProgramming.getText().toString());
        int dataStructure = Integer.parseInt(etDataStructure.getText().toString());
        int algorithm = Integer.parseInt(etAlgorithm.getText().toString());
        Intent intent = new Intent(this, ResultActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("programming", programming);
        bundle.putInt("dataStructure", dataStructure);
        bundle.putInt("algorithm", algorithm);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
