
package com.pongodev.recipesapp.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.pongodev.recipesapp.R;
import com.pongodev.recipesapp.fragments.FragmentCategories;
import com.pongodev.recipesapp.fragments.FragmentRecipes;
import com.pongodev.recipesapp.utils.DBHelperFavorites;
import com.pongodev.recipesapp.utils.DBHelperRecipes;
import com.pongodev.recipesapp.utils.Utils;

import java.io.IOException;


/**
 * Design and developed by pongodev.com
 *
 * ActivityHome is created to display recipes list in two different presentation.
 * Single pane layout when app run on smartphone and two pane layout when app run on tablet.
 * Created using AppCompatActivity.
 */
public class ActivityHome extends AppCompatActivity
        implements FragmentCategories.OnCategorySelectedListener,
        FragmentRecipes.OnRecipeSelectedListener {

    private DBHelperRecipes mDBhelperRecipes;
    private DBHelperFavorites mDBhelperFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mDBhelperRecipes = new DBHelperRecipes(this);
        mDBhelperFavorites = new DBHelperFavorites(this);

        // Create database of recipes.
        try {
            mDBhelperRecipes.createDataBase();
            mDBhelperFavorites.createDataBase();
        }catch(IOException ioe){
            throw new Error("Unable to create database");
        }

        mDBhelperRecipes.openDataBase();
        mDBhelperFavorites.openDataBase();

        // Connect toolbar object with toolbar id in xml
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        // Set toolbar as actionbar
        setSupportActionBar(mToolbar);
        // Set logo to toolbar
        mToolbar.setLogo(R.mipmap.ic_logo);

        // Handle item menu in toolbar
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menuSearch:
                        return true;
                    case R.id.menuFavorites:
                        // Open ActivityFavorites
                        Intent favoritesIntent = new Intent(getApplicationContext(),
                                ActivityFavorites.class);
                        startActivity(favoritesIntent);
                        overridePendingTransition(R.anim.open_next, R.anim.close_main);
                        return true;
                    case R.id.menuAbout:
                        // Open ActivityAbout
                        Intent aboutIntent = new Intent(getApplicationContext(),
                                ActivityAbout.class);
                        startActivity(aboutIntent);
                        overridePendingTransition(R.anim.open_next, R.anim.close_main);
                        return true;
                    default:
                        return true;
                }
            }
        });


        // If app run in single pane layout.
        if (findViewById(R.id.item_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of FragmentCategories
            FragmentCategories fragCategory = new FragmentCategories();

            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments.
            fragCategory.setArguments(getIntent().getExtras());

            // Add the fragment to the 'item_container' FrameLayout.
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_container, fragCategory).commit();
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link FragmentCategories.OnCategorySelectedListener}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onCategorySelected(String ID, String CategoryName) {

        // Create an instance of FragmentRecipes
        FragmentRecipes fragRecipes = (FragmentRecipes)
                getSupportFragmentManager().findFragmentById(R.id.item_detail_container);

        if (fragRecipes != null) {
            // In two-pane mode, show the recipes list in this activity by
            // adding or replacing the FragmentRecipes using a
            // fragment transaction.
            fragRecipes.updateRecipes(ID, Utils.ARG_CATEGORY);

        } else {


            // If the FragmentRecipes is not available, we're in the one-pane layout
            // and must swap frags... create instance of FragmentRecipes
            //  and give it an argument for the selected article
            FragmentRecipes fragment = new FragmentRecipes();
            Bundle args = new Bundle();
            args.putString(Utils.ARG_KEY, ID);
            args.putString(Utils.ARG_PAGE, Utils.ARG_CATEGORY);
            args.putString(Utils.ARG_CATEGORY, CategoryName);
            fragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the item_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.item_container, fragment);
            transaction.addToBackStack(null);
            transaction.setTransition(FragmentTransaction.TRANSIT_NONE);

            // Commit the transaction.
            transaction.commit();


        }

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
        detailIntent.putExtra(Utils.ARG_PARENT_ACTIVITY, Utils.ARG_ACTIVITY_HOME);
        startActivity(detailIntent);
        overridePendingTransition(R.anim.open_next, R.anim.close_main);
    }

    // Method to add actionbar item menu layout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);

        // Get the SearchView and set the searchable configuration.
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menuSearch).getActionView();
        // Assumes current activity is the searchable activity.
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        // Do not iconify the widget; expand it by default.
        searchView.setIconifiedByDefault(false);

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // If database is open than close database
        if(mDBhelperRecipes.isDatabaseOpen()){
            mDBhelperRecipes.close();
        }
        if(mDBhelperFavorites.isDatabaseOpen()){
            mDBhelperFavorites.close();
        }

    }
}
