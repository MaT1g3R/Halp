package com.csc301.team22.api;

public class User {
    private int user_id;
    private String first_name, last_name, bio;

    // Public constructor
    public User(){}

    private User(Builder builder) {
        this.first_name = builder.first_name;
        this.last_name = builder.last_name;
        this.bio = builder.bio;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getBio() {
        return bio;
    }

    public static class Builder {
        private String first_name = null, last_name = null, bio = null;

        public Builder first_name(String first_name) {
            this.first_name = first_name;
            return this;
        }

        public Builder last_name(String last_name) {
            this.last_name = last_name;
            return this;
        }

        public Builder bio(String bio) {
            this.bio = bio;
            return this;
        }

        public User build() {
            if (first_name == null || last_name == null) {
                throw new IllegalArgumentException("First and last name cannot be null");
            }

            return new User(this);
        }
    }
}
