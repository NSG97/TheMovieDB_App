package com.example.nishantgahlawat.themoviedbapp.MainPage;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nishantgahlawat.themoviedbapp.API_Response.DiscoverMovieResponse;
import com.example.nishantgahlawat.themoviedbapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Nishant Gahlawat on 14-07-2017.
 */

public class DiscoverMovieAdapter extends RecyclerView.Adapter<DiscoverMovieAdapter.DiscoverMovieViewHolder> {

    public static final String TAG = "DiscoverMovieAdapterTAG";
    public static final String imageBaseURL = "https://image.tmdb.org/t/p/w500";

    private ArrayList<DiscoverMovieResponse.DiscoverMovie> discoverMovies;
    private Context mContext;

    private OnLoadMoreListener onLoadMoreListener;
    private DiscoverMovieClickListener mListener;
    private boolean isLoading;

    public DiscoverMovieAdapter(RecyclerView recyclerView,
                                ArrayList<DiscoverMovieResponse.DiscoverMovie> discoverMovies,
                                Context context, DiscoverMovieClickListener mListener) {

        this.discoverMovies = discoverMovies;
        this.mContext = context;
        this.mListener = mListener;

        final GridLayoutManager gridLayoutManager = (GridLayoutManager)recyclerView.getLayoutManager();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy>0){
                    final int visibleThreshold = 4;

                    int lastItem = gridLayoutManager.findLastCompletelyVisibleItemPosition();
                    int currentTotalCount = gridLayoutManager.getItemCount();

                    if(!isLoading && currentTotalCount<=lastItem+visibleThreshold){
                        if(onLoadMoreListener!=null){
                            onLoadMoreListener.onLoadMoreDiscoverMovies();
                        }
                        isLoading=true;
                    }
                }
            }
        });
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public DiscoverMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.discover_movie_item,parent,false);
        return new DiscoverMovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DiscoverMovieViewHolder holder, int position) {
        DiscoverMovieResponse.DiscoverMovie discoverMovie = discoverMovies.get(position);
        Picasso.with(mContext)
                .load(imageBaseURL+discoverMovie.getPoster_path())
                .placeholder(R.drawable.index)
                .into(holder.posterIV);
    }

    @Override
    public int getItemCount() {
        return discoverMovies.size();
    }

    public void setLoaded(){
        isLoading=false;
    }

    public class DiscoverMovieViewHolder extends RecyclerView.ViewHolder{

        public ImageView posterIV;

        public DiscoverMovieViewHolder(View itemView) {
            super(itemView);
            posterIV = (ImageView)itemView.findViewById(R.id.discoverMovieIV);
            posterIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        mListener.onDMovieItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public interface OnLoadMoreListener{
        void onLoadMoreDiscoverMovies();
    }

    public interface DiscoverMovieClickListener{
        void onDMovieItemClick(int position);
    }

}
