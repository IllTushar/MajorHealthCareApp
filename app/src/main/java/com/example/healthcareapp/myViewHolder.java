package com.example.healthcareapp;

import android.app.Application;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;

public class myViewHolder extends RecyclerView.ViewHolder {
    View view;
    SimpleExoPlayer exoPlayer;
    private PlayerView mExoPlayerView;
    TextView textView;
    public myViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.vtitle);
        mExoPlayerView= itemView.findViewById(R.id.exoplayerview);

    }


}
