package com.csc301.team22.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.csc301.team22.api.QueryBuilder;
import com.csc301.team22.api.http.HTTPAdapter;
import com.csc301.team22.api.http.HttpException;
import com.csc301.team22.views.RequestCardObservable;
import com.csc301.team22.views.RequestCardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;


public class RequestListFragment extends Fragment implements Observer {
    final HTTPAdapter http = HTTPAdapter.getInstance();
    private final List<RequestCardObservable> observableList = new ArrayList<>();
    private JobRequest selectedJob = null;

    public JobRequest getSelectedJob() {
        return selectedJob;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        assert activity != null;
        Bundle passedBundle = activity.getIntent().getExtras();

        long fromDateTime = 0;
        if (passedBundle != null) {
            fromDateTime = passedBundle.getLong("from_datetime");
        }

        QueryBuilder queryBuilder = new QueryBuilder();
        if (fromDateTime != 0) {
            queryBuilder
                    .add("starts_after", fromDateTime)
                    .add("finished", false)
                    .add("assigned", false);
        }
        LinearLayout linearLayoutRequestList = activity.findViewById(R.id.linearLayoutRequestList);
        addToLinearLayout(activity, linearLayoutRequestList, queryBuilder.build());
    }

    public void addToLinearLayout(Context context, LinearLayout layout, Map<String, String> queries) {
        try {
            List<JobRequest> requests = http.getRequests(queries);
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
    onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
            selectedJob = observable.getView().getJob();
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
