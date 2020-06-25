package com.cermati.recruitmentassignment.githubprofilesearch.adapters;

import com.cermati.recruitmentassignment.githubprofilesearch.model.GithubProfile;

public interface Callable {
    void onClickCallback(GithubProfile githubProfile, boolean isChecked);
}
