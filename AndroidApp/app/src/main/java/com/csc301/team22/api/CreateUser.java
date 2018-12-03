package com.csc301.team22.api;

import com.google.gson.Gson;

public class CreateUser {
    private String first_name = null;
    private String last_name = null;
    private String email = null;
    private String password = null;

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static class Builder {
        private CreateUser createUser = new CreateUser();

        public Builder firstName(String first_name) {
            createUser.first_name = first_name;
            return this;
        }

        public Builder lastName(String last_name) {
            createUser.last_name = last_name;
            return this;
        }

        public Builder email(String email) {
            createUser.email = email;
            return this;
        }

        public Builder password(String password) {
            createUser.password = password;
            return this;
        }

        public CreateUser build() {
            if (createUser.first_name == null || createUser.last_name == null
                    || createUser.email == null || createUser.password == null) {
                throw new IllegalArgumentException("All CreateUser attributes must not be null");
            }
            return createUser;
        }
    }
}
