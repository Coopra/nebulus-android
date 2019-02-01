package com.coopra.nebulus;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coopra.database.entities.Track;
import com.coopra.nebulus.view_models.FeedViewModel;

import java.util.List;

public class FeedFragment extends Fragment {
    private TrackListAdapter mAdapter;
    private FeedViewModel mFeedViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFeedViewModel = ViewModelProviders.of(this).get(FeedViewModel.class);
        mFeedViewModel.getAllTracks().observe(this, new Observer<List<Track>>() {
            @Override
            public void onChanged(@Nullable List<Track> tracks) {
                if (tracks != null && tracks.size() > 0) {
                    // Update the cached copy of the tracks in the adapter.
                    mAdapter.setTracks(tracks);
                } else {
                    mFeedViewModel.populateDatabase();
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.track_list);
        mAdapter = new TrackListAdapter(getContext());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
