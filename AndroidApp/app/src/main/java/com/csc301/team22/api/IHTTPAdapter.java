package com.csc301.team22.api;

import java.util.List;

// This class is aware of the authentication context, therefore it can get the
// currently authenticated user
public interface IHTTPAdapter {
    String email = null, password = null;
    Integer user_id = null;

    public User getProfile(int user_id);

    public User createUser(CreateUser user);

    public String updateBio(String bio);

    public List<Request> getRequests(RequestQuery query);

    public Request createRequest(CreateRequest createrequest);

    public void deleteRequest(int requestId);

    public User findWorker(int userId);

    public Request findJob(Integer duration, Integer radius, Double latitude, Double longitude);

    public int authenticate(String email, String password);
}
