package com.csc301.team22.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.csc301.team22.R;
import com.csc301.team22.Util;
import com.csc301.team22.activities.PostJobFindWorkActivity;
import com.csc301.team22.activities.ProfileActivity;

public class TopBarFragment extends Fragment {
    private Button buttonHome;
    private ImageButton buttonProfile;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        assert activity != null;

        buttonHome = activity.findViewById(R.id.buttonHome);
        buttonHome.setOnClickListener(v -> Util.openActivity(activity, PostJobFindWorkActivity.class));

        buttonProfile = activity.findViewById(R.id.buttonProfile);
        buttonProfile.setOnClickListener(v -> Util.openActivity(activity, ProfileActivity.class));
        buttonProfile.setBackgroundResource(R.drawable.default_profile_pic);
    }

    @Override
    public View
    onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_topbar, container, false);
    }

}
