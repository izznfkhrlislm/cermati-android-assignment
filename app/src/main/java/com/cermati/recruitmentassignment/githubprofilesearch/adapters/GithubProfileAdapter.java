package com.cermati.recruitmentassignment.githubprofilesearch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.cermati.recruitmentassignment.githubprofilesearch.R;
import com.cermati.recruitmentassignment.githubprofilesearch.model.GithubProfile;

import java.util.List;

public class GithubProfileAdapter extends RecyclerView.Adapter<GithubProfileAdapter.GithubProfileViewHolder> {

    private List<GithubProfile> githubProfiles;
    private Context context;
    private Callable callable;

    public GithubProfileAdapter(Context context, List<GithubProfile> githubProfiles, Callable callable) {
        this.context = context;
        this.githubProfiles = githubProfiles;
        this.callable = callable;
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

        public void setData(Context context, final GithubProfile currentData, int position) {

            this.position = position;
            this.currentItem = currentData;

            TextView githubProfileUsernameTv = githubUserListView.findViewById(R.id.githubProfileUsername);
            githubProfileUsernameTv.setText(currentItem.getUsername());

            TextView githubProfileUrlTv = githubUserListView.findViewById(R.id.githubProfileUrl);
            githubProfileUrlTv.setText(currentItem.getProfileUrl());

            ImageView githubAvatarIv = githubUserListView.findViewById(R.id.profilePictureImg);
            Glide.with(context)
                    .load(currentItem.getAvatarUrl())
                    .apply(new RequestOptions()
                            .transforms(new CenterCrop(), new CircleCrop())
                    ).into(githubAvatarIv);

            ToggleButton favoriteToggleBtn = githubUserListView.findViewById(R.id.favoriteToggleBtn);
            final ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f,
                    1.0f, Animation.RELATIVE_TO_SELF, 0.7f,
                    Animation.RELATIVE_TO_SELF, 0.7f);
            scaleAnimation.setDuration(500);
            BounceInterpolator bounceInterpolator = new BounceInterpolator();
            scaleAnimation.setInterpolator(bounceInterpolator);
            if (favoriteToggleBtn != null) {
                //TODO: Find a way to dynamically mark toggle button!
                favoriteToggleBtn.setChecked(true);
                favoriteToggleBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        buttonView.startAnimation(scaleAnimation);
                        callable.onClickCallback(currentItem, isChecked);
                    }
                });
            }
        }
    }

}
