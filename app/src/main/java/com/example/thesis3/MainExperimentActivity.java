package com.example.thesis3;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.thesis3.data.ExperimentData;
import com.example.thesis3.model.FabConfig;
import com.example.thesis3.model.Post;
import com.example.thesis3.model.TestItem;
import com.example.thesis3.utils.CsvExporter;
import com.example.thesis3.utils.Randomizer;
import com.example.thesis3.utils.TimeLogger;

import java.util.List;

public class MainExperimentActivity extends BaseFeedActivity {

    private String respondentId;
    private List<String> fabPositions;
    private int currentFabBatchIndex = 0;

    private List<TestItem> currentTaskBatch;
    private int currentTaskIndex = 0;

    private TestItem currentTask;
    private TimeLogger timeLogger;

    // UI header element
    private ImageView ivProfile;
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        respondentId = getIntent().getStringExtra("respondent_id");
        if (respondentId == null) respondentId = "R-UNKNOWN";

        fabPositions = Randomizer.getRandomFabPositions();
        timeLogger = new TimeLogger();

        // Bind header UI (ikon profil & search bar)
        ivProfile = findViewById(R.id.ivProfile);
        etSearch = findViewById(R.id.etSearch);

        setupHeaderListeners();
        startNextFabBatch();
    }

    private void setupHeaderListeners() {
        if (ivProfile != null) {
            ivProfile.setOnClickListener(v -> handleTaskAction("Profile Icon"));
        }

        if (etSearch != null) {
            etSearch.setOnClickListener(v -> handleTaskAction("Search Bar"));
        }
    }

    private void startNextFabBatch() {
        if (currentFabBatchIndex >= fabPositions.size()) {
            showExperimentCompleted();
            return;
        }

        String fabPosition = fabPositions.get(currentFabBatchIndex);

        // ambil urutan acak untuk batch sekarang
        currentTaskBatch = Randomizer.getRandomMainTasks(ExperimentData.getExperimentTasks());
        currentTaskIndex = 0;

        updateFabConfig(fabPosition, null);
        showTaskInstruction();
    }

    private void showTaskInstruction() {
        if (currentTaskIndex >= currentTaskBatch.size()) {
            currentFabBatchIndex++;
            startNextFabBatch();
            return;
        }

        currentTask = currentTaskBatch.get(currentTaskIndex);

        // pilih ikon FAB sesuai task
        int fabIcon = getFabIconForTask(currentTask);
        updateFabConfig(fabPositions.get(currentFabBatchIndex), fabIcon);

        new AlertDialog.Builder(this)
                .setTitle("Task Instruction")
                .setMessage(currentTask.getQuestion())
                .setCancelable(false)
                .setPositiveButton("Start", (dialog, which) -> {
                    mistakeLogger.reset();
                    timeLogger.start();
                })
                .show();
    }

    public static int getFabIconForTask(TestItem task) {
        String q = task.getQuestion().toLowerCase();

        if (q.contains("buat") || q.contains("unggah") || q.contains("tambah")) {
            return R.drawable.ic_add;
        } else if (q.contains("scan qr")) {
            return R.drawable.ic_qr;
        } else if (q.contains("pesan")) {
            return R.drawable.ic_message; // ganti icon
        } else if (q.contains("live")) {
            return R.drawable.ic_live; // ganti icon
        } else if (q.contains("ai")) {
            return R.drawable.ic_ai;
        } else if (q.contains("bagikan")) {
            return R.drawable.ic_share;
        } else {
            return R.drawable.ic_add; // default
        }
    }

    @Override
    protected FabConfig getFabConfig() {
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
    protected void onFabClicked() {
        handleTaskAction("FAB");
    }

    @Override
    protected void onPostComment(Post post) {
        handleTaskAction("Comment Icon");
    }

    @Override
    protected void onPostArchive(Post post) {
        handleTaskAction("Archive Icon");
    }

    @Override
    protected void onPostReport(Post post) {
        handleTaskAction("Report Icon");
    }

    @Override
    protected void onMistakeDetected(String source) {
        handleMistake(source);
    }

    private void handleTaskAction(String action) {
        String expected = currentTask.getExpectedAction();

        if (expected.equalsIgnoreCase(action)) {
            handleSuccess();
        } else {
            handleMistake(action);
        }
    }

    private void handleSuccess() {
        long tct = timeLogger.stop();
        saveResult(currentTask.getQuestion(), tct, mistakeLogger.getMistakes());
        Toast.makeText(this, "Task Completed!", Toast.LENGTH_SHORT).show();
        currentTaskIndex++;
        showTaskInstruction();
    }

    private void handleMistake(String source) {
        Toast.makeText(this, "Try Again!", Toast.LENGTH_SHORT).show();
        mistakeLogger.addMistake(source);
    }

    private void saveResult(String taskName, long tctMillis, int mistakes) {
        CsvExporter.writeRow(
                this,
                respondentId,
                taskName,
                fab.getLayoutParams() instanceof CoordinatorLayout.LayoutParams ?
                        ((CoordinatorLayout.LayoutParams) fab.getLayoutParams()).gravity :
                        (Gravity.BOTTOM | Gravity.END),
                tctMillis,
                mistakes,
                mistakeLogger.getMistakeDetails()
        );
    }

    private void showExperimentCompleted() {
        new AlertDialog.Builder(this)
                .setTitle("Experiment Completed!")
                .setMessage("Thank you for completing all tasks.\nNext, please fill a questionnaire.")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, which) -> {
                    // Buka Google Form
                    String formUrl = "https://docs.google.com/forms/d/e/1FAIpQLSclNvUJiXBoMwILtiFfQOhPrqqoij2r84iCer4RIrS6fGcRmw/viewform?usp=dialog"; // ganti dengan link aslimu
                    Intent intent = new Intent(Intent.ACTION_VIEW, android.net.Uri.parse(formUrl));
                    startActivity(intent);

                    // Tutup activity setelah diarahkan ke form
                    finish();
                })
                .show();
    }
}
