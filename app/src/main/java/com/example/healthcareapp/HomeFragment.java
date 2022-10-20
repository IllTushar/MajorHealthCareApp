package com.example.healthcareapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {
TextView name,gmail,profile,password;
FirebaseDatabase database;
DatabaseReference reference;
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
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Details");
        String Password = getActivity().getIntent().getStringExtra("password");
        password.setText(Password);

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