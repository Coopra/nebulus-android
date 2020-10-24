package com.coopra.nebulus.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.coopra.nebulus.databinding.FragmentPlayerBinding
import com.coopra.nebulus.view_models.PlayerViewModel

class PlayerFragment : Fragment() {
    private var _binding: FragmentPlayerBinding? = null
    private val viewModel: PlayerViewModel by activityViewModels()
    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.artwork.observe(viewLifecycleOwner, Observer {
            binding.waveformPlayer.setArtworkDrawable(it)
        })

        viewModel.getArtwork()

        if (viewModel.activeTrack != null) {
            binding.waveformPlayer.setWaveformData(viewModel.activeTrack!!.waveform)
        }
    }
}