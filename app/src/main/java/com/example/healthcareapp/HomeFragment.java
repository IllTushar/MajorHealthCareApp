package com.example.healthcareapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.healthcareapp.Model.UserModel;
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
    private ImageSlider imageSlider;
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
        imageSlider = root.findViewById(R.id.image_slider);

        ArrayList<SlideModel> slider = new ArrayList<>();
        slider.add(new SlideModel(R.drawable.banner,ScaleTypes.FIT));
        slider.add(new SlideModel(R.drawable.card1, ScaleTypes.FIT));
        slider.add(new SlideModel(R.drawable.onlineconsult,ScaleTypes.FIT));
        //slider.add(new SlideModel(R.drawable.login4,ScaleTypes.FIT));
        imageSlider.setImageList(slider);
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
}