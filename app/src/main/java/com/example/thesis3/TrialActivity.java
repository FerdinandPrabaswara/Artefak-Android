package com.example.thesis3;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.thesis3.data.TrialData;
import com.example.thesis3.model.FabConfig;
import com.example.thesis3.model.TestItem;
import com.example.thesis3.utils.Randomizer;
import com.example.thesis3.utils.TimeLogger;

import java.util.List;

public class TrialActivity extends BaseFeedActivity {

    private List<TestItem> trialTasks;
    private int currentTaskIndex = 0;
    private TestItem currentTask;
    private TimeLogger timeLogger;
    private String respondentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timeLogger = new TimeLogger();

        // Ambil respondent ID dari intent
        respondentId = getIntent().getStringExtra("respondent_id");

        // Ambil satu task secara acak dari TrialData
        trialTasks = Randomizer.getRandomTrials(TrialData.getTrialTasks(), 1);
        currentTaskIndex = 0;

        showWelcomeDialog();
    }

    private void showWelcomeDialog() {
        String message = "Respondent ID: " + respondentId + "\n\n" +
                "Sebelum memulai sesi utama, Anda akan melakukan sesi trial terlebih dahulu.";

        new AlertDialog.Builder(this)
                .setTitle("Welcome to the Trial Session")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Next", (dialog, which) -> showNextTrialTask())
                .show();
    }

    private void showNextTrialTask() {
        if (currentTaskIndex >= trialTasks.size()) {
            showTrialCompleted();
            return;
        }

        currentTask = trialTasks.get(currentTaskIndex);

        // Tentukan ikon FAB yang sesuai dengan task
        int fabIcon = MainExperimentActivity.getFabIconForTask(currentTask);
        updateFabConfig("right", fabIcon); // Default posisi FAB kanan bawah

        new AlertDialog.Builder(this)
                .setTitle("Trial Task Instruction")
                .setMessage(currentTask.getQuestion())
                .setCancelable(false)
                .setPositiveButton("Start", (dialog, which) -> {
                    mistakeLogger.reset();
                    timeLogger.start();
                })
                .show();
    }

    @Override
    protected void onFabClicked() {
        handleTrialAction("FAB");
    }

    @Override
    protected void onPostComment(com.example.thesis3.model.Post post) {
        handleTrialAction("Comment Icon");
    }

    @Override
    protected void onPostArchive(com.example.thesis3.model.Post post) {
        handleTrialAction("Archive Icon");
    }

    @Override
    protected void onPostReport(com.example.thesis3.model.Post post) {
        handleTrialAction("Report Icon");
    }

    private void handleTrialAction(String action) {
        boolean success = currentTask.getExpectedAction().equalsIgnoreCase(action);

        if (success) {
            Toast.makeText(this, "Trial Task Completed!", Toast.LENGTH_SHORT).show();
            currentTaskIndex++;
            showNextTrialTask();
        } else {
            onMistakeDetected(action);
        }
    }

    @Override
    protected FabConfig getFabConfig() {
        // Default FAB config
        return new FabConfig(R.drawable.ic_add, Gravity.BOTTOM | Gravity.END, 16);
    }

    private void updateFabConfig(String position, Integer iconResId) {
        fab.hide();
        if (iconResId != null) fab.setImageResource(iconResId);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        params.gravity = Randomizer.fabPositionToGravity(position);
        int margin = (int) (16 * getResources().getDisplayMetrics().density);
        params.setMargins(margin, margin, margin, margin);
        fab.setLayoutParams(params);

        fab.show();
    }

    @Override
    protected void onMistakeDetected(String source) {
        Toast.makeText(this, "Try again!", Toast.LENGTH_SHORT).show();
    }

    private void showTrialCompleted() {
        new AlertDialog.Builder(this)
                .setTitle("Trial Completed!")
                .setMessage("You have completed the trial session.\nNext, you will begin the main experiment.")
                .setCancelable(false)
                .setPositiveButton("Continue", (dialog, which) -> {
                    Intent intent = new Intent(this, MainExperimentActivity.class);
                    intent.putExtra("respondent_id", respondentId);
                    startActivity(intent);
                    finish();
                })
                .show();
    }
}
