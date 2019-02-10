package com.coopra.nebulus.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coopra.database.entities.Track;
import com.coopra.nebulus.views.adapters.TrackListAdapter;
import com.coopra.nebulus.databinding.FragmentFeedBinding;
import com.coopra.nebulus.enums.NetworkStates;
import com.coopra.nebulus.view_models.FeedViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class FeedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private TrackListAdapter mAdapter;
    private FeedViewModel mViewModel;
    private FragmentFeedBinding mDataBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FeedViewModel.class);

        mViewModel.getNetworkState().observe(this, new Observer<NetworkStates>() {
            @Override
            public void onChanged(NetworkStates networkState) {
                if (mDataBinding.swipeRefreshView != null) {
                    mDataBinding.swipeRefreshView.setRefreshing(networkState == NetworkStates.LOADING);
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
        mDataBinding = FragmentFeedBinding.inflate(inflater, container, false);
        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new TrackListAdapter(getContext());
        mDataBinding.trackList.setAdapter(mAdapter);
        mDataBinding.trackList.setLayoutManager(new LinearLayoutManager(getContext()));

        mDataBinding.swipeRefreshView.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        mViewModel.refreshFeed();
    }
}
