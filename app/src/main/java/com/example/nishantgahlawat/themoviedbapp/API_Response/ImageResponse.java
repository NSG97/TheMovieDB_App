package com.example.nishantgahlawat.themoviedbapp.API_Response;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

/**
 * Created by Nishant Gahlawat on 17-07-2017.
 */

public class ImageResponse {

    int id;
    ArrayList<Backdrops> backdrops;

    public static class Backdrops{
        private String file_path;

        public String getFile_path() {
            return file_path;
        }

        public void setFile_path(String file_path) {
            this.file_path = file_path;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Backdrops> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(ArrayList<Backdrops> backdrops) {
        this.backdrops = backdrops;
    }

    public ArrayList<String> getBackdropPaths(){
        ArrayList<String> paths = new ArrayList<>();

        for(Backdrops b: backdrops){
            paths.add(b.getFile_path());
        }

        return paths;
    }
}
