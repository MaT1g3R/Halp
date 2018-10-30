package com.csc301.team22;

import java.util.Arrays;

public class RequestManager {
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
}
