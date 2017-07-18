package com.example.nishantgahlawat.themoviedbapp.MovieDetails;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nishantgahlawat.themoviedbapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Nishant Gahlawat on 17-07-2017.
 */

public class SlideAdapter extends PagerAdapter {

    public static final String imageBaseURL = "https://image.tmdb.org/t/p/w780";

    private ArrayList<String> mBackdrops;
    private Context mContext;

    public SlideAdapter(ArrayList<String> mBackdrops, Context mContext) {
        this.mBackdrops = mBackdrops;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mBackdrops.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View slideLayout = LayoutInflater.from(mContext).inflate(R.layout.slide_layout,container,false);
        ImageView imageView = (ImageView) slideLayout.findViewById(R.id.slideImageView);

        Picasso.with(mContext)
                .load(imageBaseURL+mBackdrops.get(position))
                .placeholder(R.drawable.index2)
                .into(imageView);

        container.addView(slideLayout,0);

        return slideLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
