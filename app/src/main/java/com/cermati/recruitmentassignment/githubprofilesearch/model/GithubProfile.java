package com.cermati.recruitmentassignment.githubprofilesearch.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "github_profiles")
public class GithubProfile implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @Expose
    @SerializedName("login")
    @ColumnInfo(name = "username")
    private String username;

    @Expose
    @SerializedName("avatar_url")
    @ColumnInfo(name = "avatar_url")
    private String avatarUrl;

    @Expose
    @SerializedName("html_url")
    @ColumnInfo(name = "profile_url")
    private String profileUrl;

    public GithubProfile(String username, String avatarUrl, String profileUrl) {
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.profileUrl = profileUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
