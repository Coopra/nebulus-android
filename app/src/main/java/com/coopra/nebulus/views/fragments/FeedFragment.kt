package com.coopra.nebulus.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.coopra.database.entities.Track
import com.coopra.nebulus.R
import com.coopra.nebulus.databinding.FragmentFeedBinding
import com.coopra.nebulus.enums.NetworkStates
import com.coopra.nebulus.view_models.FeedViewModel
import com.coopra.nebulus.views.adapters.TrackListAdapter

class FeedFragment : Fragment(),
        SwipeRefreshLayout.OnRefreshListener,
        TrackListAdapter.SelectionListener {
    private var _binding: FragmentFeedBinding? = null
    private val adapter = TrackListAdapter(this)
    private val viewModel: FeedViewModel by activityViewModels()
    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getNetworkState().observe(this, Observer {
            binding.swipeRefreshView.isRefreshing = it == NetworkStates.LOADING
        })

        viewModel.getAllTracks().observe(this, Observer {
            adapter.submitList(it)
        })
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.trackList.adapter = adapter
        binding.trackList.layoutManager = LinearLayoutManager(context)
        binding.swipeRefreshView.setOnRefreshListener(this)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onRefresh() {
        viewModel.refreshFeed()
    }

    override fun onClick(track: Track) {
        parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PlayerFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
    }
}