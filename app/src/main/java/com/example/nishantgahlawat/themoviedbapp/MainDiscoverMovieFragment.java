package com.example.nishantgahlawat.themoviedbapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.nishantgahlawat.themoviedbapp.API_Response.APIInterface;
import com.example.nishantgahlawat.themoviedbapp.API_Response.AbstractAPI;
import com.example.nishantgahlawat.themoviedbapp.API_Response.DiscoverMovieResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Nishant Gahlawat on 16-07-2017.
 */

public class MainDiscoverMovieFragment extends Fragment implements DiscoverMovieAdapter.OnLoadMoreListener {

    RecyclerView mRecyclerView;
    ArrayList<DiscoverMovieResponse.DiscoverMovie> mDiscoverMovies;
    DiscoverMovieAdapter mAdapter;
    ProgressBar mProgressBar;

    private int page=0;
    private int total_pages;

    public MainDiscoverMovieFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_discover_movie_fragment,container,false);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.discoverMovieFragmentRV);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mDiscoverMovies = new ArrayList<>();
        mAdapter = new DiscoverMovieAdapter(mRecyclerView,mDiscoverMovies,getContext());
        mRecyclerView.setAdapter(mAdapter);

        mProgressBar = (ProgressBar)view.findViewById(R.id.discoverMovieFragmentPB);
        mProgressBar.setVisibility(View.INVISIBLE);

        loadInitialDiscoverPage();

        mAdapter.setOnLoadMoreListener(this);

        return view;
    }

    private void loadInitialDiscoverPage() {
        page=0;
        mProgressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = AbstractAPI.getRetrofitInstance();

        APIInterface apiInterface = retrofit.create(APIInterface.class);

        Call<DiscoverMovieResponse> call = apiInterface.getDiscoverMovies(++page, getString(R.string.api_key));

        call.enqueue(new Callback<DiscoverMovieResponse>() {
            @Override
            public void onResponse(Call<DiscoverMovieResponse> call, Response<DiscoverMovieResponse> response) {
                DiscoverMovieResponse movieResponse = response.body();
                total_pages=movieResponse.getTotal_pages();
                mDiscoverMovies.addAll(movieResponse.getResults());
                mAdapter.notifyItemRangeInserted(0,mDiscoverMovies.size());
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<DiscoverMovieResponse> call, Throwable t) {
            }
        });
    }

    @Override
    public void onLoadMoreDiscoverMovies() {
        if(page<=total_pages){
            mProgressBar.setVisibility(View.VISIBLE);

            Retrofit retrofit = AbstractAPI.getRetrofitInstance();

            APIInterface apiInterface = retrofit.create(APIInterface.class);

            Call<DiscoverMovieResponse> call = apiInterface.getDiscoverMovies(++page,getString(R.string.api_key));

            call.enqueue(new Callback<DiscoverMovieResponse>() {
                @Override
                public void onResponse(Call<DiscoverMovieResponse> call, Response<DiscoverMovieResponse> response) {
                    DiscoverMovieResponse movieResponse = response.body();

                    int positionStart = mDiscoverMovies.size();

                    mDiscoverMovies.addAll(movieResponse.getResults());
                    mAdapter.notifyItemRangeInserted(positionStart,movieResponse.getResults().size());
                    mAdapter.setLoaded();

                    mProgressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<DiscoverMovieResponse> call, Throwable t) {
                }
            });
        }
    }
}
