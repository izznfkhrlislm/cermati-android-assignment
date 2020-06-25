package com.cermati.recruitmentassignment.githubprofilesearch.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cermati.recruitmentassignment.githubprofilesearch.model.GithubProfile;
import com.cermati.recruitmentassignment.githubprofilesearch.model.SearchResult;
import com.cermati.recruitmentassignment.githubprofilesearch.repositories.GithubProfileRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class GithubProfileViewModel extends ViewModel {
    private GithubProfileRepository repository;
    private MutableLiveData<SearchResult> searchResultMutableLiveData;

    public void init(Application application, String usernameQuery) {
        repository = new GithubProfileRepository(application);
        searchResultMutableLiveData = repository.getSearchResultFromAPI(usernameQuery);
    }

    public void init(Application application) {
        repository = new GithubProfileRepository(application);
    }

    public MutableLiveData<SearchResult> getSearchResultMutableLiveData() {
        return searchResultMutableLiveData;
    }

    public LiveData<List<GithubProfile>> getFavoritedGithubProfiles() throws ExecutionException, InterruptedException {
        return repository.getAll();
    }

    public void insert(GithubProfile githubProfile) {
        repository.insert(githubProfile);
    }

    public void update(GithubProfile githubProfile) {
        repository.update(githubProfile);
    }

    public void delete(GithubProfile githubProfile) {
        repository.delete(githubProfile);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
