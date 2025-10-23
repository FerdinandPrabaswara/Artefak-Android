package com.example.thesis3.utils;

import java.util.ArrayList;
import java.util.List;

public class MistakeLogger {
    private int mistakes = 0;
    private final List<String> mistakeDetails = new ArrayList<>();

    public void addMistake() {
        mistakes++;
    }

    public void addMistake(String type) {
        mistakes++;
        mistakeDetails.add(type);
    }

    public int getMistakes() {
        return mistakes;
    }

    public List<String> getMistakeDetails() {
        return mistakeDetails;
    }

    public void reset() {
        mistakes = 0;
        mistakeDetails.clear();
    }
}
