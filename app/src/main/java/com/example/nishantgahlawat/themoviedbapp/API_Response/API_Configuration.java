package com.example.nishantgahlawat.themoviedbapp.API_Response;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nishant Gahlawat on 18-07-2017.
 */

public class API_Configuration {
    public final static String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";

    private static String baseURL = "https://api.themoviedb.org/3/";

    private static Retrofit INSTANCE;

    private static Object LOCK = new Object();

    public static Retrofit getRetrofitInstance(){
        if(INSTANCE==null){
            synchronized (LOCK){
                if(INSTANCE==null){
                    INSTANCE = new Retrofit.Builder()
                            .baseUrl(baseURL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
