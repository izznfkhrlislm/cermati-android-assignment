package com.cermati.recruitmentassignment.githubprofilesearch.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cermati.recruitmentassignment.githubprofilesearch.dao.GithubProfileDao;
import com.cermati.recruitmentassignment.githubprofilesearch.databases.GithubProfileDatabase;
import com.cermati.recruitmentassignment.githubprofilesearch.model.GithubProfile;
import com.cermati.recruitmentassignment.githubprofilesearch.model.SearchResult;
import com.cermati.recruitmentassignment.githubprofilesearch.utils.ApiCallUtils;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GithubProfileRepository {

    private GithubProfileDao githubProfileDao;
    private LiveData<List<GithubProfile>> allGithubProfiles;
    private ApiCallUtils.ApiInterface apiInterface;

    public GithubProfileRepository(Application application) {
        GithubProfileDatabase database = GithubProfileDatabase.getInstance(application);
        githubProfileDao = database.githubProfileDao();
        allGithubProfiles = githubProfileDao.getAllRetrievedProfiles();
    }

    public void insert(GithubProfile githubProfile) {
        new InsertGithubProfileAsyncTask(githubProfileDao).execute(githubProfile);
    }

    public void update(GithubProfile githubProfile) {
        new UpdateGithubProfileAsyncTask(githubProfileDao).execute(githubProfile);
    }

    public void deleteAll() {
        new DeleteAllFetchedGithubProfileAsyncTask(githubProfileDao).execute();
    }

    public LiveData<List<GithubProfile>> getAllGithubProfiles() {
        return allGithubProfiles;
    }

    public MutableLiveData<List<GithubProfile>> getGithubProfileListFromAPI(String username) {
        final MutableLiveData<List<GithubProfile>> responseData = new MutableLiveData<>();
        apiInterface = ApiCallUtils.getApiInterfaceInstance();
        Call<SearchResult> apiCall = apiInterface.getGithubUserProfileByUsername(username);
        apiCall.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                if (response.isSuccessful()) {
                    Log.i("Get from Github API", response.body().toString());
                    responseData.setValue(response.body().getGithubProfiles());
                } else {
                    try {
                        Log.e("Request URL", call.request().url().toString());
                        Log.e("Get from Github API", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                responseData.setValue(null);
            }
        });

        return responseData;
    }

    private static class InsertGithubProfileAsyncTask extends AsyncTask<GithubProfile, Void, Void> {
        private GithubProfileDao githubProfileDao;

        private InsertGithubProfileAsyncTask(GithubProfileDao githubProfileDao) {
            this.githubProfileDao = githubProfileDao;
        }

        @Override
        protected Void doInBackground(GithubProfile... githubProfiles) {
            githubProfileDao.insert(githubProfiles[0]);
            return null;
        }
    }

    private static class UpdateGithubProfileAsyncTask extends AsyncTask<GithubProfile, Void, Void> {
        private GithubProfileDao githubProfileDao;

        private UpdateGithubProfileAsyncTask(GithubProfileDao githubProfileDao) {
            this.githubProfileDao = githubProfileDao;
        }

        @Override
        protected Void doInBackground(GithubProfile... githubProfiles) {
            githubProfileDao.update(githubProfiles[0]);
            return null;
        }
    }

    private static class DeleteAllFetchedGithubProfileAsyncTask extends AsyncTask<Void, Void, Void> {
        private GithubProfileDao githubProfileDao;

        private DeleteAllFetchedGithubProfileAsyncTask(GithubProfileDao githubProfileDao) {
            this.githubProfileDao = githubProfileDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            githubProfileDao.deleteAllRetrievedProfiles();
            return null;
        }
    }
}
