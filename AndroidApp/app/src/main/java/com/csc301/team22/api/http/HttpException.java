package com.csc301.team22.api.http;

public class HttpException extends Exception {
    private int status;

    public HttpException(int status, String message) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

}
