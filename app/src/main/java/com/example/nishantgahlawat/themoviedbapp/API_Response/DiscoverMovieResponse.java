package com.example.nishantgahlawat.themoviedbapp.API_Response;

import java.util.ArrayList;

/**
 * Created by Nishant Gahlawat on 14-07-2017.
 */

public class DiscoverMovieResponse {
    private int page;
    private int total_results;
    private int total_pages;
    private ArrayList<DiscoverMovie> results;

    public static class DiscoverMovie{
        private String poster_path;
        private String original_title;
        private int id;

        public String getOriginal_title() {
            return original_title;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public ArrayList<DiscoverMovie> getResults() {
        return results;
    }

    public void setResults(ArrayList<DiscoverMovie> results) {
        this.results = results;
    }
}
