package com.pongodev.recipesapp.providers;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Design and developed by pongodev.com
 *
 * ProviderSuggestion is created to provide search suggestion to search menu item on actionbar.
 * Created using SearchRecentSuggestionsProvider.
 */
public class ProviderSuggestion extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.pongodev.recipesapp.providers.ProviderSuggestion";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public ProviderSuggestion(){
        setupSuggestions(AUTHORITY, MODE);
    }

}
