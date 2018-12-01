package com.csc301.team22.api;

public class CreateUser {
    private String first_name = "", last_name = "", email = "", password = "";

    private CreateUser(Builder builder) {
        this.first_name = builder.first_name;
        this.last_name = builder.last_name;
        this.email = builder.email;
        this.password = builder.password;
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

    public String getPassword() {
        return password;
    }

    public static class Builder {
        private String first_name = null, last_name = null, email = null, password = null;

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

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public CreateUser build() {

            if (first_name == null || last_name == null
                    || email == null || password == null) {

                throw new IllegalArgumentException("All CreateUser attributes must not be null");
            }

            return new CreateUser(this);
        }

    }

}
