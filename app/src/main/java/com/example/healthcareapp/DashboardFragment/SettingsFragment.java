package com.example.healthcareapp.DashboardFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.healthcareapp.Activity.DoctorProfile;
import com.example.healthcareapp.HelpLinePackage.HelplineView;
import com.example.healthcareapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SettingsFragment extends Fragment {

FloatingActionButton floatingActionButton;
Button helpline;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_settings, container, false);
        floatingActionButton =root.findViewById(R.id.addProfile);
         helpline = root.findViewById(R.id.helpline);
         helpline.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 AppCompatActivity activity = (AppCompatActivity) v.getContext();
                 activity.getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout,new HelplineView()).addToBackStack(null).commit();
             }
         });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), DoctorProfile.class);
                startActivity(i);
            }
        });
       return root;
    }

}