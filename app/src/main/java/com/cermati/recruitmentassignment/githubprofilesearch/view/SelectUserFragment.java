package com.cermati.recruitmentassignment.githubprofilesearch.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cermati.recruitmentassignment.githubprofilesearch.R;
import com.cermati.recruitmentassignment.githubprofilesearch.adapters.GithubProfileAdapter;
import com.cermati.recruitmentassignment.githubprofilesearch.model.GithubProfile;
import com.cermati.recruitmentassignment.githubprofilesearch.viewmodel.GithubProfileViewModel;

import java.util.ArrayList;
import java.util.List;

public class SelectUserFragment extends Fragment {

    private static final String QUERY_PARAMETER_ARGS_KEY = "queryParameter";

    List<GithubProfile> githubProfileList = new ArrayList<>();
    GithubProfileAdapter adapter;
    GithubProfileViewModel viewModel;
    RecyclerView selectUserRv;
    View loadingAnimationView;

    public SelectUserFragment() {

    }

    public static SelectUserFragment newInstance(String queryParameter) {
        Bundle argsBundle = new Bundle();
        argsBundle.putString(QUERY_PARAMETER_ARGS_KEY, queryParameter);
        SelectUserFragment instance = new SelectUserFragment();
        instance.setArguments(argsBundle);

        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View fragmentView = inflater.inflate(
                R.layout.select_user_fragment, container, false);

        selectUserRv = fragmentView.findViewById(R.id.selectUserFragmentRv);
        loadingAnimationView = fragmentView.findViewById(R.id.loadingAnimationView);

        viewModel = new ViewModelProvider(this).get(GithubProfileViewModel.class);
        loadingAnimationView.setVisibility(View.VISIBLE);
        viewModel.init(getActivity().getApplication(), getArguments().getString(QUERY_PARAMETER_ARGS_KEY));
        viewModel.getMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<GithubProfile>>() {
            @Override
            public void onChanged(List<GithubProfile> githubProfiles) {
                loadingAnimationView.setVisibility(View.GONE);
                if (githubProfiles != null) {
                    githubProfileList.addAll(githubProfiles);
                }
                adapter.notifyDataSetChanged();
            }
        });
        setupRecyclerView();

        return fragmentView;
    }

    private void setupRecyclerView() {
        if (adapter == null) {
            adapter = new GithubProfileAdapter(getContext(), githubProfileList);
            selectUserRv.setLayoutManager(new LinearLayoutManager(getContext()));
            selectUserRv.setAdapter(adapter);
            selectUserRv.setItemAnimator(new DefaultItemAnimator());
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}
