package com.cermati.recruitmentassignment.githubprofilesearch.utils;

import com.cermati.recruitmentassignment.githubprofilesearch.model.SearchResult;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class ApiCallUtils {

    private static ApiInterface apiInterface;
    private static final String BASE_URL = "https://api.github.com/";

    public static ApiInterface getApiInterfaceInstance() {
        if (apiInterface == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

            Retrofit retrofitInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiInterface = retrofitInstance.create(ApiInterface.class);
        }

        return apiInterface;
    }

    public interface ApiInterface {

        @GET("search/users")
        Call<SearchResult> getGithubUserProfileByUsername(@Query("q") String query);
    }
}
