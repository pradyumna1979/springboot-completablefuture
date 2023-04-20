package com.completablefuture.exception;

public class UserInfoNotFoundException extends Throwable {
    public UserInfoNotFoundException(String noUserFoundException) {
        super(noUserFoundException);
    }
}
