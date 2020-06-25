package com.cermati.recruitmentassignment.githubprofilesearch.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cermati.recruitmentassignment.githubprofilesearch.model.GithubProfile;
import com.cermati.recruitmentassignment.githubprofilesearch.model.SearchResult;
import com.cermati.recruitmentassignment.githubprofilesearch.repositories.GithubProfileRepository;

import java.util.List;

public class GithubProfileViewModel extends ViewModel {
    private GithubProfileRepository repository;
    private LiveData<SearchResult> searchResult;
    private MutableLiveData<SearchResult> searchResultMutableLiveData;

    public void init(Application application, String usernameQuery) {
        repository = new GithubProfileRepository(application);
        searchResult = repository.getFetchedSearchResult();
        searchResultMutableLiveData = repository.getSearchResultFromAPI(usernameQuery);
    }

    public MutableLiveData<SearchResult> getSearchResultMutableLiveData() {
        return searchResultMutableLiveData;
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

    public LiveData<SearchResult> getSearchResult() {
        return searchResult;
    }
}
