package com.example.thesis3.model;

public class ResultItem {
    private String respondentId;
    private String taskName;
    private String fabPosition;
    private long completionTimeMs;
    private int mistakes;

    public ResultItem(String respondentId, String taskName, String fabPosition,
                      long completionTimeMs, int mistakes) {
        this.respondentId = respondentId;
        this.taskName = taskName;
        this.fabPosition = fabPosition;
        this.completionTimeMs = completionTimeMs;
        this.mistakes = mistakes;
    }

    public String[] toCsvRow() {
        return new String[]{
                respondentId,
                taskName,
                fabPosition,
                String.valueOf(completionTimeMs),
                String.valueOf(mistakes)
        };
    }
}

