package com.example.nishantgahlawat.themoviedbapp.MovieDetails;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.nishantgahlawat.themoviedbapp.API_Response.APIInterface;
import com.example.nishantgahlawat.themoviedbapp.API_Response.AbstractAPI;
import com.example.nishantgahlawat.themoviedbapp.API_Response.ImageResponse;
import com.example.nishantgahlawat.themoviedbapp.IntentConstraints;
import com.example.nishantgahlawat.themoviedbapp.R;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieDetailsActivity extends AppCompatActivity {

    private int movieID;
    ArrayList<String> backdrops;

    private ViewPager mPager;
    private Timer swipeTimer;
    private TimerTask mTimerTask;
    private Handler handler;
    private Runnable update;
    private int currentSlide = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.movieDetailsToolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        movieID = intent.getIntExtra(IntentConstraints.MOVIE_ID,-1);
        String title = intent.getStringExtra("MovieName");

        getSupportActionBar().setTitle(title);

        backdrops = new ArrayList<>();

        mPager = (ViewPager)findViewById(R.id.movieDetailsViewPager);
        CircleIndicator circleIndicator = (CircleIndicator) findViewById(R.id.movieDetailsCircles);
        circleIndicator.setViewPager(mPager);

        initSlides();

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.movieDetailsFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    private void initSlides() {
        Retrofit retrofit = AbstractAPI.getRetrofitInstance();

        APIInterface apiInterface = retrofit.create(APIInterface.class);

        Call<ImageResponse> call = apiInterface.getImages(movieID,getString(R.string.api_key));

        call.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                ImageResponse imageResponse = response.body();

                backdrops.addAll(imageResponse.getBackdropPaths());

                mPager.setAdapter(new SlideAdapter(backdrops,getBaseContext()));

                handler = new Handler();

                update = new Runnable() {
                    @Override
                    public void run() {
                        if(currentSlide == backdrops.size()){
                            currentSlide=0;
                        }
                        mPager.setCurrentItem(currentSlide++,true);
                    }
                };

                swipeTimer = new Timer();
                mTimerTask = new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(update);
                    }
                };
                swipeTimer.schedule(mTimerTask,2500,2500);

                mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        currentSlide = position;
                        setUpSlideTimer();
                    }

                    @Override
                    public void onPageSelected(int position) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {

            }
        });
    }

    private void setUpSlideTimer(){
        swipeTimer.cancel();
        swipeTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        };
        swipeTimer.schedule(mTimerTask,1500,1500);
    }
}
