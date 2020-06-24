package com.cermati.recruitmentassignment.githubprofilesearch.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cermati.recruitmentassignment.githubprofilesearch.model.GithubProfile;
import com.cermati.recruitmentassignment.githubprofilesearch.repositories.GithubProfileRepository;

import java.util.List;

public class GithubProfileViewModel extends ViewModel {
    private GithubProfileRepository repository;
    private LiveData<List<GithubProfile>> fetchedGithubProfiles;
    private MutableLiveData<List<GithubProfile>> mutableLiveData;

    public void init(Application application, String usernameQuery) {
        repository = new GithubProfileRepository(application);
        fetchedGithubProfiles = repository.getAllGithubProfiles();
        mutableLiveData = repository.getGithubProfileListFromAPI(usernameQuery);
    }

    public MutableLiveData<List<GithubProfile>> getMutableLiveData() {
        return mutableLiveData;
    }

    public void insert(GithubProfile githubProfile) {
        repository.insert(githubProfile);
    }

    public void update(GithubProfile githubProfile) {
        repository.update(githubProfile);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public LiveData<List<GithubProfile>> getFetchedGithubProfiles() {
        return fetchedGithubProfiles;
    }
}
