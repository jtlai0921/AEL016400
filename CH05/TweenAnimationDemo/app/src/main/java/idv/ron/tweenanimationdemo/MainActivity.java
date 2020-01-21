package idv.ron.tweenanimationdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tvTitle;
    private ImageView ivSoccer;
    private RadioButton rbTranslate, rbRotate, rbScale, rbAlpha;
    private Spinner spInterpolator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        findViews();
    }

    private void findViews() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        ivSoccer = (ImageView) findViewById(R.id.ivSoccer);
        rbTranslate = (RadioButton) findViewById(R.id.rbTranslate);
        rbRotate = (RadioButton) findViewById(R.id.rbRotate);
        rbScale = (RadioButton) findViewById(R.id.rbScale);
        rbAlpha = (RadioButton) findViewById(R.id.rbAlpha);
        spInterpolator = (Spinner) findViewById(R.id.spInterpolator);

        String[] interpolators = {
                "linear_interpolator",
                "accelerate",
                "decelerate",
                "accelerate_decelerate",
                "anticipate",
                "overshoot",
                "anticipate_overshoot",
                "bounce"
        };
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, interpolators);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spInterpolator.setAdapter(arrayAdapter);
        spInterpolator.setSelection(0, true);
        spInterpolator.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Interpolator interpolator;
                switch (item) {
                    case "linear_interpolator":
                        interpolator = new LinearInterpolator();
                        break;
                    case "accelerate":
                        interpolator = new AccelerateInterpolator();
                        break;
                    case "decelerate":
                        interpolator = new DecelerateInterpolator();
                        break;
                    case "accelerate_decelerate":
                        interpolator = new AccelerateDecelerateInterpolator();
                        break;
                    case "anticipate":
                        interpolator = new AnticipateInterpolator();
                        break;
                    case "overshoot":
                        interpolator = new OvershootInterpolator();
                        break;
                    case "anticipate_overshoot":
                        interpolator = new AnticipateOvershootInterpolator();
                        break;
                    case "bounce":
                        interpolator = new BounceInterpolator();
                        break;
                    default:
                        interpolator = new LinearInterpolator();
                        break;
                }

                Animation animation;
                if (rbTranslate.isChecked()) {
                    animation = getTranslateAnimation();
                } else if (rbRotate.isChecked()) {
                    animation = getRotateAnimation();
                } else if (rbScale.isChecked()) {
                    animation = getScaleAnimation();
                } else if (rbAlpha.isChecked()) {
                    animation = getAlphaAnimation();
                } else {
                    animation = getAnimationSet();
                }
                animation.setInterpolator(interpolator);
                ivSoccer.startAnimation(animation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onTranslateClick(View view) {
        ivSoccer.startAnimation(getTranslateAnimation());
        spInterpolator.setSelection(0, true);
        tvTitle.setText(R.string.text_TranslateAnimation);
        tvTitle.startAnimation(getShakeAnimation());
    }

    public void onRotateClick(View view) {
        ivSoccer.startAnimation(getRotateAnimation());
        spInterpolator.setSelection(0, true);
        tvTitle.setText(R.string.text_RotateAnimation);
        tvTitle.startAnimation(getShakeAnimation());
    }

    public void onScaleClick(View view) {
        ivSoccer.startAnimation(getScaleAnimation());
        spInterpolator.setSelection(0, true);
        tvTitle.setText(R.string.text_ScaleAnimation);
        tvTitle.startAnimation(getShakeAnimation());
    }

    public void onAlphaClick(View view) {
        ivSoccer.startAnimation(getAlphaAnimation());
        spInterpolator.setSelection(0, true);
        tvTitle.setText(R.string.text_AlphaAnimation);
        tvTitle.startAnimation(getShakeAnimation());
    }

    public void onAnimSetClick(View view) {
        ivSoccer.startAnimation(getAnimationSet());
        spInterpolator.setSelection(0, true);
        tvTitle.setText(R.string.text_AnimationSet);
        tvTitle.startAnimation(getShakeAnimation());
    }

    private TranslateAnimation getTranslateAnimation() {
        View parentView = (View) ivSoccer.getParent();
        float distance = parentView.getWidth() - parentView.getPaddingLeft() -
                parentView.getPaddingRight() - ivSoccer.getWidth();
        TranslateAnimation translateAnimation = new TranslateAnimation(0, distance, 0, 0);
        translateAnimation.setDuration(1000);
        translateAnimation.setRepeatMode(Animation.RESTART);
        translateAnimation.setRepeatCount(Animation.INFINITE);
        return translateAnimation;
    }

    private RotateAnimation getRotateAnimation() {
        RotateAnimation rotateAnimation = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotateAnimation.setDuration(300);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        return rotateAnimation;
    }

    private ScaleAnimation getScaleAnimation() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                0.1f, 2,
                0.1f, 2,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        scaleAnimation.setDuration(2000);
        scaleAnimation.setRepeatMode(Animation.RESTART);
        scaleAnimation.setRepeatCount(Animation.INFINITE);
        return scaleAnimation;
    }

    private AlphaAnimation getAlphaAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setRepeatMode(Animation.RESTART);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        return alphaAnimation;
    }

    private AnimationSet getAnimationSet() {
        AnimationSet animationSet = new AnimationSet(true);
        RotateAnimation rotateAnimation = getRotateAnimation();
        // RotateAnimation must be added before TranslateAnimation,
        // or the angle of the RotateAnimation should be wrong
        animationSet.addAnimation(rotateAnimation);
        TranslateAnimation translateAnimation = getTranslateAnimation();
        animationSet.addAnimation(translateAnimation);
        ScaleAnimation scaleAnimation = getScaleAnimation();
        animationSet.addAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation = getAlphaAnimation();
        animationSet.addAnimation(alphaAnimation);
        return animationSet;
    }

    private TranslateAnimation getShakeAnimation() {
        TranslateAnimation shakeAnimation = new TranslateAnimation(0, 10, 0, 0);
        shakeAnimation.setDuration(1000);
        CycleInterpolator cycleInterpolator = new CycleInterpolator(7);
        shakeAnimation.setInterpolator(cycleInterpolator);
        return shakeAnimation;
    }
}
