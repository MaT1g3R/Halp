package com.csc301.team22;

import android.content.Context;

import com.csc301.team22.views.RequestCardView;

public class Request {
    private String name;
    private String description;

    public Request(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RequestCardView toCardView(Context context) {
        return new RequestCardView(context, name, description);
    }
}
