package com.csc301.team22;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.csc301.team22.api.*;
import com.csc301.team22.views.RequestCardView;

import java.util.Arrays;
import java.util.Date;

public class RequestManager {

    MockHTTPAdapter mock = MockHTTPAdapter.getInstance();
    private static final RequestManager ourInstance = new RequestManager();

    private RequestManager() {
    }

    public static RequestManager getInstance() {
        return ourInstance;
    }

    public Iterable<Request> getRequests() {
        return Arrays.asList(
                new Request("Mowing the Lawn", "I need help mowing my lawn and doing some minor landscaping. Should only take a few hours if both of us are at it......"),
                new Request("Helping Move Dishwasher", ""),
                new Request("3-Day Manual Labor", ""),
                new Request("Garbage Disposal", "")
        );


    }


    public void createRequest(String title, String description, Date date) {
        if (date == null) {
            System.out.println("Creating Request now: " + title + " - " + description);
        } else {
            System.out.println("Creating Request later: " + title + " - " + description + " at " + date);
        }
    }
}
