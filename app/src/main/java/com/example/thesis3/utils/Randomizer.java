package com.example.thesis3.utils;

import com.example.thesis3.model.TestItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Randomizer {


    public static List<TestItem> getRandomTrials(List<TestItem> allTrials, int count) {
        List<TestItem> shuffled = new ArrayList<>(allTrials);
        Collections.shuffle(shuffled);
        return shuffled.subList(0, Math.min(count, shuffled.size()));
    }


    public static List<String> getRandomFabPositions() {
        List<String> positions = new ArrayList<>(Arrays.asList("LEFT", "CENTER", "RIGHT"));
        Collections.shuffle(positions);
        return positions;
    }


    public static List<TestItem> getRandomMainTasks(List<TestItem> allTasks) {
        List<TestItem> shuffled = new ArrayList<>(allTasks);
        Collections.shuffle(shuffled);
        return shuffled;
    }


    public static int fabPositionToGravity(String position) {
        switch (position) {
            case "LEFT":
                return android.view.Gravity.BOTTOM | android.view.Gravity.START;
            case "CENTER":
                return android.view.Gravity.BOTTOM | android.view.Gravity.CENTER_HORIZONTAL;
            case "RIGHT":
                return android.view.Gravity.BOTTOM | android.view.Gravity.END;
            default:
                return android.view.Gravity.BOTTOM | android.view.Gravity.END; // default
        }
    }
}
