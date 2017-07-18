package com.example.nishantgahlawat.themoviedbapp.MainPage;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nishantgahlawat.themoviedbapp.API_Response.DiscoverTVResponse;
import com.example.nishantgahlawat.themoviedbapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Nishant Gahlawat on 16-07-2017.
 */

public class DiscoverTVAdapter extends RecyclerView.Adapter<DiscoverTVAdapter.DiscoverTVViewHolder> {

    public static final String TAG = "DiscoverTVAdapterTAG";
    public static final String imageBaseURL = "https://image.tmdb.org/t/p/w500";

    private ArrayList<DiscoverTVResponse.DiscoverTV> discoverTVs;
    private Context mContext;

    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;

    public DiscoverTVAdapter(RecyclerView recyclerView,
                             ArrayList<DiscoverTVResponse.DiscoverTV> discoverTVs,
                             Context mContext) {
        this.discoverTVs = discoverTVs;
        this.mContext = mContext;

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
                            onLoadMoreListener.onLoadMoreDiscoverTVs();
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
    public DiscoverTVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.discover_tv_item,parent,false);
        return new DiscoverTVViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DiscoverTVViewHolder holder, int position) {
        DiscoverTVResponse.DiscoverTV discoverTV = discoverTVs.get(position);
        Picasso.with(mContext)
                .load(imageBaseURL+discoverTV.getPoster_path())
                .placeholder(R.drawable.index)
                .into(holder.posterIV);
    }

    @Override
    public int getItemCount() {
        return discoverTVs.size();
    }

    public void setLoaded(){
        isLoading=false;
    }

    public class DiscoverTVViewHolder extends RecyclerView.ViewHolder {

        public ImageView posterIV;

        public DiscoverTVViewHolder(View itemView) {
            super(itemView);
            posterIV = (ImageView)itemView.findViewById(R.id.discoverTVIV);
        }

    }

    interface OnLoadMoreListener{
        void onLoadMoreDiscoverTVs();
    }
}
