package com.example.thesis3.utils;

public class TimeLogger {
    private long startTime;

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public long stop() {
        return System.currentTimeMillis() - startTime;
    }
}
