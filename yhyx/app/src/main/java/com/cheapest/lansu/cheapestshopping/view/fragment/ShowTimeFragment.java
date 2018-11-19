package com.cheapest.lansu.cheapestshopping.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheapest.lansu.cheapestshopping.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 晒晒界面
 * A simple {@link Fragment} subclass.
 */
public class ShowTimeFragment extends Fragment {


    @Bind(R.id.custom_view)
    LinearLayout customView;

    public ShowTimeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_time, container, false);
        ButterKnife.bind(this, view);

        View views = inflater.inflate(R.layout.item_fragmetn_show_time, null, false);
                customView.addView(views);
         views = inflater.inflate(R.layout.item_fragmetn_show_time, null, false);
        ((TextView)views.findViewById(R.id.tv_no)).setText("NO 2");

                customView.addView(views);
                views = inflater.inflate(R.layout.item_fragmetn_show_time, null, false);
        ((TextView)views.findViewById(R.id.tv_no)).setText("NO 3");

                customView.addView(views);
         views = inflater.inflate(R.layout.item_fragmetn_show_time, null, false);
        ((TextView)views.findViewById(R.id.tv_no)).setText("NO 4");
                customView.addView(views);





        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
