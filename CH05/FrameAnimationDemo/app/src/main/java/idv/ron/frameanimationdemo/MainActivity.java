package idv.ron.frameanimationdemo;

import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Drawable p01;
        Drawable p02;
        Drawable p03;
        Drawable p04;
        Drawable p05;
        Drawable p06;
        Drawable p07;
        Drawable p08;
        Drawable p09;
        Drawable p10;
        Drawable p11;
        Drawable p12;

        Resources res = getResources();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            p01 = res.getDrawable(R.drawable.p01, null);
            p02 = res.getDrawable(R.drawable.p02, null);
            p03 = res.getDrawable(R.drawable.p03, null);
            p04 = res.getDrawable(R.drawable.p04, null);
            p05 = res.getDrawable(R.drawable.p05, null);
            p06 = res.getDrawable(R.drawable.p06, null);
            p07 = res.getDrawable(R.drawable.p07, null);
            p08 = res.getDrawable(R.drawable.p08, null);
            p09 = res.getDrawable(R.drawable.p09, null);
            p10 = res.getDrawable(R.drawable.p10, null);
            p11 = res.getDrawable(R.drawable.p11, null);
            p12 = res.getDrawable(R.drawable.p12, null);
        } else {
            p01 = res.getDrawable(R.drawable.p01);
            p02 = res.getDrawable(R.drawable.p02);
            p03 = res.getDrawable(R.drawable.p03);
            p04 = res.getDrawable(R.drawable.p04);
            p05 = res.getDrawable(R.drawable.p05);
            p06 = res.getDrawable(R.drawable.p06);
            p07 = res.getDrawable(R.drawable.p07);
            p08 = res.getDrawable(R.drawable.p08);
            p09 = res.getDrawable(R.drawable.p09);
            p10 = res.getDrawable(R.drawable.p10);
            p11 = res.getDrawable(R.drawable.p11);
            p12 = res.getDrawable(R.drawable.p12);
        }

        animationDrawable = new AnimationDrawable();
        animationDrawable.setOneShot(false);
        int duration = 200;
        animationDrawable.addFrame(p01, duration);
        animationDrawable.addFrame(p02, duration);
        animationDrawable.addFrame(p03, duration);
        animationDrawable.addFrame(p04, duration);
        animationDrawable.addFrame(p05, duration);
        animationDrawable.addFrame(p06, duration);
        animationDrawable.addFrame(p07, duration);
        animationDrawable.addFrame(p08, duration);
        animationDrawable.addFrame(p09, duration);
        animationDrawable.addFrame(p10, duration);
        animationDrawable.addFrame(p11, duration);
        animationDrawable.addFrame(p12, duration);

        ImageView ivPicture = (ImageView) findViewById(R.id.ivPicture);
        ivPicture.setBackground(animationDrawable);
    }

    public void onPictureClick(View view) {
        if (!animationDrawable.isRunning()) {
            animationDrawable.start();
        } else {
            animationDrawable.stop();
        }
    }
}
