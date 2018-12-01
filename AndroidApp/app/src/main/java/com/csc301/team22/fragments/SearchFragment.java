package com.csc301.team22.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.csc301.team22.EButtonState;
import com.csc301.team22.R;
//import com.csc301.team22.Request;
import com.csc301.team22.RequestManager;
import com.csc301.team22.Util;
import com.csc301.team22.activities.JobConfirmationActivity;
import com.csc301.team22.activities.JobDescriptionActivity;
import com.csc301.team22.api.MockHTTPAdapter;
import com.csc301.team22.api.Request;
import com.csc301.team22.views.RequestCardObservable;
import com.csc301.team22.views.RequestCardView;
import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static android.content.Context.MODE_PRIVATE;

public class SearchFragment extends Fragment implements Observer {
    private LinearLayout linearLayoutRequestList;
    private List<RequestCardObservable> observableList = new ArrayList<>();
    private AppCompatActivity activity;
    MockHTTPAdapter mock = MockHTTPAdapter.getInstance();

    double radius;
    int distance;
    String lati, longi;
    double dlati, dlongi;

    LatLng L1, L2;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (AppCompatActivity) getActivity();
        assert activity != null;

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loc", MODE_PRIVATE);
        SharedPreferences sP = getActivity().getSharedPreferences("distance", MODE_PRIVATE);

        lati = sharedPreferences.getString("lati", "");
        longi = sharedPreferences.getString("longi", "");
        distance = sP.getInt("dist", 0);

        dlati = (double) Double.parseDouble(lati);
        dlongi = (double) Double.parseDouble(longi);

        L1 = new LatLng(dlati, dlongi);

        linearLayoutRequestList = activity.findViewById(R.id.linearLayoutRequestList);
        addToLinearLayout(activity, linearLayoutRequestList);
    }

    public void addToLinearLayout(Context context, LinearLayout layout) {
//        for (Request request : RequestManager.getInstance().getRequests()) {
        for (Request request: mock.requests) {
            L2 = new LatLng(request.getLatitude(), request.getLongitude());
            radius = CalculationByDistance(L1, L2);
            if (radius <= distance) {
                RequestCardView cardView = toCardView(context, request.getTitle(), request.getDescription());
                LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                layout.addView(cardView.getLayout(), lp);
                cardView.getObservable().addObserver(this);
                observableList.add(cardView.getObservable());
            }
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
        } else {
            Util.openActivity(activity, JobConfirmationActivity.class);
        }
    }

    public RequestCardView toCardView(Context context,String name, String description) {
        return new RequestCardView(context, name, description);
    }

    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }
}