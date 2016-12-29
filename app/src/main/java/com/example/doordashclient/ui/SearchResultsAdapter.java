package com.example.doordashclient.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.doordashclient.R;
import com.example.doordashclient.model.SearchResult;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Set;

/**
 * Created by vvenkatramen on 12/29/16.
 */

public class SearchResultsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SearchResult> searchResultList;
    private SparseArray<SearchResult> favorites;
    private LayoutInflater inflater;
    private Context context;
    private boolean showFavs;
    private WeakReference<FavoritesDialogFragment> favoritesDialogFragmentWeakReference;
    
    public SearchResultsAdapter (List<SearchResult> searchResultList, SparseArray<SearchResult> favorites, Context context,
                                 boolean showFavs) {
        this.searchResultList = searchResultList;
        this.favorites = favorites;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.showFavs = showFavs;
    }
    
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = inflater.inflate(R.layout.list_item_search_result, parent, false);
        return new SearchResultsViewHolder(view);
    }
    
    public void setFavoritesDialogFragmentWeakReference(FavoritesDialogFragment dialogFragment) {
        this.favoritesDialogFragmentWeakReference = new WeakReference<FavoritesDialogFragment>(dialogFragment);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SearchResult searchResult;
        if (!showFavs) {
            searchResult = searchResultList.get(position);
        } else {
            searchResult = favorites.valueAt(position);
        }
        SearchResultsViewHolder searchResultsViewHolder = (SearchResultsViewHolder) holder;
        searchResultsViewHolder.tvRestaurantName.setText(searchResult.getName());
        if (searchResult.getTags().size() > 0) {
            searchResultsViewHolder.tvRestaurantTag.setText(searchResult.getTags().get(0));
        } else {
            searchResultsViewHolder.tvRestaurantTag.setVisibility(View.INVISIBLE);
        }
        searchResultsViewHolder.tvDescription.setText(searchResult.getDescription());
        Glide.with(context).load(searchResult.getCoverImgUrl())
                .thumbnail(1.0f)
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(searchResultsViewHolder.ivRestaurantPic);
        if (favorites.get(searchResult.getId()) != null) {
            searchResultsViewHolder.ivFavorite.setImageResource(R.drawable.ic_fave_on);
        } else {
            searchResultsViewHolder.ivFavorite.setImageResource(R.drawable.ic_fave_off);
        }
        searchResultsViewHolder.searchResult = searchResult;
    }

    @Override
    public int getItemCount() {
        if (!showFavs) {
            return searchResultList.size();
        } else {
            return favorites.size();
        }
    }

    private class SearchResultsViewHolder extends RecyclerView.ViewHolder {
        final ImageView ivRestaurantPic;
        final TextView tvRestaurantName;
        final TextView tvRestaurantTag;
        final ImageView ivFavorite;
        final TextView tvDescription;
        SearchResult searchResult;

        SearchResultsViewHolder(View itemView) {
            super(itemView);
            ivRestaurantPic = (ImageView) itemView.findViewById(R.id.ivRestaurantPic);
            tvRestaurantName = (TextView) itemView.findViewById(R.id.tvRestaurantName);
            tvRestaurantTag = (TextView) itemView.findViewById(R.id.tvRestaurantTag);
            ivFavorite = (ImageView) itemView.findViewById(R.id.ivFavorite);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);

            ivFavorite.setOnClickListener(view -> {
                if (favorites.get(searchResult.getId()) != null) {
                    ivFavorite.setImageResource(R.drawable.ic_fave_off);
                    favorites.remove(searchResult.getId());
                } else {
                    ivFavorite.setImageResource(R.drawable.ic_fave_on);
                    favorites.put(searchResult.getId(), searchResult);
                }
                if (showFavs && favoritesDialogFragmentWeakReference != null &&
                        favoritesDialogFragmentWeakReference.get() != null &&
                        favorites.size() == 0) {
                    favoritesDialogFragmentWeakReference.get().dismiss();
                }
                SearchResultsAdapter.this.notifyDataSetChanged();
            });
        }
    }
}

