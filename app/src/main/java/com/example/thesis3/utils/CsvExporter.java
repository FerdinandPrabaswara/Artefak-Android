package com.example.thesis3.utils;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvExporter {

    private static final String TAG = "CsvExporter";

    public static void writeRow(Context context,
                                String respondentId,
                                String taskName,
                                int fabGravity,
                                long tctMillis,
                                int mistakes,
                                List<String> mistakeSourcesList) {

        // jalankan di background supaya nggak ngeblok UI thread
        new Thread(() -> {
            try {
                // SIMPAN DI INTERNAL STORAGE (data/data/.../files)
                File folder = context.getFilesDir();
                if (folder != null && !folder.exists()) {
                    boolean ok = folder.mkdirs();
                    Log.d(TAG, "mkdirs created? " + ok);
                }

                // fallback nama file kalau respondentId null/empty
                String safeRespondentId = (respondentId == null || respondentId.trim().isEmpty())
                        ? "resp_" + System.currentTimeMillis()
                        : respondentId.trim();

                File file = new File(folder, safeRespondentId + ".csv");

                boolean newFile = !file.exists();

                // try-with-resources untuk auto-close
                try (FileWriter writer = new FileWriter(file, true)) {

                    if (newFile) {
                        writer.append("respondent_id;task_name;fab_position;tct_ms;mistakes;mistake_source\n");
                    }

                    String fabPosition = mapFabPosition(fabGravity);

                    String mistakeSources = (mistakeSourcesList == null || mistakeSourcesList.isEmpty())
                            ? ""
                            : String.join(" | ", mistakeSourcesList);

                    writer.append(safeRespondentId).append(";")
                            .append(taskName == null ? "" : taskName).append(";")
                            .append(fabPosition).append(";")
                            .append(String.valueOf(tctMillis)).append(";")
                            .append(String.valueOf(mistakes)).append(";")
                            .append("\"").append(mistakeSources).append("\"")
                            .append("\n");

                    writer.flush();
                }

                Log.d(TAG, "✅ Data tersimpan di internal: " + file.getAbsolutePath());

            } catch (IOException e) {
                Log.e(TAG, "❌ Gagal menulis CSV", e);
            }
        }).start();
    }

    private static String mapFabPosition(int gravity) {
        if ((gravity & Gravity.START) == Gravity.START) return "left";
        if ((gravity & Gravity.END) == Gravity.END) return "right";
        if ((gravity & Gravity.CENTER_HORIZONTAL) == Gravity.CENTER_HORIZONTAL) return "center";
        return "unknown";
    }
}
