package com.csc301.team22.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MockHTTPAdapter implements IHTTPAdapter {
    // We want a mock database inside this class
    List<User> users = new ArrayList<>();
    List<Request> requests = new ArrayList<>();
    List<Response> respones = new ArrayList<>();
    HashMap<String, String> passwords = new HashMap<>();


    @Override
    public User getProfile(int user_id) {
        return null;
    }

    @Override
    public User createUSer(CreateUser user) {
        return null;
    }

    @Override
    public String updateBio(String bio) {
        return null;
    }

    @Override
    public List<Request> getRequests(RequestQuery query) {
        return null;
    }

    @Override
    public Request createRequest(CreateRequest createrequest) {
        return null;
    }

    @Override
    public void deleteRequest(int requestId) {

    }

    @Override
    public User findWorker(int userId) {
        return null;
    }

    @Override
    public Request findJob(Integer duration, Integer radius, Double latitude, Double longitude) {
        return null;
    }

    @Override
    public void authenticate(String email, String password) {

    }
}
