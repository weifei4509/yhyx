package com.cheapest.lansu.cheapestshopping.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.utils.TextUtil;
import com.cheapest.lansu.cheapestshopping.view.activity.SearchActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 搜券
 * A simple {@link Fragment} subclass.
 */
public class SearchStampsFragment extends Fragment {


    @Bind(R.id.btn_search)
    TextView btnSearch;
    @Bind(R.id.tv_title_1)
    TextView tvTitle1;
    @Bind(R.id.tv_title_2)
    TextView tvTitle2;
    @Bind(R.id.tv_title_3)
    TextView tvTitle3;
    @Bind(R.id.et_input)
    EditText etInput;

    public SearchStampsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_stamps, container, false);
        ButterKnife.bind(this, view);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra(Constants.INTENT_KEY_TITLE,etInput.getText().toString() );

                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextUtil.setTextType(getActivity(), tvTitle1);
        TextUtil.setTextType(getActivity(), tvTitle2);
        TextUtil.setTextType(getActivity(), tvTitle3);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
