package com.example.doordashclient.ui;

/**
 * Created by vvenkatramen on 12/28/16.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import com.example.doordashclient.R;
import com.example.doordashclient.model.SearchResult;

import java.util.List;

public class SearchResultBottomSheetDialog extends BottomSheetDialogFragment {

    private SearchResultsAdapter searchResultsAdapter;
    private List<SearchResult> searchResultList;
    private SparseArray<SearchResult> favorites;
    private Context context;

    private BottomSheetBehavior.BottomSheetCallback bottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    public static SearchResultBottomSheetDialog newInstance(List<SearchResult> searchResultList, SparseArray<SearchResult> favorites,
                                                            Context context) {
        SearchResultBottomSheetDialog dialogFragment = new SearchResultBottomSheetDialog();
        dialogFragment.searchResultList = searchResultList;
        dialogFragment.favorites = favorites;
        dialogFragment.context = context;
        return dialogFragment;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        RecyclerView recyclerView = (RecyclerView) View.inflate(getContext(), R.layout.bottom_sheet_dialog, null);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchResultsAdapter = new SearchResultsAdapter(searchResultList, favorites, context, false);
        recyclerView.setAdapter(searchResultsAdapter);

        dialog.setContentView(recyclerView);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                final BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialog;
                View bottomSheet = bottomSheetDialog.findViewById(android.support.design.R.id.design_bottom_sheet);
                assert bottomSheet != null;
                final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                bottomSheetBehavior.setPeekHeight(400);
                bottomSheetBehavior.setBottomSheetCallback(bottomSheetBehaviorCallback);
            }
        });

        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        return dialog;
    }
}
