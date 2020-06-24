package com.cermati.recruitmentassignment.githubprofilesearch.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Query;

import java.util.List;

import com.cermati.recruitmentassignment.githubprofilesearch.model.GithubProfile;

@Dao
public interface GithubProfileDao {

    @Insert
    void insert(GithubProfile githubProfile);

    @Update
    void update(GithubProfile githubProfile);

    @Query("DELETE FROM github_profiles")
    void deleteAllRetrievedProfiles();

    @Query("SELECT * FROM github_profiles ORDER BY id")
    LiveData<List<GithubProfile>> getAllRetrievedProfiles();
}
