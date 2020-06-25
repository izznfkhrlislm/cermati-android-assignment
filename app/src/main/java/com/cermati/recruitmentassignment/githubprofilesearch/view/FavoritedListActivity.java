package com.cermati.recruitmentassignment.githubprofilesearch.view;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cermati.recruitmentassignment.githubprofilesearch.R;
import com.cermati.recruitmentassignment.githubprofilesearch.adapters.Callable;
import com.cermati.recruitmentassignment.githubprofilesearch.adapters.GithubProfileAdapter;
import com.cermati.recruitmentassignment.githubprofilesearch.model.GithubProfile;
import com.cermati.recruitmentassignment.githubprofilesearch.viewmodel.GithubProfileViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FavoritedListActivity extends AppCompatActivity implements Callable {

    LinearLayout placeholderView;
    List<GithubProfile> favoritedGithubProfiles = new ArrayList<>();
    GithubProfileAdapter adapter;
    GithubProfileViewModel viewModel;
    RecyclerView favoritedUserRv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorited_list);
        setTitle("Favorited Github Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        placeholderView = findViewById(R.id.placeholderScreen);
        favoritedUserRv = findViewById(R.id.favoritedUserRv);

        viewModel = new ViewModelProvider(this).get(GithubProfileViewModel.class);
        viewModel.init(getApplication());
        try {
            viewModel.getFavoritedGithubProfiles().observe(this, new Observer<List<GithubProfile>>() {
                @Override
                public void onChanged(List<GithubProfile> githubProfiles) {
                    if (!githubProfiles.isEmpty() && placeholderView.getVisibility() != View.GONE) {
                        placeholderView.setVisibility(View.GONE);
                        favoritedGithubProfiles.addAll(githubProfiles);
                    } else {
                        placeholderView.setVisibility(View.VISIBLE);
                    }
                    adapter.notifyDataSetChanged();
                }
            });
            setupRecyclerView();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setupRecyclerView() {
        if (adapter == null) {
            adapter = new GithubProfileAdapter(this, favoritedGithubProfiles, this);
            favoritedUserRv.setLayoutManager(new LinearLayoutManager(this));
            favoritedUserRv.setAdapter(adapter);
            favoritedUserRv.setItemAnimator(new DefaultItemAnimator());
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onClickCallback(GithubProfile githubProfile, boolean isChecked) {
        if (isChecked) {
            viewModel.insert(githubProfile);
            Toast.makeText(this, "User added to favorited list!", Toast.LENGTH_SHORT).show();
        } else {
            viewModel.delete(githubProfile);
            Toast.makeText(this, "User removed from favorited list!", Toast.LENGTH_SHORT).show();
        }
    }
}
