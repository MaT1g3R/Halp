package com.csc301.team22.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.csc301.team22.R;
import com.csc301.team22.Util;
import com.csc301.team22.activities.ProfileActivity;

public class TopBarFragment extends Fragment {
    private Button buttonHome;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        buttonHome = activity.findViewById(R.id.buttonHome);
        buttonHome.setOnClickListener(v -> Util.openActivity(activity, ProfileActivity.class));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_topbar, container, false);
    }

}
