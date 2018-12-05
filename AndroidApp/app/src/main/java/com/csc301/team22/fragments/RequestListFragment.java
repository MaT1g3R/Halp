package com.csc301.team22.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.csc301.team22.EButtonState;
import com.csc301.team22.R;
import com.csc301.team22.Util;
import com.csc301.team22.api.JobRequest;
import com.csc301.team22.api.http.HTTPAdapter;
import com.csc301.team22.api.http.HttpException;
import com.csc301.team22.views.RequestCardObservable;
import com.csc301.team22.views.RequestCardView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class RequestListFragment extends Fragment implements Observer {
    HTTPAdapter http = HTTPAdapter.getInstance();
    private LinearLayout linearLayoutRequestList;
    private List<RequestCardObservable> observableList = new ArrayList<>();
    private AppCompatActivity activity;
    private List<JobRequest> requests = null;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (AppCompatActivity) getActivity();
        assert activity != null;

        linearLayoutRequestList = activity.findViewById(R.id.linearLayoutRequestList);
        addToLinearLayout(activity, linearLayoutRequestList);
    }

    public void addToLinearLayout(Context context, LinearLayout layout) {
        try {
            requests = http.getRequests(new HashMap<>());
            requests.forEach(request -> {
                RequestCardView cardView = toCardView(context, request);
                LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                layout.addView(cardView.getLayout(), lp);
                cardView.getObservable().addObserver(this);
                observableList.add(cardView.getObservable());
            });
        } catch (HttpException e) {
            Util.showError(context, "Failed to get requests", e.getMessage());
        }
    }

    @Override
    public View
    onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_request_list, container, false);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (!(o instanceof RequestCardObservable && arg instanceof EButtonState)) {
            return;
        }
        RequestCardObservable observable = (RequestCardObservable) o;
        EButtonState buttonState = (EButtonState) arg;
        if (buttonState == EButtonState.EXPANDED) {
            for (RequestCardObservable nextObservable : observableList) {
                if (!nextObservable.equals(observable)) {
                    nextObservable.getView().setCollapsed();
                }

            }
        }
    }

    public RequestCardView toCardView(Context context, JobRequest job) {
        return new RequestCardView(context, job);
    }
}
