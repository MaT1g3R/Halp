package com.csc301.team22.api;

public class User {
    private int user_id;
    private String first_name, last_name, email, bio="";

    private User(Builder builder) {
        this.user_id = builder.user_id;
        this.first_name = builder.first_name;
        this.last_name = builder.last_name;
        this.email = builder.email;
        this.bio = builder.bio;
    }

    // Public constructor
    public User() {
    }

    public int getUser_id() {
        return user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String newbio) {
        this.bio = newbio;
    }

    public static class Builder {
        private Integer user_id = null;
        private String first_name = null, last_name = null, email = null, bio = null;

        public Builder user_id(int user_id) {
            this.user_id = user_id;
            return this;
        }

        public Builder first_name(String first_name) {
            this.first_name = first_name;
            return this;
        }

        public Builder last_name(String last_name) {
            this.last_name = last_name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder bio(String bio) {
            this.bio = bio;
            return this;
        }

        public User build() {
            if (user_id == null || first_name == null || last_name == null || email == null) {
                throw new IllegalArgumentException("First and last name cannot be null");
            }

            return new User(this);
        }
    }
}
