package com.example.thesis3.model;

public class TestItem {
    private int id;
    private String question;
    private boolean requiresFab;
    private String expectedAction;

    public TestItem(int id, String question, boolean requiresFab, String expectedAction) {
        this.id = id;
        this.question = question;
        this.requiresFab = requiresFab;
        this.expectedAction = expectedAction;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public boolean isRequiresFab() {
        return requiresFab;
    }
    public String getExpectedAction() {
        return expectedAction;
    }

}

