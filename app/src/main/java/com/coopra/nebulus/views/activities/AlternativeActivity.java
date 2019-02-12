package com.coopra.nebulus.views.activities;

import android.os.Bundle;

import com.coopra.data.Track;
import com.coopra.nebulus.R;
import com.coopra.nebulus.view_models.AlternativeViewModel;
import com.coopra.nebulus.views.adapters.AlternativeAdapter;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AlternativeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alternative);

        RecyclerView recyclerView = findViewById(R.id.track_list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final AlternativeAdapter adapter = new AlternativeAdapter();
        recyclerView.setAdapter(adapter);

        AlternativeViewModel viewModel = ViewModelProviders.of(this).get(AlternativeViewModel.class);
        viewModel.getTracks().observe(this, new Observer<List<Track>>() {
            @Override
            public void onChanged(List<Track> tracks) {
                adapter.setTracks(tracks);
            }
        });

        viewModel.retrieveTracks();
    }
}
