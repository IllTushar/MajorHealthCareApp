package com.example.healthcareapp.DashboardFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.healthcareapp.Activity.Video_Upload;
import com.example.healthcareapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class VideoFragment extends Fragment {
FloatingActionButton floatingActionButton;
//RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_video, container, false);
        floatingActionButton = root.findViewById(R.id.video_button);
        //recyclerView = root.findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        FirebaseRecyclerOptions<filemodel> options =
//                new FirebaseRecyclerOptions.Builder<filemodel>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("myvideo"), filemodel.class)
//                        .build();





        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             startActivity(new Intent(getActivity(), Video_Upload.class));
            }
        });

        return root;
    }
}