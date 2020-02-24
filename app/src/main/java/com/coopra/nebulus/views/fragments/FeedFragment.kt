package com.coopra.nebulus.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.coopra.nebulus.databinding.FragmentFeedBinding
import com.coopra.nebulus.enums.NetworkStates
import com.coopra.nebulus.view_models.FeedViewModel
import com.coopra.nebulus.views.adapters.TrackListAdapter

class FeedFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private val adapter = TrackListAdapter()
    private lateinit var viewModel: FeedViewModel
    private lateinit var dataBinding: FragmentFeedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)

        viewModel.getNetworkState().observe(this, Observer {
            dataBinding.swipeRefreshView.isRefreshing = it == NetworkStates.LOADING
        })

        viewModel.getAllTracks().observe(this, Observer {
            adapter.submitList(it)
        })
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        dataBinding = FragmentFeedBinding.inflate(inflater, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding.trackList.adapter = adapter
        dataBinding.trackList.layoutManager = LinearLayoutManager(context)
        dataBinding.swipeRefreshView.setOnRefreshListener(this)
    }

    override fun onRefresh() {
        viewModel.refreshFeed()
    }
}