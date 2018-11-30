package com.csc301.team22.api;

public class CreateResponse {
    private int request_id = 0;
    private String comment = "";

    private CreateResponse(Builder builder) {
        this.request_id = builder.request_id;
        this.comment = builder.comment;
    }

    public int getRequest_id() {
        return request_id;
    }

    public String getComment() {
        return comment;
    }

    public static class Builder {
        private int request_id = 0;
        private String comment = null;

        public Builder request_id(int request_id) {
            this.request_id = request_id;
            return this;
        }

        public Builder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public CreateResponse build() {
            if (request_id == 0 || comment == null) {
                throw new IllegalArgumentException("Invalid request_id and/or comment required");
            }

            return new CreateResponse(this);
        }
    }

}
