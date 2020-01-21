package idv.ron.fragmentintrodemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyFragment extends Fragment {
    private final static String TAG = "MyFragment";
    private String title = "";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        title = getArguments().getString("title");
        Log.d(TAG, title + ": onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, title + ": onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.d(TAG, title + ": onCreateView");
        View view = inflater.inflate(R.layout.my_fragment, container, false);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(title);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, title + ": onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, title + ": onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, title + ": onResume");
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, title + ": onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, title + ": onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, title + ": onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, title + ": onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, title + ": onDetach");
    }
}
