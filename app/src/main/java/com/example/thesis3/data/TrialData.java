package com.example.thesis3.data;

import com.example.thesis3.model.TestItem;
import java.util.ArrayList;
import java.util.List;

public class TrialData {

    public static List<TestItem> getTrialTasks() {
        List<TestItem> trials = new ArrayList<>();
        trials.add(new TestItem(1, "Unggah video baru", true, "FAB"));
        trials.add(new TestItem(2, "Arsipkan salah satu postingan", false, "Archive Icon"));
        trials.add(new TestItem(3, "Berikan komentar pada salah satu postingan", false, "Comment Icon"));
        return trials;
    }
}

