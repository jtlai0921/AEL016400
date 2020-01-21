package idv.ron.fragmentintrodemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "fragment";
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        fragmentManager = getSupportFragmentManager();
    }

    public void onAddClick(View view) {
        Fragment fragment = fragmentManager.findFragmentById(R.id.frameLayout);
        if (fragment == null) {
            String title = "Fragment A";
            MyFragment fragmentA = new MyFragment();
            Bundle bundle = new Bundle();
            bundle.putString("title", title);
            fragmentA.setArguments(bundle);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.frameLayout, fragmentA, TAG);
            transaction.commit();
        } else {
            showToast("fragment exists");
        }
    }

    public void onReplaceClick(View view) {
        String title = "Fragment B";
        MyFragment fragmentB = new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        fragmentB.setArguments(bundle);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragmentB, TAG);
        transaction.commit();
    }

    public void onAttachClick(View view) {
        Fragment fragment = fragmentManager.findFragmentById(R.id.frameLayout);
        if (fragment == null) {
            showToast("fragment doesn't exist");
        } else {
            if (fragment.isDetached()) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.attach(fragment);
                transaction.commit();
            } else {
                showToast("fragment attached");
            }
        }
    }

    public void onDetachClick(View view) {
        Fragment fragment = fragmentManager.findFragmentByTag(TAG);
        if (fragment == null) {
            showToast("fragment doesn't exists");
        } else {
            if (!fragment.isDetached()) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.detach(fragment);
                transaction.commit();
            } else {
                showToast("fragment detached");
            }
        }
    }

    public void onRemoveClick(View view) {
        Fragment fragment = fragmentManager.findFragmentByTag(TAG);
        if (fragment != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
        } else {
            showToast("fragment doesn't exists");
        }
    }

    public void onFinishClick(View view) {
        finish();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
