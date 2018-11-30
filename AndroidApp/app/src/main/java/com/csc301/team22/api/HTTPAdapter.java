package com.csc301.team22.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HTTPAdapter implements IHTTPAdapter {
    // We want a mock database inside this class
    public List<User> users = new ArrayList<>();
    List<Request> requests = new ArrayList<>();
    List<Response> respones = new ArrayList<>();
    HashMap<String, String> passwords = new HashMap<>();

    public int id_counter = 0;

    // Singleton
    private static MockHTTPAdapter instance = null;

    public static MockHTTPAdapter getInstance() {
        if(instance == null) {
            instance = new MockHTTPAdapter();
        }

        return instance;
    }


    @Override
    public User getProfile(int user_id) {
        for(User user: users) {
            if (user.getUser_id() == user_id) {
                return user;
            }
        }

        return null;
    }

    @Override
    public User createUser(CreateUser user) {


        User newUser = new User.Builder().user_id(id_counter).first_name(user.getFirst_name())
                .last_name(user.getLast_name()).email(user.getEmail()).build();

        id_counter++;

        users.add(newUser);
        passwords.put(user.getEmail(), user.getPassword());

        return newUser;
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
    public int authenticate(String email, String password) {
        if (passwords.containsKey(email)) {
            if (passwords.get(email).equals(password)) {
                for(User user: users) {
                    if (user.getEmail().equals(email)) {
                        return user.getUser_id();
                    }
                }
            }
        }

        return -1;
    }
}

