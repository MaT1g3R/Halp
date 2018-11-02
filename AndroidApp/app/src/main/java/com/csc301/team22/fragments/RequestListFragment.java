package com.csc301.team22.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.csc301.team22.R;
import com.csc301.team22.RequestManager;

public class RequestListFragment extends Fragment {
    private LinearLayout linearLayoutRequestList;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        assert activity != null;

        linearLayoutRequestList = activity.findViewById(R.id.linearLayoutRequestList);
        RequestManager.getInstance().addToLinearLayout(activity, linearLayoutRequestList);
    }

    @Override
    public View
    onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_request_list, container, false);
    }
}
