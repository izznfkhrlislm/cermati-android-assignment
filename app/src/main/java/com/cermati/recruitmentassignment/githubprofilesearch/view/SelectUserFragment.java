package com.cermati.recruitmentassignment.githubprofilesearch.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

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
import com.cermati.recruitmentassignment.githubprofilesearch.model.SearchResult;
import com.cermati.recruitmentassignment.githubprofilesearch.viewmodel.GithubProfileViewModel;

import java.util.ArrayList;
import java.util.List;

public class SelectUserFragment extends Fragment {

    private static final String QUERY_PARAMETER_ARGS_KEY = "queryParameter";
    private static final int STATUS_CODE_RATE_LIMIT_EXCEEDED = 403;
    private static final int STATUS_CODE_INTERNAL_SERVER_ERROR = 500;
    private static final int STATUS_CODE_OK = 200;

    List<GithubProfile> githubProfileList = new ArrayList<>();
    GithubProfileAdapter adapter;
    GithubProfileViewModel viewModel;
    RecyclerView selectUserRv;
    View loadingAnimationView;
    View errorMessageView;
    TextView errorMessageText;
    ToggleButton favoriteToggleBtn;

    public SelectUserFragment() {
        super();
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
        final View fragmentView = inflater.inflate(
                R.layout.select_user_fragment, container, false);

        selectUserRv = fragmentView.findViewById(R.id.selectUserFragmentRv);
        loadingAnimationView = fragmentView.findViewById(R.id.loadingAnimationView);
        errorMessageView = fragmentView.findViewById(R.id.errorMessage);
        errorMessageView.setVisibility(View.GONE);
        errorMessageText = fragmentView.findViewById(R.id.errorMessageText);

        favoriteToggleBtn = fragmentView.findViewById(R.id.favoriteToggleBtn);
        setupToggleButtonAnimation(favoriteToggleBtn);

        viewModel = new ViewModelProvider(this).get(GithubProfileViewModel.class);
        loadingAnimationView.setVisibility(View.VISIBLE);

        viewModel.init(getActivity().getApplication(), getArguments().getString(QUERY_PARAMETER_ARGS_KEY));
        viewModel.getSearchResultMutableLiveData().observe(getViewLifecycleOwner(), new Observer<SearchResult>() {
            @Override
            public void onChanged(SearchResult searchResult) {
                loadingAnimationView.setVisibility(View.GONE);
                if (searchResult.getGithubProfiles() != null && searchResult.getStatusCode() == STATUS_CODE_OK) {
                    if (errorMessageView.getVisibility() == View.VISIBLE) {
                        errorMessageView.setVisibility(View.GONE);
                    }
                    githubProfileList.addAll(searchResult.getGithubProfiles());
                } else {
                    String errorMessage = "";
                    errorMessageView.setVisibility(View.VISIBLE);
                    switch (searchResult.getStatusCode()) {
                        case STATUS_CODE_RATE_LIMIT_EXCEEDED:
                            errorMessage = getString(R.string.error_message, "403", "Rate Limit Exceeded");
                            break;
                        case STATUS_CODE_INTERNAL_SERVER_ERROR:
                            errorMessage = getString(R.string.error_message, "500", "Internal Server Error");
                            break;
                    }
                    errorMessageText.setText(errorMessage);
                    Log.e("SearchUserFragment", errorMessage);
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

    private void setupToggleButtonAnimation(ToggleButton toggleButton) {
        final ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f,
                1.0f, Animation.RELATIVE_TO_SELF, 0.7f,
                Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(500);
        BounceInterpolator bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);

        if (toggleButton != null) {
            toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    buttonView.startAnimation(scaleAnimation);
                }
            });
        }
    }
}
