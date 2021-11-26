
package com.pongodev.recipesapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pongodev.recipesapp.R;
import com.pongodev.recipesapp.listeners.OnTapListener;

import java.util.ArrayList;

/**
 * Design and developed by pongodev.com
 *
 * AdapterCategories is created to display category item.
 * Created using RecyclerView.Adapter.
 */
public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.ViewHolder>
{
    // Create arraylist variables to store category data
    private final ArrayList<String> mCategoryIds;
    private final ArrayList<String> mCategoryNames;

    private OnTapListener onTapListener;

    public AdapterCategories(Context context)
    {

        this.mCategoryIds = new ArrayList<String>();
        this.mCategoryNames = new ArrayList<String>();


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        // Set item layout
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_categories, null);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position)
    {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (onTapListener != null)
                    onTapListener.onTapView(mCategoryIds.get(position),
                            mCategoryNames.get(position));
            }
        });

        // Set category name to TextView object
        viewHolder.mTxtCategory.setText(mCategoryNames.get(position));

    }

    @Override
    public int getItemCount()
    {
        return mCategoryIds.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        // Create TextView object
        TextView mTxtCategory;

        public ViewHolder(View v)
        {
            super(v);
            // Connect TextView object with views id in xml
            mTxtCategory = (TextView) v.findViewById(R.id.txtCategory);
        }
    }

    // Method to update data
    public void updateList(
            ArrayList<String> categoryIds,
            ArrayList<String> categoryNames)
    {
        this.mCategoryIds.clear();
        this.mCategoryIds.addAll(categoryIds);

        this.mCategoryNames.clear();
        this.mCategoryNames.addAll(categoryNames);

        this.notifyDataSetChanged();
    }

    public void setOnTapListener(OnTapListener onTapListener)
    {
        this.onTapListener = onTapListener;
    }
}