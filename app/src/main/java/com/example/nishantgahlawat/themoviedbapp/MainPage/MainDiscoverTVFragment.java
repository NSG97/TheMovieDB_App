package com.example.nishantgahlawat.themoviedbapp.MainPage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.nishantgahlawat.themoviedbapp.API_Response.APIInterface;
import com.example.nishantgahlawat.themoviedbapp.API_Response.API_Configuration;
import com.example.nishantgahlawat.themoviedbapp.API_Response.DiscoverTVResponse;
import com.example.nishantgahlawat.themoviedbapp.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Nishant Gahlawat on 16-07-2017.
 */

public class MainDiscoverTVFragment extends Fragment implements DiscoverTVAdapter.OnLoadMoreListener {

    RecyclerView mRecyclerView;
    ArrayList<DiscoverTVResponse.DiscoverTV> mDiscoverTVs;
    DiscoverTVAdapter mAdapter;
    ProgressBar mProgressBar;
    FloatingActionButton fab;

    private int page=0;
    private int total_pages;

    public MainDiscoverTVFragment() {
    }

    public void setFab(FloatingActionButton fab) {
        this.fab = fab;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_discover_tv_fragment,container,false);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.discoverTVFragmentRV);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mDiscoverTVs = new ArrayList<>();
        mAdapter = new DiscoverTVAdapter(mRecyclerView,mDiscoverTVs,getContext());
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy>0){
                    fab.hide();
                }
                else if(dy<0){
                    fab.show();
                }
            }
        });



        mProgressBar = (ProgressBar)view.findViewById(R.id.discoverTVFragmentPB);
        mProgressBar.setVisibility(View.INVISIBLE);

        loadInitialDiscoverPage();

        mAdapter.setOnLoadMoreListener(this);

        return view;
    }

    private void loadInitialDiscoverPage() {
        page=0;
        mProgressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = API_Configuration.getRetrofitInstance();

        APIInterface apiInterface = retrofit.create(APIInterface.class);

        Call<DiscoverTVResponse> call = apiInterface.getDiscoverTVs(++page,getString(R.string.api_key));

        call.enqueue(new Callback<DiscoverTVResponse>() {
            @Override
            public void onResponse(Call<DiscoverTVResponse> call, Response<DiscoverTVResponse> response) {
                DiscoverTVResponse tvResponse = response.body();
                total_pages=tvResponse.getTotal_pages();
                mDiscoverTVs.addAll(tvResponse.getResults());
                mAdapter.notifyItemRangeInserted(0,mDiscoverTVs.size());
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<DiscoverTVResponse> call, Throwable t) {
                Log.i("DiscoverTVTAG", "onFailure: ");
            }
        });
    }

    @Override
    public void onLoadMoreDiscoverTVs() {
        if(page<=total_pages){
            mProgressBar.setVisibility(View.VISIBLE);

            Retrofit retrofit = API_Configuration.getRetrofitInstance();

            APIInterface apiInterface = retrofit.create(APIInterface.class);

            Call<DiscoverTVResponse> call = apiInterface.getDiscoverTVs(++page,getString(R.string.api_key));

            call.enqueue(new Callback<DiscoverTVResponse>() {
                @Override
                public void onResponse(Call<DiscoverTVResponse> call, Response<DiscoverTVResponse> response) {
                    DiscoverTVResponse tvResponse = response.body();

                    int positionStart = mDiscoverTVs.size();

                    mDiscoverTVs.addAll(tvResponse.getResults());
                    mAdapter.notifyItemRangeInserted(positionStart,tvResponse.getResults().size());
                    mAdapter.setLoaded();

                    mProgressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<DiscoverTVResponse> call, Throwable t) {
                }
            });
        }
    }

    public void scrollToTop(){
        mRecyclerView.scrollToPosition(0);
    }
}
