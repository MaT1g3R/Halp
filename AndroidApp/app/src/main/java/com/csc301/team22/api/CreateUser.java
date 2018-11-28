package com.csc301.team22.api;

public class CreateUser {
    private String first_name="", last_name="", email="", password="";

    public class Builder {
        private String first_name=null, last_name=null, email=null, password=null;

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public CreateUser build () {

            if (first_name == null || last_name == null
                    || email == null || password == null) {

                throw new IllegalArgumentException("All CreateUser attributes must not be null");
            }

            CreateUser user = new CreateUser();

            user.first_name = first_name;
            user.last_name = last_name;
            user.email = email;
            user.password = password;

            return user;
        }

        public Builder (){}
    }



    CreateUser(){}
}
