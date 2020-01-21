package idv.ron.alertdialogdemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    public static class AlertDialogFragment
            extends DialogFragment implements DialogInterface.OnClickListener {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.title)
                    .setIcon(R.drawable.alert)
                    .setMessage(R.string.msg_Alert)
                    .setPositiveButton(R.string.text_btYes, this)
                    .setNegativeButton(R.string.text_btNo, this)
                    .create();
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    getActivity().finish();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    dialog.cancel();
                    break;
                default:
                    break;
            }
        }
    }

    public void onExitClick(View view) {
        AlertDialogFragment alertFragment = new AlertDialogFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        alertFragment.show(fragmentManager, "alert");
    }
}
