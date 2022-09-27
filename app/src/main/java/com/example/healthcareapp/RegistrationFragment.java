package com.example.healthcareapp;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationFragment extends Fragment {

    EditText name ,password,confirmpassword,email;
    private FirebaseAuth mAuth;
    Button registration;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_registration, container, false);
        name = root.findViewById(R.id.name);
        password = root.findViewById(R.id.Password);
        confirmpassword = root.findViewById(R.id.confirmPassword);
        email = root.findViewById(R.id.Email);
        mAuth = FirebaseAuth.getInstance();
        registration = root.findViewById(R.id.registration);

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString().trim();
                String Email = email.getText().toString().trim();
                String Password = password.getText().toString().trim();
                String ConfPassword = confirmpassword.getText().toString().trim();
                if (Name.isEmpty()){
                    Toast.makeText(getContext(), "Enter Name!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(Email.isEmpty()){
                    Toast.makeText(getContext(), "Enter Email!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(Password.isEmpty()){
                    Toast.makeText(getContext(), "Enter Password!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(ConfPassword.isEmpty()){
                    Toast.makeText(getContext(), "Enter Conf-Password!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(Password.equals(ConfPassword)) {
                    ProgressDialog pd = new ProgressDialog(getContext());
                    pd.setMessage("Please wait");
                    pd.setTitle("Registration");
                    pd.show();
                    mAuth.createUserWithEmailAndPassword(Email, Password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // startActivity(new Intent(getActivity().getApplication(),DashBoard.class));
                                        pd.dismiss();
                                        Intent i = new Intent(getActivity().getApplication(), DashBoard.class);
                                        startActivity(i);
                                    } else {
                                        pd.dismiss();
                                        //Toast.makeText(MainActivity, "Failed", Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, "signInWithCustomToken:failure", task.getException());

                                    }
                                }
                            });
                }
            }
        });
        return root;

    }
}