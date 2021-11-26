package com.pongodev.recipesapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pongodev.recipesapp.R;
import com.pongodev.recipesapp.utils.Utils;

import org.sufficientlysecure.htmltextview.HtmlTextView;

/**
 * Design and developed by pongodev.com
 *
 * FragmentPager is created to display html based data on viewpager.
 * Created using Fragment.
 */
public class FragmentPager extends Fragment {

    // Create variable to store data
    private String mContent;

    // Method to create FragmentPager and pass data
    public static FragmentPager newInstance(String summary) {
        FragmentPager fragment = new FragmentPager();
        Bundle bundle = new Bundle();
        bundle.putString(Utils.ARG_CONTENT, summary);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Store data that pass from activity to variable
        mContent = getArguments().getString(Utils.ARG_CONTENT);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Set fragment layout
        View rootView = inflater.inflate(R.layout.fragment_pager,container,false);

        // Connect HTMLTextView object with view id in xml
        HtmlTextView mTxtContent = (HtmlTextView) rootView.findViewById(R.id.txtContent);

        // Set data to HTMLTextView
        mTxtContent.setHtmlFromString(mContent, true);

        return rootView;
    }

}
