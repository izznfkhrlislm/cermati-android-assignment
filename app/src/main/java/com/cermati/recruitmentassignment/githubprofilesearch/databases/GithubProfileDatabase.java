package com.cermati.recruitmentassignment.githubprofilesearch.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cermati.recruitmentassignment.githubprofilesearch.dao.GithubProfileDao;
import com.cermati.recruitmentassignment.githubprofilesearch.model.GithubProfile;

@Database(entities = {GithubProfile.class}, version = 1, exportSchema = false)
public abstract class GithubProfileDatabase extends RoomDatabase{

    private static GithubProfileDatabase instance;

    public abstract GithubProfileDao githubProfileDao();

    public static synchronized GithubProfileDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    GithubProfileDatabase.class,
                    "github_profile_database"
            ).fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }

        return instance;
    }
}
