package com.example.thesis3.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thesis3.R;
import com.example.thesis3.model.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private final List<Post> postList;
    private final OnPostActionListener listener;

    public interface OnPostActionListener {
        void onCommentClicked(Post post);
        void onArchiveClicked(Post post);
        void onReportClicked(Post post);
        void onMistakeClicked(Post post, String type);
    }

    public PostAdapter(List<Post> postList, OnPostActionListener listener) {
        this.postList = postList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);

        holder.tvUsername.setText(post.getUsername());
        holder.tvPostContent.setText(post.getContent());
        holder.ivUserProfile.setImageResource(post.getProfileResId());

        holder.btnComment.setOnClickListener(v -> {
            if (listener != null) listener.onCommentClicked(post);
        });

        holder.btnArchive.setOnClickListener(v -> {
            if (listener != null) listener.onArchiveClicked(post);
        });

        holder.btnReport.setOnClickListener(v -> {
            if (listener != null) listener.onReportClicked(post);
        });

        View.OnClickListener mistakeListener = v -> {
            if (listener != null) {
                String viewName = getViewName(v, holder);
                listener.onMistakeClicked(post, viewName);
            }
        };

        holder.itemView.setOnClickListener(mistakeListener);
        holder.tvUsername.setOnClickListener(mistakeListener);
        holder.ivUserProfile.setOnClickListener(mistakeListener);
        holder.tvPostContent.setOnClickListener(mistakeListener);
    }

    private String getViewName(View v, PostViewHolder h) {
        if (v == h.ivUserProfile) return "User Profile Picture";
        if (v == h.tvUsername) return "Username";
        if (v == h.tvPostContent) return "Post Content";
        if (v == h.itemView) return "Post Area";
        if (v.getId() == R.id.etSearch) return "Search Bar";
        return "Empty Area";
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView ivUserProfile;
        TextView tvUsername, tvPostContent;
        ImageButton btnArchive, btnComment, btnReport;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            ivUserProfile = itemView.findViewById(R.id.ivUserProfile);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvPostContent = itemView.findViewById(R.id.tvPostContent);
            btnArchive = itemView.findViewById(R.id.btnArchive);
            btnComment = itemView.findViewById(R.id.btnComment);
            btnReport = itemView.findViewById(R.id.btnReport);
        }
    }
}
