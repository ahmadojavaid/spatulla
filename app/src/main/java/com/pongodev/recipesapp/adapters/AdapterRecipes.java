
package com.pongodev.recipesapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;
import com.pongodev.recipesapp.R;
import com.pongodev.recipesapp.listeners.OnTapListener;
import com.pongodev.recipesapp.utils.ImageLoaderFromDrawable;
import com.pongodev.recipesapp.utils.MySingleton;
import java.util.ArrayList;

/**
 * Design and developed by pongodev.com
 *
 * AdapterRecipes is created to display recipe item.
 * Created using RecyclerView.Adapter.
 */
public class AdapterRecipes extends RecyclerView.Adapter<AdapterRecipes.ViewHolder>
{
    // Create arraylist variables to store recipe data
    private final ArrayList<String> mRecipeIds;
    private final ArrayList<String> mRecipeNames;
    private final ArrayList<String> mCookTimes;
    private final ArrayList<String> mServings;
    private final ArrayList<String> mRecipeImages;
    private OnTapListener onTapListener;

    private Context mContext;

    // Create variables to store data from resources file
    private String mCookTimeLabel, mMinutesLabel, mServingsLabel, mPersonsLabel, mPersonLabel;

    private ImageLoaderFromDrawable mImageLoaderFromDrawable;
    private final ImageLoader IMAGE_LOADER;

    public AdapterRecipes(Context context)
    {

        this.mRecipeIds = new ArrayList<String>();
        this.mRecipeNames = new ArrayList<String>();
        this.mCookTimes = new ArrayList<String>();
        this.mServings = new ArrayList<String>();
        this.mRecipeImages = new ArrayList<String>();

        mContext = context;

        // get data from strings.xml and store to String variables
        mCookTimeLabel = mContext.getResources().getString(R.string.cook_time);
        mMinutesLabel = mContext.getResources().getString(R.string.minutes);
        mServingsLabel = mContext.getResources().getString(R.string.servings);
        mPersonsLabel = mContext.getResources().getString(R.string.persons);
        mPersonLabel = mContext.getResources().getString(R.string.person);
        int mImageWidth = mContext.getResources().getDimensionPixelSize(R.dimen.thumb_width);
        int mImageHeight = mContext.getResources().getDimensionPixelSize(R.dimen.thumb_height);

        IMAGE_LOADER = MySingleton.getInstance(context).getImageLoader();
        mImageLoaderFromDrawable = new ImageLoaderFromDrawable(mContext, mImageWidth, mImageHeight);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        // Set item layout
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_recipes, null);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position)
    {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (onTapListener != null)
                    onTapListener.onTapView(mRecipeIds.get(position), "");

            }
        });

        // If servings is equal 1 than display "person" else display "persons"
        String lblperson = mServings.get(position).equals("1") ? mPersonLabel: mPersonsLabel;
        // Set data to TextView and ImageView objects
        viewHolder.mTxtRecipeName.setText(mRecipeNames.get(position));
        viewHolder.mTxtRecipeInfo.setText(mCookTimeLabel + " " + mCookTimes.get(position) + " " +
                mMinutesLabel + ", " +
                mServingsLabel + " " + mServings.get(position) + " " + lblperson);

        if(mRecipeImages.get(position).toLowerCase().contains("http")){
            IMAGE_LOADER.get(mRecipeImages.get(position),
                    com.android.volley.toolbox.ImageLoader.
                            getImageListener(viewHolder.mImgRecipeImage,
                                    R.mipmap.empty_photo, R.mipmap.empty_photo));

        } else {
            int image = mContext.getResources().getIdentifier(mRecipeImages.get(position),
                    "drawable", mContext.getPackageName());

            // Load image lazily
            mImageLoaderFromDrawable.loadBitmap(image, viewHolder.mImgRecipeImage);
        }




    }

    @Override
    public int getItemCount()
    {
        return mRecipeIds.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        // Create view objects
        private TextView mTxtRecipeName, mTxtRecipeInfo;
        private RoundedImageView mImgRecipeImage;

        public ViewHolder(View v)
        {
            super(v);
            // Connect views object with views id in xml
            mTxtRecipeName = (TextView) v.findViewById(R.id.txtRecipeName);
            mTxtRecipeInfo = (TextView) v.findViewById(R.id.txtRecipeInfo);
            mImgRecipeImage = (RoundedImageView) v.findViewById(R.id.imgRecipeImage);
        }
    }

    // Method to update data
    public void updateList(
            ArrayList<String> recipeIds,
            ArrayList<String> recipeNames,
            ArrayList<String> cookTimes,
            ArrayList<String> servings,
            ArrayList<String> images)
    {
        this.mRecipeIds.clear();
        this.mRecipeIds.addAll(recipeIds);

        this.mRecipeNames.clear();
        this.mRecipeNames.addAll(recipeNames);

        this.mCookTimes.clear();
        this.mCookTimes.addAll(cookTimes);

        this.mServings.clear();
        this.mServings.addAll(servings);

        this.mRecipeImages.clear();
        this.mRecipeImages.addAll(images);

        this.notifyDataSetChanged();
    }

    public void setOnTapListener(OnTapListener onTapListener)
    {
        this.onTapListener = onTapListener;
    }
}
