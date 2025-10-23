package com.example.thesis3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class OnBoarding extends AppCompatActivity {

    private Button btnStart;
    private TextView tvInfo;
    private boolean isAgreed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.on_boarding);

        btnStart = findViewById(R.id.btnStart);
        tvInfo = findViewById(R.id.tvInfo);

        // Awalnya tombol Start tidak bisa ditekan
        btnStart.setEnabled(false);

        // Klik "Read This Instructions"
        tvInfo.setOnClickListener(v -> showInstructionsDialog());

        // Klik tombol Start
        btnStart.setOnClickListener(v -> {
            if (isAgreed) {
                // Auto generate respondent ID
                SharedPreferences prefs = getSharedPreferences("experiment_prefs", MODE_PRIVATE);
                int lastId = prefs.getInt("lastRespondentId", 0);
                int newId = lastId + 1;

                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("lastRespondentId", newId);
                editor.apply();

                String respondentId = "R" + newId;

                Intent intent = new Intent(OnBoarding.this, TrialActivity.class);
                intent.putExtra("respondent_id", respondentId);
                startActivity(intent);

                finish(); // supaya user tidak kembali ke onboarding
            } else {
                Toast.makeText(this, "Please read the instructions first", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showInstructionsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Experiment Instruction");

        View dialogView = getLayoutInflater().inflate(R.layout.instructions_main, null);
        CheckBox checkAgree = dialogView.findViewById(R.id.checkAgree);

        builder.setView(dialogView);
        builder.setPositiveButton("OK", null);
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();

        // Gunakan setOnShowListener supaya tombol sudah siap
        dialog.setOnShowListener(d -> {
            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(v -> {
                if (checkAgree.isChecked()) {
                    isAgreed = true;
                    btnStart.setEnabled(true);
                    dialog.dismiss();
                    Toast.makeText(OnBoarding.this, "You can start the experiment now", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OnBoarding.this, "Please tick 'I agree' first", Toast.LENGTH_SHORT).show();
                }
            });
        });

        dialog.show();
    }
}
