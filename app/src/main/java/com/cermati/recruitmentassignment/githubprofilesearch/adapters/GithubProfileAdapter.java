package com.cermati.recruitmentassignment.githubprofilesearch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cermati.recruitmentassignment.githubprofilesearch.R;
import com.cermati.recruitmentassignment.githubprofilesearch.model.GithubProfile;

import java.util.List;
import java.util.concurrent.Callable;

public class GithubProfileAdapter extends RecyclerView.Adapter<GithubProfileAdapter.GithubProfileViewHolder> {

    private List<GithubProfile> githubProfiles;
    private Context context;

    public GithubProfileAdapter(Context context, List<GithubProfile> githubProfiles) {
        this.context = context;
        this.githubProfiles = githubProfiles;
    }

    @NonNull
    @Override
    public GithubProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View githubUserMenuView = LayoutInflater.from(context)
                .inflate(R.layout.profile_card, parent, false);
        return new GithubProfileViewHolder(githubUserMenuView);
    }

    @Override
    public void onBindViewHolder(@NonNull GithubProfileViewHolder holder, int position) {
        GithubProfile currentItem = githubProfiles.get(position);
        holder.setData(context, currentItem, position);
    }

    @Override
    public int getItemCount() {
        return githubProfiles.size();
    }

    class GithubProfileViewHolder extends RecyclerView.ViewHolder {
        private View githubUserListView;
        private int position = 0;
        private GithubProfile currentItem;

        public GithubProfileViewHolder(View githubUserListView) {
            super(githubUserListView);
            this.githubUserListView = githubUserListView;
        }

        public void setData(Context context, GithubProfile currentData, int position) {
            this.position = position;
            this.currentItem = currentData;

            TextView githubProfileUsernameTv = githubUserListView.findViewById(R.id.githubProfileUsername);
            githubProfileUsernameTv.setText(currentItem.getUsername());

            TextView githubProfileUrlTv = githubUserListView.findViewById(R.id.githubProfileUrl);
            githubProfileUrlTv.setText(currentItem.getProfileUrl());

            ImageView githubAvatarIv = githubUserListView.findViewById(R.id.profilePictureImg);
            Glide.with(context).load(currentItem.getAvatarUrl()).into(githubAvatarIv);
        }
    }

}
