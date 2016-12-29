package com.example.doordashclient.ui;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.doordashclient.R;
import com.example.doordashclient.model.SearchResult;

import java.util.List;

/**
 * Created by vvenkatramen on 12/29/16.
 */

public class FavoritesDialogFragment extends DialogFragment {
    List<SearchResult> searchResultList;
    SparseArray<SearchResult> favorites;
    Context context;
    SearchResultsAdapter searchResultsAdapter;

    public static FavoritesDialogFragment getInstance(List<SearchResult> searchResultList, SparseArray<SearchResult> favorites,
                                                      Context context) {
        FavoritesDialogFragment dialogFragment = new FavoritesDialogFragment();
        dialogFragment.searchResultList = searchResultList;
        dialogFragment.favorites = favorites;
        dialogFragment.context = context;
        return dialogFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favs_dialog, container);
        getDialog().setTitle(R.string.favsDialogTitle);
        getDialog().getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        RecyclerView favsRecyclerView = (RecyclerView) view.findViewById(R.id.choice_recycler_view);
        favsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        searchResultsAdapter = new SearchResultsAdapter(searchResultList, favorites, context, true);
        searchResultsAdapter.setFavoritesDialogFragmentWeakReference(this);
        favsRecyclerView.setAdapter(searchResultsAdapter);

        Button btnClose = (Button) view.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(v -> {
            dismiss();
        });
    }
}