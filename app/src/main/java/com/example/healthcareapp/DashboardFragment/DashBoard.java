package com.example.healthcareapp.DashboardFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.healthcareapp.DashboardFragment.CovidFragment;
import com.example.healthcareapp.DashboardFragment.HomeFragment;
import com.example.healthcareapp.DashboardFragment.MapsFragment;
import com.example.healthcareapp.DashboardFragment.SettingsFragment;
import com.example.healthcareapp.DashboardFragment.VideoFragment;
import com.example.healthcareapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class DashBoard extends AppCompatActivity implements ChipNavigationBar.OnItemSelectedListener{
    ChipNavigationBar chipNavigationBar;
    FrameLayout frameLayout;
    Toolbar toolbar;
    FirebaseDatabase database;
    DatabaseReference reference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        loadFragment(new HomeFragment());
        chipNavigationBar = findViewById(R.id.menu);
        chipNavigationBar.setOnItemSelectedListener(this);
        toolbar = findViewById(R.id.toolbar);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Details");
        String Email = getIntent().getStringExtra("email");
         toolbar.setSubtitle(Email);
        setSupportActionBar(toolbar);
    }
    private  void loadFragment(Fragment fragment){
        if (fragment!=null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.FrameLayout,fragment)
                    .commit();
        }
        else{
            Toast.makeText(this, "Fragment Error ", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_bottom,menu);
        return true;
    }
    @Override
    public void onItemSelected(int i) {
        Fragment fragment =null;
        switch (i){
            case R.id.home:
                fragment = new HomeFragment();
                break;
            case R.id.world:
                fragment = new CovidFragment();
                break;
            case R.id.video:
                fragment = new VideoFragment();
                break;
            case R.id.maps:
                fragment = new MapsFragment();
                break;
            case R.id.setting:
                fragment = new SettingsFragment();
                break;

        }
        loadFragment(fragment);
    }
}