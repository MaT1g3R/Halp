package com.csc301.team22.views;

import java.util.Observable;

public class RequestCardObservable extends Observable {
    private RequestCardView view;

    RequestCardObservable(RequestCardView view) {
        this.view = view;
    }

    public RequestCardView getView() {
        return view;
    }

    @Override
    public void notifyObservers(Object arg) {
        setChanged();
        super.notifyObservers(arg);
    }
}
