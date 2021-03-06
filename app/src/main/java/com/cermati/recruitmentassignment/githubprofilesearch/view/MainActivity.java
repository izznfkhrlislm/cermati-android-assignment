package com.cermati.recruitmentassignment.githubprofilesearch.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.SearchView;

import com.cermati.recruitmentassignment.githubprofilesearch.R;

public class MainActivity extends AppCompatActivity {

    FragmentManager mainActivityFm;
    LinearLayout placeholderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityFm = getSupportFragmentManager();
        placeholderView = findViewById(R.id.placeholderScreen);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorite_button:
                Intent favoritedUserIntent = new Intent(this, FavoritedListActivity.class);
                startActivity(favoritedUserIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_button);
        SearchView accountSearchView = (SearchView) menuItem.getActionView();
        accountSearchView.setQueryHint(getResources().getString(R.string.search_placeholder));

        accountSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null && !newText.isEmpty()) {
                    placeholderView.setVisibility(View.GONE);
                    FragmentTransaction mainActivityFt = mainActivityFm.beginTransaction();
                    mainActivityFt.replace(R.id.searchResultFragment, SelectUserFragment.newInstance(newText));
                    mainActivityFt.commit();
                }

                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}