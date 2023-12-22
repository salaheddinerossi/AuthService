package com.example.authservice.exception;

public class OldPasswordNotMatchedException extends RuntimeException {
    public OldPasswordNotMatchedException() {
        super("Old password does not match.");
    }
}
