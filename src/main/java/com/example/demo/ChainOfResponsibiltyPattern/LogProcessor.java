package com.example.demo.ChainOfResponsibiltyPattern;

public abstract class LogProcessor {
    public static int INFO = 1;
    public static int DEBUG = 2;
    public static int ERROR = 3;
    LogProcessor nextLogProcessor;

    LogProcessor(LogProcessor loggerProcessor) {
        this.nextLogProcessor = loggerProcessor;
    }

    public void log(int logLevel, String message) {
        if (this.nextLogProcessor != null) {
            this.nextLogProcessor.log(logLevel, message);
        }
    }
}
