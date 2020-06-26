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

import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GithubProfileRepository {

    private static final int STATUS_CODE_INTERNAL_SERVER_ERROR = 500;

    private GithubProfileDao githubProfileDao;

    public GithubProfileRepository(Application application) {
        GithubProfileDatabase database = GithubProfileDatabase.getInstance(application);
        githubProfileDao = database.githubProfileDao();
    }

    public LiveData<List<GithubProfile>> getAll() {
        return githubProfileDao.getFavoritedGithubProfiles();
    }

    public void insert(GithubProfile githubProfile) {
        new InsertGithubProfileAsyncTask(githubProfileDao).execute(githubProfile);
        Log.i("Repository", "DAO insert function!");
    }

    public void update(GithubProfile githubProfile) {
        new UpdateGithubProfileAsyncTask(githubProfileDao).execute(githubProfile);
    }

    public void delete(GithubProfile githubProfile) {
        new DeleteGithubProfileAsyncTask(githubProfileDao).execute(githubProfile);
    }

    public void deleteAll() {
        new DeleteAllFetchedGithubProfileAsyncTask(githubProfileDao).execute();
    }

    public MutableLiveData<SearchResult> getSearchResultFromAPI(String username) {
        final MutableLiveData<SearchResult> responseData = new MutableLiveData<>();
        ApiCallUtils.ApiInterface apiInterface = ApiCallUtils.getApiInterfaceInstance();

        Call<SearchResult> apiCall = apiInterface.getGithubUserProfileByUsername(username);
        apiCall.enqueue(new Callback<SearchResult>() {
            SearchResult responseModel;
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                if (response.isSuccessful()) {
                    responseModel = response.body();
                } else {
                    responseModel = new SearchResult();
                }
                responseModel.setStatusCode(response.code());
                responseData.setValue(responseModel);
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                responseModel = new SearchResult();
                responseModel.setStatusCode(STATUS_CODE_INTERNAL_SERVER_ERROR);
                responseData.setValue(responseModel);
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
            Log.i("Repository", "DAO insert AsyncTask!");
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

    private static class DeleteGithubProfileAsyncTask extends AsyncTask<GithubProfile, Void, Void> {
        private GithubProfileDao githubProfileDao;

        private DeleteGithubProfileAsyncTask(GithubProfileDao githubProfileDao) {
            this.githubProfileDao = githubProfileDao;
        }

        @Override
        protected Void doInBackground(GithubProfile... githubProfiles) {
            githubProfileDao.delete(githubProfiles[0]);
            return null;
        }
    }
}
