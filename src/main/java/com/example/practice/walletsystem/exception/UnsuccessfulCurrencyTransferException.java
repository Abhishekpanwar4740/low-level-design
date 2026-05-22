package com.example.practice.walletsystem.exception;

public class UnsuccessfulCurrencyTransferException extends RuntimeException{
    public UnsuccessfulCurrencyTransferException(String message) {
        super(message);
    }
}
