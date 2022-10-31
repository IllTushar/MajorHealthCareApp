package com.example.healthcareapp.DashboardFragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.healthcareapp.Activity.DoctorProfile;
import com.example.healthcareapp.Adapter.RecyclerViewAdapter;
import com.example.healthcareapp.Model.DoctorModel;
import com.example.healthcareapp.Model.UserModel;
import com.example.healthcareapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {
TextView name,gmail,profile,password;
CircleImageView img;
FirebaseDatabase database;
DatabaseReference reference;
RecyclerView rec_view;
FloatingActionButton floatingActionButton;
EditText searchBar;
   // private ImageSlider imageSlider;
    RecyclerViewAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        name = root.findViewById(R.id.name);
        gmail = root.findViewById(R.id.gmail);
        profile = root.findViewById(R.id.profile);
        password = root.findViewById(R.id.password);
        img  = root.findViewById(R.id.img);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Details");
        String Password = getActivity().getIntent().getStringExtra("password");
        password.setText(Password);
     //   imageSlider = root.findViewById(R.id.image_slider);

        floatingActionButton = root.findViewById(R.id.btnSearch);
        searchBar = root.findViewById(R.id.searchoption);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Search = searchBar.getText().toString().trim();
                searchBar(Search);
            }
        });

//
//        ArrayList<SlideModel> slider = new ArrayList<>();
//        slider.add(new SlideModel(R.drawable.banner,ScaleTypes.FIT));
//        slider.add(new SlideModel(R.drawable.card1, ScaleTypes.FIT));
//        slider.add(new SlideModel(R.drawable.onlineconsult,ScaleTypes.FIT));
//        //slider.add(new SlideModel(R.drawable.login4,ScaleTypes.FIT));
//        imageSlider.setImageList(slider);

        // Recycler View....
        rec_view = root.findViewById(R.id.rev_view);
        rec_view.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<DoctorModel> options = new FirebaseRecyclerOptions
                .Builder<DoctorModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference("Doctor's Details"), DoctorModel.class)
                .build();
         adapter= new RecyclerViewAdapter(options);
        rec_view.setAdapter(adapter);




        //Show Profile...
        ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Loading..");
        pd.show();

        reference.child(Password).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pd.dismiss();
                profile.setVisibility(View.GONE);
                password.setVisibility(View.GONE);

                String Email = String.valueOf(snapshot.child("email").getValue());
                String Name = String.valueOf(snapshot.child("name").getValue());

                name.setVisibility(View.VISIBLE);
                name.setText(Name);
                gmail.setVisibility(View.VISIBLE);
                gmail.setText(Email);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                pd.dismiss();
                Toast.makeText(getContext(), "Something went wrong !!", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    private void searchBar(String query) {
        FirebaseRecyclerOptions<DoctorModel> options = new FirebaseRecyclerOptions
                .Builder<DoctorModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference("Doctor's Details").orderByChild("name").startAt(query).endAt(query+"\uf8ff"), DoctorModel.class)
                .build();
        adapter= new RecyclerViewAdapter(options);
        adapter.startListening();
        rec_view.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            adapter.startListening();
        }catch (Exception e){
            Log.d("onStop: ",e.getMessage());
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        try {
            adapter.stopListening();
        }catch (Exception e){
            Log.d("onStop: ",e.getMessage());
        }
    }
}