package com.cermati.recruitmentassignment.githubprofilesearch.repositories;

import androidx.lifecycle.LiveData;

import com.cermati.recruitmentassignment.githubprofilesearch.dao.GithubProfileDao;
import com.cermati.recruitmentassignment.githubprofilesearch.model.GithubProfile;

import java.util.List;

public class GithubProfileRepository {

    private GithubProfileDao githubProfileDao;
    private LiveData<List<GithubProfile>> allGithubProfiles;
}
