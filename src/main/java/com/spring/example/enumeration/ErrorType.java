package com.spring.example.enumeration;

public enum ErrorType {
    USER_NOT_FOUND("User with username %s was not found"),
    ROLE_NOT_FOUND("Role with roleName %s was not found"),
    USER_ALREADY_EXISTS("User with username %s already exists"),
    PRODUCT_NOT_FOUND("Product with id %s was not found");

    public final String message;

    ErrorType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
