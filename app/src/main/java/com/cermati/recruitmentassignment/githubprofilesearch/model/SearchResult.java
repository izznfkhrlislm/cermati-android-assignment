package com.cermati.recruitmentassignment.githubprofilesearch.model;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

import java.util.List;
import java.io.Serializable;

public class SearchResult implements Serializable {

    @Expose
    @SerializedName("total_count")
    private Integer totalCount;

    @Expose
    @SerializedName("items")
    private List<GithubProfile> githubProfiles;

    private Integer statusCode;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<GithubProfile> getGithubProfiles() {
        return githubProfiles;
    }

    public void setGithubProfiles(List<GithubProfile> githubProfiles) {
        this.githubProfiles = githubProfiles;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
