package com.example.nishantgahlawat.themoviedbapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.example.nishantgahlawat.themoviedbapp.API_Response.APIInterface;
import com.example.nishantgahlawat.themoviedbapp.API_Response.AbstractAPI;
import com.example.nishantgahlawat.themoviedbapp.API_Response.DiscoverMovieResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements DiscoverMovieAdapter.OnLoadMoreListener {

    public static final String API_KEY = "8b41c974ebd7764fd527b8c0b42f651c";
    private static final String TAG = "MainActivityTAG";

    RecyclerView mRecyclerView;
    ArrayList<DiscoverMovieResponse.DiscoverMovie> mDiscoverMovies;
    DiscoverMovieAdapter mAdapter;
    ProgressBar mProgressBar;

    private int page=0;
    private int total_pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView)findViewById(R.id.mainRecyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mDiscoverMovies = new ArrayList<>();
        mAdapter = new DiscoverMovieAdapter(mRecyclerView,mDiscoverMovies,this);
        mRecyclerView.setAdapter(mAdapter);

        mProgressBar = (ProgressBar)findViewById(R.id.mainProgressBar);
        mProgressBar.setVisibility(View.INVISIBLE);

        loadInitialDiscoverPage();

        mAdapter.setOnLoadMoreListener(this);
    }

    private void loadInitialDiscoverPage() {
        mProgressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = AbstractAPI.getRetrofitInstance();

        APIInterface apiInterface = retrofit.create(APIInterface.class);

        Call<DiscoverMovieResponse> call = apiInterface.getDiscoverMovies(++page);

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

            Call<DiscoverMovieResponse> call = apiInterface.getDiscoverMovies(++page);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
