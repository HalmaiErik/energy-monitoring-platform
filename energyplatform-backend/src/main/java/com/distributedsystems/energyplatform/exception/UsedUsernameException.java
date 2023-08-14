package com.distributedsystems.energyplatform.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UsedUsernameException extends RuntimeException {

    private final String message;

    @Override
    public String getMessage() {
        return message;
    }
}
