package com.pongodev.recipesapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.pongodev.recipesapp.R;
import com.pongodev.recipesapp.fragments.FragmentRecipes;
import com.pongodev.recipesapp.utils.Utils;

/**
 * Design and developed by pongodev.com
 *
 * ActivityFavorites is created to display list of favorite recipes. Use FragmentRecipes to display
 * recipe list. Created using AppCompatActivity.
 */
public class ActivityFavorites extends AppCompatActivity implements FragmentRecipes.OnRecipeSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Connect toolbar object with toolbar id in xml
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        // Set toolbar as action bar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            // Create the FragmentRecipes and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(Utils.ARG_PAGE, Utils.ARG_FAVORITES);
            FragmentRecipes fragment = new FragmentRecipes();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_container, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_NONE)
                    .commit();
        }

        // Handle item menu in toolbar
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case android.R.id.home:
                        finish();
                        return true;
                    default:
                        return true;
                }
            }
        });
    }

    /**
     * Callback method from {@link FragmentRecipes.OnRecipeSelectedListener}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onRecipeSelected(String ID, String CategoryName) {
        // Call ActivityDetail and pass recipe id to that activity
        Intent detailIntent = new Intent(getApplicationContext(), ActivityDetail.class);
        detailIntent.putExtra(Utils.ARG_KEY, ID);
        detailIntent.putExtra(Utils.ARG_PARENT_ACTIVITY, Utils.ARG_ACTIVITY_FAVORITES);
        startActivity(detailIntent);
        overridePendingTransition(R.anim.open_next, R.anim.close_main);
    }

    // Method to handle physical back button
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Call transition when physical back button pressed
        overridePendingTransition(R.anim.open_main, R.anim.close_next);
    }

    /** Called when returning to the activity */
    @Override
    protected void onResume() {
        super.onResume();

        // Re-create recipes fragment when back to ActivityFavorites
        Bundle arguments = new Bundle();
        arguments.putString(Utils.ARG_PAGE, Utils.ARG_FAVORITES);
        FragmentRecipes fragment = new FragmentRecipes();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.item_container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_NONE)
                .commit();
    }
}
