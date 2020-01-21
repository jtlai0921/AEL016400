package idv.ron.datetimepickerdemo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private TextView tvDateTime;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        tvDateTime = (TextView) findViewById(R.id.tvDateTime);
        showNow();
    }

    private void showNow() {
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);
        updateDisplay();
    }

    private void updateDisplay() {
        tvDateTime.setText(new StringBuilder().append(mYear).append("-")
                .append(pad(mMonth + 1)).append("-").append(pad(mDay))
                .append(" ").append(pad(mHour)).append(":")
                .append(pad(mMinute)));
    }

    private String pad(int number) {
        if (number >= 10)
            return String.valueOf(number);
        else
            return "0" + String.valueOf(number);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        mYear = year;
        mMonth = month;
        mDay = day;
        updateDisplay();
    }

    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {
        mHour = hour;
        mMinute = minute;
        updateDisplay();
    }

    public static class DatePickerDialogFragment extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            MainActivity activity = (MainActivity) getActivity();
            return new DatePickerDialog(
                    activity, activity, activity.mYear, activity.mMonth, activity.mDay);
        }
    }

    public static class TimePickerDialogFragment extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            MainActivity activity = (MainActivity) getActivity();
            return new TimePickerDialog(
                    activity, activity, activity.mHour, activity.mMinute, false);
        }
    }

    public void onDateClick(View view) {
        DatePickerDialogFragment datePickerFragment = new DatePickerDialogFragment();
        FragmentManager fm = getSupportFragmentManager();
        datePickerFragment.show(fm, "datePicker");
    }

    public void onTimeClick(View view) {
        TimePickerDialogFragment timePickerFragment = new TimePickerDialogFragment();
        FragmentManager fm = getSupportFragmentManager();
        timePickerFragment.show(fm, "timePicker");
    }
}
