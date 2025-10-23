package com.example.thesis3;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thesis3.adapter.PostAdapter;
import com.example.thesis3.data.DummyPosts;
import com.example.thesis3.model.FabConfig;
import com.example.thesis3.model.Post;
import com.example.thesis3.utils.MistakeLogger;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public abstract class BaseFeedActivity extends AppCompatActivity {

    protected RecyclerView recyclerView;
    protected FloatingActionButton fab;
    protected List<Post> posts;
    protected MistakeLogger mistakeLogger;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        recyclerView = findViewById(R.id.recyclerViewFeed);
        fab = findViewById(R.id.fab);
        mistakeLogger = new MistakeLogger();

        posts = DummyPosts.getPosts();
        setupRecyclerView();
        setupFab();
        setupMistakeDetectors();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        PostAdapter adapter = new PostAdapter(posts, new PostAdapter.OnPostActionListener() {
            @Override
            public void onCommentClicked(Post post) {
                onPostComment(post);
            }

            @Override
            public void onArchiveClicked(Post post) {
                onPostArchive(post);
            }

            @Override
            public void onReportClicked(Post post) {
                onPostReport(post);
            }

            @Override
            public void onMistakeClicked(Post post, String source) {
                // kirim ke Activity biar diputuskan di sana
                onMistakeDetected(source);
            }
        });

        recyclerView.setAdapter(adapter);

        // klik area kosong pada recyclerView (jika tidak kena item)
        recyclerView.setOnClickListener(v -> onMistakeDetected("Empty Area"));
    }

    private void setupFab() {
        FabConfig config = getFabConfig();
        fab.hide();

        fab.setImageResource(config.getIconResId());
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        params.gravity = config.getGravity() != 0 ? config.getGravity() : Gravity.BOTTOM | Gravity.END;
        int margin = (int) (config.getMarginDp() * getResources().getDisplayMetrics().density);
        params.setMargins(margin, margin, margin, margin);
        fab.setLayoutParams(params);

        if (config.isVisible()) fab.show();
        else fab.hide();

        fab.setOnClickListener(v -> onFabClicked());
    }

    private void setupMistakeDetectors() {
        ImageView ivProfile = findViewById(R.id.ivProfile);
        EditText etSearch = findViewById(R.id.etSearch);

        if (ivProfile != null) {
            ivProfile.setOnClickListener(v -> onMistakeDetected("Profile Icon"));
        }

        if (etSearch != null) {
            etSearch.setOnClickListener(v -> onMistakeDetected("Search Bar"));
        }
    }

    /**
     * Dipanggil ketika user melakukan klik yang salah (baik di UI utama atau RecyclerView).
     * Bisa di-override di subclass untuk menangani cara pencatatan.
     */
    protected void onMistakeDetected(String source) {
        // default behavior: langsung dicatat di logger
        mistakeLogger.addMistake(source);
        Toast.makeText(this, "Try Again!", Toast.LENGTH_SHORT).show();
    }

    // abstract methods yang wajib diimplementasi oleh subclass
    protected abstract FabConfig getFabConfig();
    protected abstract void onFabClicked();

    // opsional override
    protected void onPostComment(Post post) {}
    protected void onPostArchive(Post post) {}
    protected void onPostReport(Post post) {}
}
