package idv.ron.draw2ddemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private GeometricView geometricView;
    private int offset = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        geometricView = (GeometricView) findViewById(R.id.geometricView);
    }

    public void onMoveRightClick(View view) {
        if (geometricView != null) {
            offset += 10;
            geometricView.setOffset(offset);
            geometricView.invalidate();
        }
    }
}
