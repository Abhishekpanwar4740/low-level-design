package com.example.practice.walletsystem.exception;

public class InvalidCurrencyPairException extends RuntimeException{
    public InvalidCurrencyPairException(String message) {
        super(message);
    }
}
