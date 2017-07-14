package com.example.nishantgahlawat.themoviedbapp.API_Response;

import com.example.nishantgahlawat.themoviedbapp.MainActivity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Nishant Gahlawat on 14-07-2017.
 */

public interface APIInterface {

    @GET("discover/movie?api_key="+MainActivity.API_KEY)
    Call<DiscoverMovieResponse> getDiscoverMovies(@Query("page") int page);
}