package com.example.nishantgahlawat.themoviedbapp.API_Response;

import java.util.ArrayList;

/**
 * Created by Nishant Gahlawat on 16-07-2017.
 */

public class DiscoverTVResponse {
    private int page;
    private int total_results;
    private int total_pages;
    private ArrayList<DiscoverTV> results;

    public DiscoverTVResponse(int page, int total_results, int total_pages, ArrayList<DiscoverTV> results) {
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;
        this.results = results;
    }

    public static class DiscoverTV {
        private String poster_path;

        public DiscoverTV(String poster_path) {
            this.poster_path = poster_path;
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

    public ArrayList<DiscoverTV> getResults() {
        return results;
    }

    public void setResults(ArrayList<DiscoverTV> results) {
        this.results = results;
    }
}
