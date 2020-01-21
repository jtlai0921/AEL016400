package idv.ron.masterdetaildemo;

import java.util.ArrayList;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MasterFragment extends ListFragment {
    private boolean isDualPane;
    private int position;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<String> parkNames = new ArrayList<>();
        for (MyData.Park park : MyData.PARKS) {
            parkNames.add(park.getName());
        }

        View detailFrame = getActivity().findViewById(R.id.detail);
        isDualPane = detailFrame != null
                && detailFrame.getVisibility() == View.VISIBLE;

        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("position");
        }

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        if (isDualPane) {
            // simple_list_item_activated_1: change background color while selected
            setListAdapter(new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_list_item_activated_1, parkNames));
            getListView().setItemChecked(position, true);
            showDetail();
        } else {
            // simple_list_item_1: not necessary to change background color while selected in single-pane style
            setListAdapter(new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_list_item_1, parkNames));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", position);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        this.position = position;
        showDetail();
    }

    void showDetail() {
        if (isDualPane) {
            DetailFragment detailFragment = (DetailFragment) getFragmentManager()
                    .findFragmentById(R.id.detail);
            if (detailFragment == null || detailFragment.getIndex() != position) {
                detailFragment = new DetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                detailFragment.setArguments(bundle);
                FragmentTransaction ft = getFragmentManager()
                        .beginTransaction();
                ft.replace(R.id.detail, detailFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        } else {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}