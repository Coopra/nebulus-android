package com.coopra.nebulus;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coopra.database.entities.Track;
import com.coopra.nebulus.view_models.FeedViewModel;

public class FeedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private TrackListAdapter mAdapter;
    private FeedViewModel mViewModel;
    private SwipeRefreshLayout mSwipeRefreshView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FeedViewModel.class);

        mViewModel.getNetworkState().observe(this, new Observer<FeedViewModel.NetworkStates>() {
            @Override
            public void onChanged(FeedViewModel.NetworkStates networkState) {
                if (mSwipeRefreshView != null) {
                    mSwipeRefreshView.setRefreshing(networkState == FeedViewModel.NetworkStates.LOADING);
                }
            }
        });

        mViewModel.getAllTracks().observe(this, new Observer<PagedList<Track>>() {
            @Override
            public void onChanged(@Nullable PagedList<Track> tracks) {
                mAdapter.submitList(tracks);
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

        mSwipeRefreshView = view.findViewById(R.id.swipe_refresh_view);
        mSwipeRefreshView.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        mViewModel.refreshFeed();
    }
}
