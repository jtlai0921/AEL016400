package idv.ron.masterdetaildemo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailFragment extends Fragment {
    private int position;

    public int getIndex() {
        return position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (container == null) {
            return null;
        }
        Bundle bundle = this.getArguments();
        position = bundle.getInt("position");
        MyData.Park park = MyData.PARKS[position];
        View view = inflater.inflate(R.layout.detail_fragment, container, false);
        TextView tvHeadline = (TextView) view.findViewById(R.id.tvHeadline);
        tvHeadline.setText(park.getName());
        ImageView ivImage = (ImageView) view.findViewById(R.id.ivImage);
        ivImage.setImageResource(park.getImageId());
        TextView tvDescription = (TextView) view.findViewById(R.id.tvDescription);
        tvDescription.setText(park.getDescription());
        return view;
    }
}
