package com.example.authservice.exception;

public class PasswordSameAsOldException extends RuntimeException {
    public PasswordSameAsOldException() {
        super("New password cannot be the same as the old password.");
    }
}
