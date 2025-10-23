package com.example.thesis3.data;

import com.example.thesis3.model.TestItem;
import java.util.ArrayList;
import java.util.List;

public class ExperimentData {

    public static List<TestItem> getExperimentTasks() {
        List<TestItem> mainTasks = new ArrayList<>();

        // 10 FAB tasks
        mainTasks.add(new TestItem(1, "Buat postingan baru", true, "FAB"));
        mainTasks.add(new TestItem(2, "Unggah media baru", true, "FAB"));
        mainTasks.add(new TestItem(3, "Buat polling", true, "FAB"));
        mainTasks.add(new TestItem(4, "Buat grup baru", true, "FAB"));
        mainTasks.add(new TestItem(5, "Kirim pesan", true, "FAB"));
        mainTasks.add(new TestItem(6, "Mulai live", true, "FAB"));
        mainTasks.add(new TestItem(7, "Tanya AI", true, "FAB"));
        mainTasks.add(new TestItem(8, "Scan qr untuk menambahkan teman baru", true, "FAB"));
        mainTasks.add(new TestItem(9, "Tambah koleksi baru", true, "FAB"));
        mainTasks.add(new TestItem(10, "Bagikan postingan", true, "FAB"));

        // 2 filler (tanpa FAB)
        mainTasks.add(new TestItem(11, "Ganti foto profil baru", false, "Profile Icon"));
        mainTasks.add(new TestItem(12, "Cari pengguna lain", false, "Search Bar"));

        return mainTasks;
    }
}

